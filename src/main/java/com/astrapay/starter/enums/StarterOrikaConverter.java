package com.astrapay.starter.enums;

import com.astrapay.starter.configuration.mapper.converter.BigDecimalToFormattedIdrStringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-16
 */
@Getter
@AllArgsConstructor
public enum StarterOrikaConverter {

  AdditionalDataToListConverter("additionalDataToListConverter"),
  BigDecimalToFormattedIdrStringConverter("bigDecimalToFormattedIdrStringConverter");
  

  private String name;
}
