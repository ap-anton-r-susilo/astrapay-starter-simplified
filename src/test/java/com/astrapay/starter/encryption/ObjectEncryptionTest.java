package com.astrapay.starter.encryption;

import com.astrapay.starter.encryption.exception.EncryptionException;
import com.astrapay.starter.encryption.security.ObjectEncryption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ObjectEncryptionTest {

    private ObjectEncryption<String> objectEncryption;

    @Mock
    private BytesEncryptor bytesEncryptor;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectEncryption = new ObjectEncryption<>(bytesEncryptor, objectMapper);
    }

    String value = "\"08161889551234567890\"";
    String encryptedValue = "22303831363138383935353132333435363738393022";

    @Test
    void encrypt_Success() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(value)).thenReturn(value);  // Mock objectMapper behavior
        when(bytesEncryptor.encrypt(value.getBytes())).thenReturn(value.getBytes());  // Mock bytesEncryptor behavior

        String actualEncryptedValue = objectEncryption.encrypt(value);
        assertEquals(encryptedValue, actualEncryptedValue);
    }

    @Test
    void encrypt_Failed() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        assertThrows(EncryptionException.class, () -> objectEncryption.encrypt(""));
    }

    @Test
    void decrypt_Success() throws DecoderException, JsonProcessingException {
        String encryptedText = "440a937c10777f7851fc4ee01e6ea6279625b00466c23a77af757d12ae94c974c9d7287410b25610475dc5a5841d1d6f2a4da763a38c9f4c4497f2348c2604221508bdaae0d20d9d6c8537a7a945eb3f6444c07dc103cea9a68b96592f4758c71c9bb51b3ddaaf45a5ff";
        String decryptedValue = "08161889551234567890";
        Class<String> objectType = String.class;

        when(bytesEncryptor.decrypt(Hex.decodeHex(encryptedText))).thenReturn(decryptedValue.getBytes());
        when(objectMapper.readValue(decryptedValue, objectType)).thenReturn(decryptedValue);

        String actualDecryptedValue = objectEncryption.decrypt(encryptedText, objectType);
        assertEquals(decryptedValue, actualDecryptedValue);
    }

    @Test
    void decrypt_Failed() throws DecoderException, JsonProcessingException {
        Class<String> objectType = String.class;
        when(bytesEncryptor.decrypt(Hex.decodeHex(encryptedValue))).thenReturn("invalid_json".getBytes());

        when(objectMapper.readValue("invalid_json", objectType)).thenThrow(JsonProcessingException.class);
        assertThrows(EncryptionException.class, () -> objectEncryption.decrypt(encryptedValue, objectType));
    }

}