package com.astrapay.starter.dto;

import com.astrapay.starter.enums.Identity;
import com.astrapay.starter.enums.UserType;
import lombok.Builder;
import lombok.Data;

/**
 * @author Raymond Sugiarto
 * @since 2021-08-18
 */
@Data
@Builder
public class HttpHeaderDto {
  private String XUserId;
  private String XAccountId;
  private String XAccountIdPoint;
  private UserType XUserType;
  private Identity XIdentity;
  private String XFirebaseId;
  private String XAdsId;
  private String XLatitude;
  private String XLongitude;
  private String XDeviceModel;
  private String XDeviceNumber;
  private String XOsVersion;
  private String XAppVersion;
  private String XCsrfToken;
  private String XForwardedFor;
  private String TrueClientIp;
  private String AppsFlyerId;
  private String ClientType;
  private String XFraudSeverity;
  private String XChannelName;
  private String XDeviceManufacturer;
  private String XCredentialId;

  public Long getXUserId() {
    if (this.XUserId == null) {
      return 0L;
    }
    return Long.parseLong(this.XUserId);
  }

  public Long getXAccountId() {
    if (this.XAccountId == null) {
      return 0L;
    }
    return Long.parseLong(this.XAccountId);
  }

  public Long getXAccountIdPoint() {
    if (this.XAccountIdPoint == null) {
      return 0L;
    }
    return Long.parseLong(this.XAccountIdPoint);
  }

  public UserType getXUserType() {
    if (this.XUserType == null) {
      return null;
    }
    return this.XUserType;
  }

  public Identity getXIdentity() {
    if (this.XIdentity == null) {
      return null;
    }
    return this.XIdentity;
  }

  public String getXFirebaseId() {
    if (this.XFirebaseId == null) {
      return null;
    }
    return this.XFirebaseId;
  }

  public String getAdsId() {
    if (this.XAdsId == null) {
      return null;
    }
    return this.XAdsId;
  }

  public String getXLatitude() {
    if (this.XLatitude == null) {
      return null;
    }
    return this.XLatitude;
  }

  public String getXLongitude() {
    if (this.XLongitude == null) {
      return null;
    }
    return this.XLongitude;
  }

  public String getXDeviceModel() {
    if (this.XDeviceModel == null) {
      return null;
    }
    return this.XDeviceModel;
  }

  public String getXDeviceNumber() {
    if (this.XDeviceNumber == null) {
      return null;
    }
    return this.XDeviceNumber;
  }

  public String getXOsVersion() {
    if (this.XOsVersion == null) {
      return null;
    }
    return this.XOsVersion;
  }

  public String getXAppVersion() {
    if (this.XAppVersion == null) {
      return null;
    }
    return this.XAppVersion;
  }

  public String getXCsrfToken() {
    if (this.XCsrfToken == null) {
      return null;
    }
    return this.XCsrfToken;
  }

  public String getXForwardedFor() {
    if (this.XForwardedFor == null){
      return null;
    }
    return this.XForwardedFor;
  }

  public String getTrueClientIp() {
    if (this.TrueClientIp == null) {
      return null;
    }
    return this.TrueClientIp;
  }

  public String getAppsFlyerId() {
    if (this.AppsFlyerId == null) {
      return null;
    }
    return this.AppsFlyerId;
  }

  public String getClientType() {
    if (this.ClientType == null){
      return null;
    }
    return this.ClientType;
  }

  public String getXFraudSeverity(){
    if(this.XFraudSeverity == null){
      return "LOW";
    }
    return this.XFraudSeverity;
  }

  public String getXChannelName(){
    return this.XChannelName;
  }

  public String getXDeviceManufacturer(){
    return this.XDeviceManufacturer;
  }

  public Long getXCredentialId() {
    if (this.XCredentialId == null) {
      return 0L;
    }
    return Long.parseLong(this.XCredentialId);
  }
}