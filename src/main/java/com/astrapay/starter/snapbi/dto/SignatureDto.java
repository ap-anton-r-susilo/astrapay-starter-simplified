package com.astrapay.starter.snapbi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignatureDto {
    private String url;
    private String accessToken;
    private String httpMethod;
    private String timestamp;
}
