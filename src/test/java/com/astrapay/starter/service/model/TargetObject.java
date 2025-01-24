package com.astrapay.starter.service.model;

import com.astrapay.starter.dto.AdditionalDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-14
 */
@Builder
@AllArgsConstructor
@Data
public class TargetObject {

  ArrayList<AdditionalDataDto> additionalDataDtoList;

}
