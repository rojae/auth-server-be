package io.github.rojae.authunionapi.common.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlProps {

    // CORE API //
    @Value("${auth.coreapi.signup.url}")
    public String coreApiSignupUrl;

    @Value("${auth.coreapi.login.url}")
    public String coreApiLoginUrl;

    @Value("${auth.coreapi.logout.url}")
    public String coreApiLogoutUrl;

    @Value("${auth.coreapi.exist-check.url}")
    public String coreApiExistCheckUrl;

    // OAUTH2 API //
    @Value("${auth.oauth2api.token.publish.url}")
    public String oauth2ApiTokenPublishUrl;

    @Value("${auth.oauth2api.token.drop.url}")
    public String oauth2ApiTokenDropUrl;

    // SOCIAL API //
    @Value("${auth.socialapi.client-info.url}")
    public String socialApiClientInfoUrl;

    @Value("${auth.socialapi.login.url}")
    public String socialApiLoginUrl;

}
