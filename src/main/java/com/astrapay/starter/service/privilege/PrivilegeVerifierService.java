package com.astrapay.starter.service.privilege;

import com.astrapay.starter.client.PrivilegeVerifierClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author r.pb on 22/06/23
 *
 */
@Service
@RequiredArgsConstructor
public class PrivilegeVerifierService {
    private final PrivilegeVerifierClient privilegeVerifierClient;

    public boolean hasPrivilege(String code){
        try {
            privilegeVerifierClient.verifyMyPrivilegeByCode(code);
            return true;
        } catch (ResponseStatusException e){
            return false;
        }
    }

    public boolean hasPrivilege(String ...code){
        try {
            privilegeVerifierClient.verifyMyPrivilegeByCode(code);
            return true;
        } catch (ResponseStatusException e){
            return false;
        }
    }
}
