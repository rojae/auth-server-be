package io.github.rojae.authserver.oauth.login.nonsocial;


import io.github.rojae.authserver.oauth.login.LoginService;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;

public interface NonSocialLoginService extends LoginService {

    OAuth2LoginResponse login(String email, String password, String reqUuid);


}
