package com.astrapay.starter.snapbi.service;

import com.astrapay.starter.snapbi.dto.SignatureDto;
import com.astrapay.starter.snapbi.dto.SignatureBodyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignatureServiceTest {

    @InjectMocks
    SignatureService signatureService;

    @Mock
    ObjectMapper objectMapper;

    private static final String PARTNER_REFERENCE_NO = "123456789";
    private static final String CLIENT_SECRET = "WD628ae7bca24354c4c4896c96f76092";
    private static final String BENEFICIARY_ACCOUNT_NO = "701075323";
    private static final String URL_ACCOUNT_INQUIRY = "/apiservice/snp/inquiry/v1.0/account-inquiry-internal";
    private static final String ACCESS_TOKEN = "p7gmB4vZa1G8Jx7H7Mpic126SkfCJVpz7VqI5dbo3ChLQ2z44YzmJb";
    private static final String TIMESTAMP = "2023-02-23T12:00:00+07:00";
    private static final String MINIFIED_BODY = "{\"partnerReferenceNo\":\"123456789\",\"beneficiaryAccountNo\":\"701075323\"}";
    private static final String EXPECTED_SIGNATURE = "n2mfI/3M4fUU33t/soCpwu9LPTlL55oG0TU07DODt7PJ5YQKnWjOZhU2+PKYYrMWGU4Pp2uf8Z5M3h23AtXRyg==";

    private final AccountInquiry mockAccountInquiry = AccountInquiry.builder()
            .partnerReferenceNo(PARTNER_REFERENCE_NO)
            .beneficiaryAccountNo(BENEFICIARY_ACCOUNT_NO)
            .build();

    private final SignatureDto mockSignatureDto = SignatureDto.builder()
            .httpMethod(HttpMethod.POST.name())
            .url(URL_ACCOUNT_INQUIRY)
            .accessToken(ACCESS_TOKEN)
            .timestamp(TIMESTAMP)
            .build();

    private final Map<String, Object> mockAccountInquiryMap = Map.of(
            "partnerReferenceNo", PARTNER_REFERENCE_NO,
            "beneficiaryAccountNo", BENEFICIARY_ACCOUNT_NO
    );

    @Test
    void generateSignature_HashMap_Success() throws NoSuchAlgorithmException, InvalidKeyException {
        JsonNode mockJsonNode = mock(JsonNode.class);
        when(mockJsonNode.toString()).thenReturn(MINIFIED_BODY);
        when(objectMapper.convertValue(anyMap(), eq(JsonNode.class))).thenReturn(mockJsonNode);
        assertEquals(EXPECTED_SIGNATURE, signatureService.generateSignature(mockAccountInquiryMap, mockSignatureDto, CLIENT_SECRET));
    }

    @Test
    void generateSignature_Object_Success() throws NoSuchAlgorithmException, InvalidKeyException {
        assertEquals(EXPECTED_SIGNATURE, signatureService.generateSignature(mockAccountInquiry, mockSignatureDto, CLIENT_SECRET));
    }

    @Getter
    @Setter
    @Builder
    static class AccountInquiry extends SignatureBodyDto {
        private String partnerReferenceNo;
        private String beneficiaryAccountNo;
    }
}