package com.astrapay.starter.snapbi.service;

import com.astrapay.starter.snapbi.dto.SignatureAuthRequestDto;
import com.astrapay.starter.snapbi.dto.SignatureAuthResponseDto;
import com.astrapay.starter.snapbi.enums.ServiceCode;
import com.astrapay.starter.snapbi.enums.SnapBiResponseCode;
import com.astrapay.starter.snapbi.exception.CustomSnapBIException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SignatureAuthServiceTest {

    @InjectMocks
    private SignatureAuthService signatureAuthService;
    
    private String validPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDlv4zb9bdVVNZHWHrnmljCPXYe0zY0K7li1PEhL9CcRQhUvhynBfWAfUJN1FVebCIJvw90UvvQcwxa5XaCennMue9cELzduK2Fcck+2sF60RrImOCGG6aZvIhyTCNb7VGxCCqouMm8PQPAOYc5HRhLerRxbY7EmMJEaJXG+PUXEmoYQJ52ZxY/1V8n9MUV2JN3dGai3JNj8RO4uJmoTIct/LvbyM5up99qUDKX7wDm5xxWTQ9wPXXsPHu6uUettVjvBWfPrS/s+dlwtbEaOxUa6G6htojGKLH9iPDFVmEJKLYFArUUwz55QePbSH8Hr0P2dhNhBdL1tvEqRtnfFhUDAgMBAAECggEBAIA7eMBwliJPlsX9kaPhVzcbHOm5k6Bs4y1Rk0Clsnb2ZN7/uXhT8CIGR9Ku9/MawajU9ihEBP7kp/m353yOJZtqEDo/sVrDsD4yM1Qcvy7wtVVPVsojdsqKG1JIQtgVTqpT15OSiC7Dk2tf8KSlI6FFXJ+OucfrR0rtYOPMoud7tdAdcpHivG0KZ3hkkOq9C6ZIDI6q7LfbB6DWL5aVsfhQyk6z942HmvPaRllGTJfBjR9ed7ykuQ4C1tFttNg+wUS7haIrtZkFGHM/o6XJr9cY1PJpzG0dEE2mAQ9PnlkSFgvrurNcryc3lB0i6UP0sVnYmnClBIbtkrUkorHGyNECgYEA+urjeUQ9hz4O9b5Qu5fwjUZWmvnU447JVHNzocENbZUg+JpA84q+Iz/AA/UfEW6DQhVG6KXm4N1JhTT18ESHR94fIH1nFeP+R4uo3n66oWbSMVOMbBKd/V6VLE7lk1fnYzenoXBSZ8+L4JEuppzmh+4lkN2WUcoxD+3/VNosFTsCgYEA6mbj3p7HGATlyF7T564AKp31wD9j+VqcxgZuQ4uVoE4dl/6WbmUwbIwroCYNMdZPQbUHIzhogCtptWYsx5rfWbGNf9nP98BG4pCKvcij3DipqBn8Lob6hoYB2b44U9tXZy6RNMekCxb7Mr3bWHMVrnHMBnZdtoqVZe4TPddO4tkCgYBd5rWGVmFoUXMryDNSYQQm+qHCkah4GKGDt0kYybSKw/p4ztFr0wphCqSDVqJv1ZnNi0TkH+INWQMAlNdNOXgfDyM693MyoAYzJDLoQSj66g3TY97zOuxhO5o74LLNt2HioZtcMTrFmshQHEUHGUS0rgzk7+gnZ7GAsqKRUKyarQKBgQCsb8yqdPvZ+6Be6wPQp/yPXWzwZLHTOomiVBDBt+n26AB1VGCuDdq1v9hIFDYYt6POnrIFgvW9dDJrtCK1JIWyoh+KMl3/0H9VJSgrvXJuC3EjrRoL0Fh0phiuanVdmzqNOYCw5z0Su3FIPssiImeM0UJabrE6HHETbzTQhZgO4QKBgGlOwk37Ng6WVGUmhqrsgymUrBRIrrh/qeqsCbPdq5HRPSstS6r4D01/+lOJiAu51dar7Zgtk8Au4OWZ2rp4Gcvv5tFgTVfOKM2P5vAanxaNXThA3MZf9pPv17BrfzDLi1FI7zqpCg28Vy6i5+DbUsb4P43zPOmxR843ZYM0wTTW";
    private String invalidPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDlv4zb9bdVVNZHWHrnmljCPXYe0zY0K7li1PEhL9CcRQhUvhynBfWAfUJN1FVebCIJvw90UvvQcwxa5XaCennMue9cELzduK2Fcck+2sF60RrImOCGG6aZvIhyTCNb7VGxCCqouMm8PQPAOYc5HRhLerRxbY7EmMJEaJXG+PUXEmoYQJ52ZxY/1V8n9MUV2JN3dGai3JNj8RO4uJmoTIct/LvbyM5up99qUDKX7wDm5xxWTQ9wPXXsPHu6uUettVjvBWfPrS/s+dlwtbEaOxUa6G6htojGKLH9iPDFVmEJKLYFArUUwz55QePbSH8Hr0P2dhNhBdL1tvEqRtnfFhUDAgMBAAECggEBAIA7eMBwliJPlsX9kaPhVzcbHOm5k6Bs4y1Rk0Clsnb2ZN7/uXhT8CIGR9Ku9/MawajU9ihEBP7kp/m353yOJZtqEDo/sVrDsD4yM1Qcvy7wtVVPVsojdsqKG1JIQtgVTqpT15OSiC7Dk2tf8KSlI6FFXJ+OucfrR0rtYOPMoud7tdAdcpHivG0KZ3hkkOq9C6ZIDI6q7LfbB6DWL5aVsfhQyk6z942HmvPaRllGTJfBjR9ed7ykuQ4C1tFttNg+wUS7haIrtZkFGHM/o6XJr9cY1PJpzG0dEE2mAQ9PnlkSFgvrurNcryc3lB0i6UP0sVnYmnClBIbtkrUkorHGyNECgYEA+urjeUQ9hz4O9b5Qu5fwjUZWmvnU447JVHNzocENbZUg+JpA84q+Iz/AA/UfEW6DQhVG6KXm4N1JhTT18ESHR94fIH1nFeP+R4uo3n66oWbSMVOMbBKd/V6VLE7lk1fnYzenoXBSZ8+L4JEuppzmh+4lkN2WUcoxD+3/VNosFTsCgYEA6mbj3p7HGATlyF7T564AKp31wD9j+VqcxgZuQ4uVoE4dl/6WbmUwbIwroCYNMdZPQbUHIzhogCtptWYsx5rfWbGNf9nP98BG4pCKvcij3DipqBn8Lob6hoYB2b44U9tXZy6RNMekCxb7Mr3bWHMVrnHMBnZdtoqVZe4TPddO4tkCgYBd5rWGVmFoUXMryDNSYQQm+qHCkah4GKGDt0kYybSKw/p4ztFr0wphCqSDVqJv1ZnNi0TkH+INWQMAlNdNOXgfDyM693MyoAYzJDLoQSj66g3TY97zOuxhO5o74LLNt2HioZtcMTrFmshQHEUHGUS0rgzk7+gnZ7GAsqKRUKyarQKBgQCsb8yqdPvZ+6Be6wPQp/yPXWzwZLHTOomiVBDBt+n26AB1VGCuDdq1v9hIFDYYt6POnrIFgvW9dDJrtCK1JIWyoh+KMl3/0H9VJSgrvXJuC3EjrRoL0Fh0phiuanVdmzqNOYCw5z0Su3FIPssiImeM0UJabrE6HHETbzTQhZgO4QKBgGlOwk37Ng6WVGUmhqrsgymUrBRIrrh/qeqsCbPdq5HRPSstS6r4D01/+lOJiAu51dar7Zgtk8Au4OWZ2rp4Gcvv5tFgTVfOKM2P5vAanxaNXThA3MZf9pPv17BrfzDLi1FI7zqpCg28Vy6i5+DbUsb4P4\n3zPOmxR843ZYM0wTTW";

    private SignatureAuthRequestDto signatureAuthRequestDto = SignatureAuthRequestDto
                                                        .builder()
                                                        .xClientId("253191d0-3776-4564-9506-d04375850aa4")
                                                        .xTimeStamp("2022-03-22T14:45:43+07:00")
                                                        .build();
    @Test
    void getSignatureAuth_Success() {
        signatureAuthRequestDto.setPrivateKey(validPrivateKey);
        SignatureAuthResponseDto signatureAuthResponseDto = signatureAuthService.getSignatureAuth(signatureAuthRequestDto);
        assertEquals("m7/wsqPB+B5kw28wlLGRKQwWf+HaVx9bF3jnJfIr5wGzTlSwcCYv+d8NNRyVPvOO7Dvt8fUS8QRBkLrq22S3ZXQ7rjZ7rQFG37eZyslURANwA/8DC/zlH3LEBuu8kOzM5L+ENM7/WSbFAA99+bHQ1GazZBKSECd7lN7zXDnRCKS3VwCHoUteNLbpJklfY1VScXDR/So4PrzZiOgL2+XWeVtK91V0C4LSjabAb1UuZD3NjfFiIp/BRq2mOBIjxc18NoZ2u6MtvwaMMZzKjJf2VVbQFUAA9/sI2MXKYSCUmcAr/5cDMJ0q7FdihTtuHBCyH+lk/aa+AaUVG3wPMl5YrA==", signatureAuthResponseDto.getSignature());
    }
    
    @Test
    void getSignatureAuth_Failed() {
        signatureAuthRequestDto.setPrivateKey(invalidPrivateKey);
        assertThrows( CustomSnapBIException.class, () -> signatureAuthService.getSignatureAuth(signatureAuthRequestDto));
    }
    
    @Test
    void getSignatureAuth_Failed_ErrorCode() {
        signatureAuthRequestDto.setPrivateKey(invalidPrivateKey);
        try {
            signatureAuthService.getSignatureAuth(signatureAuthRequestDto);
        } catch (CustomSnapBIException customSnapBIException) {
            assertEquals(customSnapBIException.getCode(), SnapBiResponseCode.UNAUTHORIZED.getResponseCode(ServiceCode.URL_OUTSIDE_SNAP_BI.getLabel()));
        }
    }
}