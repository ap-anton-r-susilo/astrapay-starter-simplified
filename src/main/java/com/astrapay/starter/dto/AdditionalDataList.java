package com.astrapay.starter.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalDataList {
    List<AdditionalData> additionalDataList;

    public AdditionalDataList add(String key, String value) {
        if (additionalDataList == null) {
            additionalDataList = new ArrayList<>();
        }
        additionalDataList.add(new AdditionalData(key, value));
        return this;
    }
}