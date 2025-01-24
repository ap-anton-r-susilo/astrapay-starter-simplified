package com.astrapay.starter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-16
 */
@Getter
@AllArgsConstructor
public enum CurrencyPrefix {

  IDR("Rp");
  
  private String name;
}
