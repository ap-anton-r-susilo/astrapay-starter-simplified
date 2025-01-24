package com.astrapay.starter.encryption.security;

import com.astrapay.starter.encryption.exception.EncryptionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ObjectEncryption<T> {

    private final BytesEncryptor bytesEncryptor;
    private final ObjectMapper objectMapper;

    public String encrypt(T object) {
        try {
            String plaintext = objectMapper.writeValueAsString(object);
            byte[] encrypted = bytesEncryptor.encrypt(plaintext.getBytes());

            return Hex.encodeHexString(encrypted);
        } catch (JsonProcessingException e){
            log.error("Error while encrypting: " + e);
            throw new EncryptionException("Error during encryption: " + e.getMessage(), e);
        }

    }

    public T decrypt(String encryptedText, Class<T> objectType) {
        try {
            // Decrypt
            byte[] decrypted = bytesEncryptor.decrypt(Hex.decodeHex(encryptedText));

            // convert byte[] to String
            String decryptedString = new String(decrypted, StandardCharsets.UTF_8);

            // Convert decrypted string back to object
            return objectMapper.readValue(decryptedString, objectType);
        } catch (DecoderException | JsonProcessingException | IllegalStateException e){
            log.error("Error while decrypting: " + e);
            throw new EncryptionException("Error during decryption: " + e.getMessage(), e);
        }
    }

}