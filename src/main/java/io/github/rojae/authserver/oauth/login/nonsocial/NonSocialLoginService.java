package io.github.rojae.authserver.oauth.login.nonsocial;


import io.github.rojae.authserver.domain.entity.Account;
import io.github.rojae.authserver.dto.ServiceAuthRequest;
import io.github.rojae.authserver.dto.ServiceSignupRequest;
import io.github.rojae.authserver.oauth.login.LoginService;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;

public interface NonSocialLoginService extends LoginService {

    Account signup(ServiceSignupRequest request);
    OAuth2LoginResponse login(String email, String password, String reqUuid);
    void auth(ServiceAuthRequest request);
}
