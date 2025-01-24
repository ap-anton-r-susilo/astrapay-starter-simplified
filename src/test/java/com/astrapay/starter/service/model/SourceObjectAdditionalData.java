package com.astrapay.starter.service.model;

import com.astrapay.starter.annotations.AdditionalData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-14
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SourceObjectAdditionalData extends SourceParentObjectAdditionalData implements DetailAdditionalData {

    @AdditionalData(value = "amount", label = "Harga", isCopyable = false)
    private String amount;

    @AdditionalData(label = "Nama",isCopyable = true)
    private String name;
}
