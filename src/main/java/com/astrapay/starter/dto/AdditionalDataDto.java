package com.astrapay.starter.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-13
 */
@Data
@Builder
public class AdditionalDataDto {

    private String key;
    private String value;
    private String label;
    private boolean isCopyable;
}
