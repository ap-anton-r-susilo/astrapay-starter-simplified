package com.astrapay.starter.configuration;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

@Configuration
public class EncryptionConfiguration {

    @Value("${encryption.aes.password:t5ejbMPw9Z8vVhuF}")
    private String aesPassword;

    @Value("${encryption.aes.salt:EMbxU7rcJtkPhnZA}")
    private String aesSalt;

    @Bean
    public BytesEncryptor bytesEncryptor() {
        return Encryptors.stronger(aesPassword, DigestUtils.sha512Hex(aesSalt));
    }

}