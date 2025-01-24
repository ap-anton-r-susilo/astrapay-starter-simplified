package com.astrapay.starter.service.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MaskingServiceTest {

    @InjectMocks
    MaskingService maskingService;

    @Test
    void testMaskingNameDefault() {
        Assertions.assertEquals(
                "I Gu**i De*a",
                maskingService.maskingName("I Gusti Dewa")
        );
    }

    @Test
    void testMaskingName() {
        Assertions.assertEquals(
                "o y* i*i ja*i co***h ka****t y* 12*******0",
                maskingService.maskingName("o ya ini jadi contoh kalimat ya 1234567890", "*", 50)
        );
    }

    @Test
    void testMaskingTruncated() {
        Assertions.assertEquals(
                "o y* i*i ja*i co***h ka**",
                maskingService.maskingName("o ya ini jadi contoh kalimat ya", "*", 25)
        );
    }

    @Test
    void testMaskingPhoneNumber() {
        Assertions.assertEquals(
                "0812*****645",
                maskingService.maskingPhoneNumber("081282675645")
        );
    }

    @Test
    void testMaskingPhoneNumberCustomMaskChar() {
        Assertions.assertEquals(
                "0812XXXXX645",
                maskingService.maskingPhoneNumber("081282675645", "X")
        );
    }
}
