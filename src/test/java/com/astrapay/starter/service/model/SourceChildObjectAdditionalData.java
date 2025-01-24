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
public class SourceChildObjectAdditionalData extends SourceObjectAdditionalData {

    @AdditionalData(value = "address", label = "Alamat", isCopyable = false)
    private String address;
}
