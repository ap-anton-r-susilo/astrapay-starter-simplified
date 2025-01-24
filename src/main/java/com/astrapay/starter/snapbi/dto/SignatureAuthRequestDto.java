package com.astrapay.starter.snapbi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignatureAuthRequestDto {
    private String xClientId;
    private String xTimeStamp;
    private String privateKey;
}
