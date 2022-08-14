package io.github.rojae.authserver.oauth.social;

import io.github.rojae.authserver.oauth.LoginService;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;

public interface SocialLoginService extends LoginService {

    /**
     * @method : login()
     * @description : Social Login method
     * @author: rojae
     * @date : 2022/07/31
     **/
    OAuth2LoginResponse login(String code);


}
