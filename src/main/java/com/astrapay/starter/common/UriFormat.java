package com.astrapay.starter.common;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * @author Raymond Sugiarto
 * @since 2021-09-09
 */
public class UriFormat {

  private StringBuilder stringBuilder = new StringBuilder();

  @JsonAnySetter
  public void addToUri(String name, Object property){
    if (name.length() == 0) return;
    if (property == null) return;
    if (stringBuilder.length() > 0){
      stringBuilder.append("&");
    }
    stringBuilder.append(name).append("=").append(property);
  }

  @Override
  public String toString(){
    return stringBuilder.toString();
  }
}
