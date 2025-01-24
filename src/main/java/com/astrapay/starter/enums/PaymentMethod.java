package com.astrapay.starter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
  
  BALANCE("AstraPay"),
  PAYLATER("Maupaylater"),
  ASTRAPOINTS("Astra Points"),
  VOUCHER("Voucher");
  
  private String title;
}
