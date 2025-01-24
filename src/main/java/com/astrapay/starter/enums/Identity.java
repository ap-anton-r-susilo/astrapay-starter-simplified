package com.astrapay.starter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author raymond on 24/02/23
 */
@AllArgsConstructor
@Getter
public enum Identity {
    COM_ASTRAPAY_MERCHANT_APP("MOBILE"),
    COM_ASTRAPAY("MOBILE"),
    SERVICE_SNAP("H2H"),
    SERVICE_MERCHANT("H2H"),
    WEB_ADMIN("WEB"),
    WEB_DASHBOARD("WEB")
    ;

    private final String type;

}