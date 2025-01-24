package com.astrapay.starter.common;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class ApDateTimeFormatter {

  public String getMonthNameDateTimeString(LocalDateTime dateTime){
    Locale locale = new Locale("ID");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", locale);
    return dateTimeFormatter.format(dateTime);
  }

}
