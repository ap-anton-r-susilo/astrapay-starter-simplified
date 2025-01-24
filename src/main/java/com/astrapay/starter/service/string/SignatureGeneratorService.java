package com.astrapay.starter.service.string;

import com.astrapay.starter.service.model.AstrapaySignature;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Radithya Mirza Aribowo
 * @since 2022-04-19
 */
@Slf4j
public class SignatureGeneratorService {

    @Setter
    private Mac sha256HMAC;

    public String createAstraPaySignature(AstrapaySignature astrapaySignature, String astrapayValidationKey ) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

        String astrapaySignatureSign = astrapaySignature.toSign();

        SecretKeySpec secretKeySpec = new SecretKeySpec(astrapayValidationKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(secretKeySpec);
        log.info("astrapaySignatureSign value {}", astrapaySignatureSign);

        String signature = Hex.encodeHexString(sha256HMAC.doFinal(astrapaySignatureSign.getBytes(StandardCharsets.UTF_8)));
        log.info("Signature {}", signature);

        return signature;

    }

}
