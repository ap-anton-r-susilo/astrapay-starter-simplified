package com.astrapay.starter.service.v1.string;

import org.springframework.stereotype.Service;

@Service
public class MaskingServiceV1 {

    private static final String MASK_CHAR = "*";
    private static final int ZERO = 0;
    private static final int FOUR = 4;


    public String maskString(String originalString) {
        if (originalString == null || originalString.isEmpty()) {
            return originalString;
        }

        // If the input is a number, mask the first half if it's a number
        if (originalString.matches("[0-9]+")) {
            int lengthToMask = originalString.length() - FOUR;
            if (lengthToMask <= ZERO) {
                return originalString;
            }
            return originalString.substring(ZERO, lengthToMask).replaceAll("\\d", MASK_CHAR) + originalString.substring(lengthToMask);
        }

        // If the input contains multiple words, mask characters between the words
        String[] words = originalString.split("\\s+");
        if (words.length > 1) {
            StringBuilder maskedString = new StringBuilder(originalString);
            for (int i = 1; i < words.length; i++) {
                int startIndexOfMiddleWord = originalString.indexOf(words[i]);
                int endIndexOfMiddleWord = startIndexOfMiddleWord + words[i].length();
                for (int j = startIndexOfMiddleWord + 1; j < endIndexOfMiddleWord - 1; j++) {
                    maskedString.setCharAt(j, MASK_CHAR.charAt(ZERO));
                }
            }
            return maskedString.toString();
        }

        // If the input is a single word, no masking needed
        return originalString;
    }

}
