package com.astrapay.starter.common;

import com.astrapay.starter.enums.CurrencyPrefix;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountFormatter {
  
  
  public String getIdrString(BigDecimal amount){
    String amountText = String.format("%,.0f", amount);
    return CurrencyPrefix.IDR.getName() + amountText.replace(",",".");
  }
  
  public String getIdrString(String prefix, BigDecimal amount){
    String amountText = String.format("%,.0f", amount);
    return prefix + CurrencyPrefix.IDR.getName() + amountText.replace(",",".");
  }
}
