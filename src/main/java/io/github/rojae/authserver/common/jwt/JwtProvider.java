package io.github.rojae.authserver.common.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.rojae.authserver.common.crypt.AESCrypt;
import io.github.rojae.authserver.common.props.JwtProps;
import io.github.rojae.authserver.oauth.OAuth2Principal;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {

    private final JwtProps jwtProps;

    public JwtProvider(JwtProps jwtProps) {
        this.jwtProps = jwtProps;
    }

    public SignatureAlgorithm algorithm() {
        return SignatureAlgorithm.HS256;
    }

    public String dataClaims(OAuth2Principal principal){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        AESCrypt aesCrypt = new AESCrypt(jwtProps.jwtCryptSecretKey, jwtProps.jwtCryptIv);
        String data = null;
        try {
            data = mapper.writeValueAsString(principal);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            return aesCrypt.encrypt(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OAuth2Principal dataClaims(String dataClaims) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        AESCrypt aesCrypt = new AESCrypt(jwtProps.jwtCryptSecretKey, jwtProps.jwtCryptIv);
        String data = null;

        try {
            data = aesCrypt.decrypt(dataClaims);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            return mapper.readValue(data, OAuth2Principal.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(OAuth2Principal principal) {
        Date now = new Date();

        try {
            return Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    .claim(jwtProps.jwtDataClaims, this.dataClaims(principal))          // NOTICE : claims 먼저 셋팅해야함 (그렇지 않을 시, 나머지 데이터 미셋팅 문제 발생)
                    .setIssuer(jwtProps.jwtIssuer)
                    .setIssuedAt(now)
                    .setSubject(jwtProps.jwtSubject)
                    .setExpiration(new Date(now.getTime() + Duration.ofHours(JwtProps.jwtExpireDurationHour).toMillis()))
                    .signWith(this.algorithm(), Base64.encodeBase64(jwtProps.jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.encodeBase64(jwtProps.jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Date getExpiration(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.encodeBase64(jwtProps.jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token).getBody();
//        System.out.println(claims.toString());

        return claims.getExpiration();
    }

    public String get(String token, String key) {
        return Jwts.parser()
                .setSigningKey(Base64.encodeBase64(jwtProps.jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJwt(token)
                .getBody()
                .get(key, String.class);
    }

    public boolean verify(String token){
        Map<String, Object> claimMap = null;
        try {
            claimMap = Jwts.parser()
                    .setSigningKey(Base64.encodeBase64(jwtProps.jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                    .parseClaimsJws(token) // Parsing, Verify
                    .getBody();
        } catch (ExpiredJwtException e) {
            // token Expired
            e.getCause();
            e.printStackTrace();
        }
        return claimMap != null;
    }

    public OAuth2Principal toPrincipal(String token){
        Map<String, Object> claimMap = null;
        try {
            claimMap = Jwts.parser()
                    .setSigningKey(Base64.encodeBase64(jwtProps.jwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                    .parseClaimsJws(token) // Parsing, Verify
                    .getBody();
        } catch (ExpiredJwtException e) {
            // token Expired
            e.getCause();
            e.printStackTrace();
        }

        assert claimMap != null;

        return this.dataClaims(claimMap.get(jwtProps.jwtDataClaims).toString());

    }
}
