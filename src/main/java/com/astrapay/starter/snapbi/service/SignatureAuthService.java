package com.astrapay.starter.snapbi.service;

import com.astrapay.starter.snapbi.dto.SignatureAuthRequestDto;
import com.astrapay.starter.snapbi.dto.SignatureAuthResponseDto;
import com.astrapay.starter.snapbi.enums.ServiceCode;
import com.astrapay.starter.snapbi.enums.SnapBiResponseCode;
import com.astrapay.starter.snapbi.exception.CustomSnapBIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Service
public class SignatureAuthService {


    public SignatureAuthResponseDto getSignatureAuth(SignatureAuthRequestDto signatureAuthRequestDto) throws CustomSnapBIException {
        log.info("SignatureAuthRequestDto -> {}", signatureAuthRequestDto);
        String clientId = signatureAuthRequestDto.getXClientId();
        String timeStamp = signatureAuthRequestDto.getXTimeStamp();
        String privateKey = signatureAuthRequestDto.getPrivateKey();

        String input = clientId + "|" + timeStamp;
        String signature = signatureAuthGeneration(input, privateKey);

        log.info("Signature is {}", signature);

        return SignatureAuthResponseDto.builder()
                .signature(signature)
                .build();
    }

    public static String signatureAuthGeneration(String input, String privateKeyString) throws CustomSnapBIException {

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(input.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = privateSignature.sign();

            var result = Base64.getEncoder().encodeToString(signatureBytes);
            log.info("Result of signature Auth Generation {}", result);
            return result;
        } catch(NoSuchAlgorithmException | SignatureException | InvalidKeySpecException | InvalidKeyException | IllegalArgumentException e) {
            throw new CustomSnapBIException(SnapBiResponseCode.UNAUTHORIZED.getResponseCode(ServiceCode.URL_OUTSIDE_SNAP_BI.getLabel()), SnapBiResponseCode.UNAUTHORIZED.getResponseMessage());
        }
    }
}
