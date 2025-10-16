package com.soeun.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CheckController {

    private static final Logger log = LoggerFactory.getLogger(CheckController.class);

    @GetMapping("/whoami")
    public String whoAmI(
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient googleClient,
            OAuth2AuthenticationToken auth) {

        var reg = googleClient.getClientRegistration();

        log.info("== OAuth2 Client ==");
        log.info("registrationId   : {}", reg.getRegistrationId());
        log.info("clientId         : {}", reg.getClientId());
        log.info("principal        : {}", googleClient.getPrincipalName());
        log.info("scopes           : {}", googleClient.getAccessToken().getScopes());
        log.info("tokenExpiresAt   : {}", googleClient.getAccessToken().getExpiresAt());
        log.info("authorizationUri : {}", reg.getProviderDetails().getAuthorizationUri());
        log.info("tokenUri         : {}", reg.getProviderDetails().getTokenUri());
        log.info("userInfoUri      : {}", reg.getProviderDetails().getUserInfoEndpoint().getUri());

        return "ok";
    }
}