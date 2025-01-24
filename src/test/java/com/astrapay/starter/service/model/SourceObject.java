package com.astrapay.starter.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-14
 */
@Builder
@Data
@AllArgsConstructor
public class SourceObject {

    SourceObjectAdditionalData sourceObjectAdditionalData;
}