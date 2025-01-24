package com.astrapay.starter.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

@Builder
@Slf4j
public class AstrapaySignature {

    private String url;
    private String key;
    private HttpMethod httpMethod;
    private String body;
    private String timestamp;


    public String toSign() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readValue(body, JsonNode.class);
        String bodyClean = jsonNode.toString();

        log.info("Minified request body {}",bodyClean);

        return httpMethod.toString() + ":"
                + url + ":"
                + key + ":"
                + org.apache.commons.codec.digest.DigestUtils.sha256Hex(bodyClean) + ":"
                + timestamp;
    }


}
