package com.astrapay.starter.service.string;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MaskingService {

    private static final String BLANK_SPACE = " ";
    private static final String MASK_CHAR = "*";
    private static final int ZERO = 0;
    private static final int MAX_OUTPUT_LENGTH = 20;
    private static final int LIMIT_PATTERN = 3;

    private static final int DYNAMIC_DIGIT_PHONE_NUMBER = 7;

    public String maskingName(String input) {
        return this.maskingName(input, MASK_CHAR, MAX_OUTPUT_LENGTH);
    }

    public String maskingName(String input, String maskChar, int maxOutputLength) {
        final var splitInput = input.split("\\s+");
        var maskedInput = new ArrayList<String>();
        for (String s : splitInput) {
            if (s.length() <= LIMIT_PATTERN) {
                s = s.replaceAll("\\b(\\w)\\w(\\w?)\\b", "$1" + maskChar + "$2");
            } else {
                s = s.replaceAll("\\b(\\w{2})\\w+(\\w)\\b", "$1" + maskChar.repeat(s.length() - LIMIT_PATTERN) + "$2");
            }
            maskedInput.add(s);
        }

        var result = String.join(BLANK_SPACE, maskedInput);
        if (result.length() > maxOutputLength) {
            //truncated to maxOutputLength
            return result.substring(ZERO, maxOutputLength);
        }
        return result;
    }

    public String maskingPhoneNumber(String phoneNumber) {

        String maskedPhoneNumber = phoneNumber.replaceAll("^(\\d{4})\\d+(\\d{3})$", "$1" + MASK_CHAR.repeat(phoneNumber.length() - DYNAMIC_DIGIT_PHONE_NUMBER) + "$2");

        return maskedPhoneNumber;
    }

    public String maskingPhoneNumber(String phoneNumber, String maskChar) {

        String maskedPhoneNumber = phoneNumber.replaceAll("^(\\d{4})\\d+(\\d{3})$", "$1" + maskChar.repeat(phoneNumber.length() - DYNAMIC_DIGIT_PHONE_NUMBER) + "$2");

        return maskedPhoneNumber;
    }
}
