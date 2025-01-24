package com.astrapay.starter.service.v1.string;

import com.astrapay.starter.service.string.MaskingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MaskingServiceV1Test {

    @InjectMocks
    MaskingServiceV1 maskingService;

    @Test
    void testMaskingNameOneWord() {
        Assertions.assertEquals(
                "Adi",
                maskingService.maskString("Adi")
        );
    }

    @Test
    void testMaskingNameTwoWord() {
        Assertions.assertEquals(
                "Adi W****a",
                maskingService.maskString("Adi Wijaya")
        );
    }

    @Test
    void testMaskingNameThreeWord() {
        Assertions.assertEquals(
                "Adi W****a I**n",
                maskingService.maskString("Adi Wijaya Iman")
        );
    }

    @Test
    void testMaskingNumber() {
        Assertions.assertEquals(
                "******5678",
                maskingService.maskString("0812345678")
        );
    }

    @Test
    void testMaskingEmpty() {
        Assertions.assertEquals(
                "",
                maskingService.maskString("")
        );
    }

    @Test
    void testMaskingIfNumberisFour() {
        Assertions.assertEquals(
                "0813",
                maskingService.maskString("0813")
        );
    }
}
