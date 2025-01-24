package com.astrapay.starter.service.privilege;

import com.astrapay.starter.client.PrivilegeVerifierClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)

class PrivilegeVerifierServiceTest {

    @InjectMocks
    private PrivilegeVerifierService privilegeVerifierService;

    @Mock
    private PrivilegeVerifierClient privilegeVerifierClient;

    private final static String CODE = "disbursement.transaction.view";

    private final static String CODE2 = "disbursement.transaction.delete";

    @Test
    void testHasPrivilege_true(){
        Mockito.doNothing().when(privilegeVerifierClient).verifyMyPrivilegeByCode(Mockito.anyString());
        Assertions.assertTrue(privilegeVerifierService.hasPrivilege(CODE));
    }

    @Test
    void testHasPrivilege_false(){
        Mockito.doThrow(ResponseStatusException.class).when(privilegeVerifierClient).verifyMyPrivilegeByCode(Mockito.anyString());
        Assertions.assertFalse(privilegeVerifierService.hasPrivilege(CODE));
    }

    @Test
    void testHasPrivilegeMultipleCode_true(){
        Mockito.doNothing().when(privilegeVerifierClient).verifyMyPrivilegeByCode(Mockito.anyString(), Mockito.anyString());
        Assertions.assertTrue(privilegeVerifierService.hasPrivilege(CODE, CODE2));
    }

    @Test
    void testHasPrivilegeMultipleCode_false(){
        Mockito.doThrow(ResponseStatusException.class).when(privilegeVerifierClient).verifyMyPrivilegeByCode(Mockito.anyString(), Mockito.anyString());
        Assertions.assertFalse(privilegeVerifierService.hasPrivilege(CODE, CODE2));
    }
}
