package com.astrapay.starter.service.model;

import com.astrapay.starter.annotations.AdditionalData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-21
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SourceParentObjectAdditionalData implements DetailAdditionalData {

    @AdditionalData(value = "id", label = "ID",isCopyable = false)
    private String id;
}
