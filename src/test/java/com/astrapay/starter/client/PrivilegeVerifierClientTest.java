package com.astrapay.starter.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class PrivilegeVerifierClientTest {

    @InjectMocks
    PrivilegeVerifierClient privilegeVerifierClient;

    @Mock
    private RestTemplate starterRestTemplate;

    private final static String DISBURSEMENT_VIEW_TRANSACTION_CODE = "DISBURSEMENT.TRANSACTION.VIEW";
    private final static String DISBURSEMENT_UPDATE_TRANSACTION_CODE = "DISBURSEMENT.TRANSACTION.UPDATE";

    @Test
    void verifyMyPrivilegeByCode_success(){
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        Mockito.when(starterRestTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                eq(Void.class)
        )).thenReturn(responseEntity);
        Assertions.assertDoesNotThrow(() ->
                privilegeVerifierClient.verifyMyPrivilegeByCode(DISBURSEMENT_VIEW_TRANSACTION_CODE)
        );
    }

    @Test
    void verifyMyPrivilegeByCode_failed() {
        Mockito.when(starterRestTemplate.exchange(
                anyString(),
                any(),
                any(),
                eq(Void.class)
        )).thenThrow(HttpClientErrorException.class);
        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> privilegeVerifierClient.verifyMyPrivilegeByCode(DISBURSEMENT_VIEW_TRANSACTION_CODE)
        );
    }

    @Test
    void verifyMyPrivilegeByMultipleCode_success(){
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        Mockito.when(starterRestTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                eq(Void.class)
        )).thenReturn(responseEntity);
        Assertions.assertDoesNotThrow(() ->
                privilegeVerifierClient.verifyMyPrivilegeByCode(DISBURSEMENT_VIEW_TRANSACTION_CODE, DISBURSEMENT_UPDATE_TRANSACTION_CODE)
        );
    }

    @Test
    void verifyMyPrivilegeByMultipleCode_failed() {
        Mockito.when(starterRestTemplate.exchange(
                anyString(),
                any(),
                any(),
                eq(Void.class)
        )).thenThrow(HttpClientErrorException.class);
        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> privilegeVerifierClient.verifyMyPrivilegeByCode(DISBURSEMENT_VIEW_TRANSACTION_CODE, DISBURSEMENT_UPDATE_TRANSACTION_CODE)
        );
    }
}
