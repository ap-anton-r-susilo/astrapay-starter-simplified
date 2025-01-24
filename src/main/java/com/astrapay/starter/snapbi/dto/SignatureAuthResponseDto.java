package com.astrapay.starter.snapbi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignatureAuthResponseDto {
    private String signature;
}
