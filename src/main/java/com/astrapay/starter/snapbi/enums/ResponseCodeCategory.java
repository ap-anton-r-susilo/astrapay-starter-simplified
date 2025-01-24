package com.astrapay.starter.snapbi.enums;

import lombok.Getter;


@Getter
public enum ResponseCodeCategory {
    
    SUCCESS("Success"),
    SYSTEM("System"),
    MESSAGE("Message"),
    BUSINESS("Business");
    
    private final String category;
    
    ResponseCodeCategory(String category) {
        this.category = category;
    }
}
