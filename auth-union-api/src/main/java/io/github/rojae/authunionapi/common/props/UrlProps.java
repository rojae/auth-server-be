package io.github.rojae.authunionapi.common.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

@Component
public class UrlProps {

    // CORE API //
    @Value("${auth.coreapi}")
    public String coreApi;
    @Value("${auth.coreapi.signup.url}")
    public String coreApiSignupUrl;
    @Value("${auth.coreapi.login.url}")
    public String coreApiLoginUrl;

    @Value("${auth.coreapi.logout.url}")
    public String coreApiLogoutUrl;
    @Value("${auth.coreapi.exist-check.url}")
    public String coreApiExistCheckUrl;
    @Value("${auth.coreapi.profile.url}")
    public String coreApiProfileUrl;

    // OAUTH2 API //
    @Value("${auth.oauth2api}")
    public String oauth2Api;
    @Value("${auth.oauth2api.token.publish.url}")
    public String oauth2ApiTokenPublishUrl;
    @Value("${auth.oauth2api.token.drop.url}")
    public String oauth2ApiTokenDropUrl;
    @Value("${auth.oauth2api.token.detail.url}")
    public String oauth2ApiTokenDetailUrl;

    // SOCIAL API //
    @Value("${auth.socialapi}")
    public String socialApi;
    @Value("${auth.socialapi.client-info.url}")
    public String socialApiClientInfoUrl;
    @Value("${auth.socialapi.login.url}")
    public String socialApiLoginUrl;

}
