package io.github.rojae.authserver.oauth.nonsocial;


import io.github.rojae.authserver.oauth.LoginService;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;

public interface NonSocialLoginService extends LoginService {

    OAuth2LoginResponse login(String email, String password);


}
