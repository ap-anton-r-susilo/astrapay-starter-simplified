package com.astrapay.starter.snapbi.service;

import com.astrapay.starter.snapbi.dto.SignatureDto;
import com.astrapay.starter.snapbi.dto.SignatureBodyDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Service
public class SignatureService {

    @Autowired
    private ObjectMapper objectMapper;

    private static final String HMAC_SHA512 = "HmacSHA512";

    public String generateSignature(Map<String, Object> requestBody, SignatureDto signatureDto, String clientSecret)
            throws NoSuchAlgorithmException, InvalidKeyException {

        String minifiedBody = minifyBody(requestBody);
        String processedBody = DigestUtils.sha256Hex(minifiedBody).toLowerCase();
        String signature = signatureServiceGenerationSnapBi(
                this.toSign(processedBody, signatureDto),
                clientSecret);

        log.info("signature service generated: " + signature);
        return signature;
    }

    public String generateSignature(SignatureBodyDto requestBody, SignatureDto signatureDto, String clientSecret)
            throws NoSuchAlgorithmException, InvalidKeyException {

        String processedBody = DigestUtils.sha256Hex(requestBody.toString()).toLowerCase();
        String signature = signatureServiceGenerationSnapBi(
                this.toSign(processedBody, signatureDto),
                clientSecret);

        log.info("signature service generated: " + signature);
        return signature;
    }

    private String minifyBody(Map<String, Object> requestBody) {
        JsonNode jsonNode = objectMapper.convertValue(requestBody, JsonNode.class);
        return jsonNode.toString();
    }

    private String toSign(String processedBody, SignatureDto signatureDto) {
        return signatureDto.getHttpMethod()
                .concat(":").concat(signatureDto.getUrl())
                .concat(":").concat(signatureDto.getAccessToken())
                .concat(":").concat(processedBody)
                .concat(":").concat(signatureDto.getTimestamp());
    }

    private static String signatureServiceGenerationSnapBi(String input, String clientSecret)
            throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec secretKeySpec = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA512);
        Mac sha512HMAC = Mac.getInstance(HMAC_SHA512);
        sha512HMAC.init(secretKeySpec);

        return Base64.getEncoder().encodeToString(sha512HMAC.doFinal(input.getBytes(StandardCharsets.UTF_8)));
    }
}
