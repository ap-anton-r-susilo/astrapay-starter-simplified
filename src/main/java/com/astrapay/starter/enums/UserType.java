package com.astrapay.starter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    CUSTOMER,
    MERCHANT,
    EMPLOYEE,
    BUSINESS_ENTITY
}
