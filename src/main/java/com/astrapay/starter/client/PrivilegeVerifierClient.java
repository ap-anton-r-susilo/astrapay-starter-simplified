package com.astrapay.starter.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class PrivilegeVerifierClient {
    @Value("${astrapay-starter.authentication.base.url:https://authentication-sit-api-gcp.astrapay.com/authentication-service}")
    private String authenticationServiceBaseUrl;

    @Value("${astrapay-starter.authentication.endpoint.verifyMyPrivilegeCode:/me/privileges/{privilegeCode}}")
    private String verifyMyPrivilegeCodeEndpoint;

    @Value("${astrapay-starter.authentication.endpoint.privileges:/me/privileges?codes={privilegeCodes}}")
    private String findPrivilegesByPrivilegeCode;

    @Autowired
    @Qualifier("starterRestTemplate")
    private final RestTemplate starterRestTemplate;

    public void verifyMyPrivilegeByCode(String ... codes){

        var targetUrl = verifyMyPrivilegeCodeEndpoint;

        Map<String, String> params = new HashMap<>();
        if (codes.length == 1) {
            params.put("privilegeCode", codes[0]);
        } else {
            targetUrl = findPrivilegesByPrivilegeCode;
            params.put("privilegeCodes", String.join(",", codes));
        }

        var uriString = UriComponentsBuilder.fromUriString(authenticationServiceBaseUrl + targetUrl)
                .buildAndExpand(params).toUriString();
        log.info("PrivilegeVerifierClient::verifyMyPrivilegeByCode Request: {}", uriString);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        try {
            ResponseEntity<Void> responseEntity = starterRestTemplate.exchange(
                    uriString,
                    HttpMethod.HEAD,
                    entity,
                    Void.class);

            log.info("PrivilegeVerifierClient::verifyMyPrivilegeByCode Response: {}", responseEntity);
        } catch (HttpClientErrorException httpClientErrorException) {
            log.error("PrivilegeVerifierClient::verifyMyPrivilegeByCode Response Failed: {} {}", uriString, httpClientErrorException.getMessage());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}