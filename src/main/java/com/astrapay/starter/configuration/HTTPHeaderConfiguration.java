package com.astrapay.starter.configuration;

import com.astrapay.starter.dto.HttpHeaderDto;
import com.astrapay.starter.enums.Identity;
import com.astrapay.starter.enums.UserType;
import com.astrapay.starter.service.HttpHeaderService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(4)
@Component
public class HTTPHeaderConfiguration extends OncePerRequestFilter {

  private HttpHeaderService httpService;

  public static final String X_USER_ID = "x-user-id";
  public static final String X_ACCOUNT_ID = "x-account-id";
  public static final String X_ACCOUNT_ID_POINT = "x-account-id-point";
  public static final String X_USER_TYPE = "x-user-type";
  public static final String X_IDENTITY = "x-identity";
  public static final String X_FIREBASE_ID = "x-firebase-id";
  public static final String X_ADS_ID = "x-ads-id";
  public static final String X_LATITUDE = "x-latitude";
  public static final String X_LONGITUDE = "x-longitude";
  public static final String X_DEVICE_MODEL = "x-device-model";
  public static final String X_DEVICE_NUMBER = "x-device-number";
  public static final String X_OS_VERSION = "x-os-version";
  public static final String X_APP_VERSION = "x-app-version";
  public static final String X_CSRF_TOKEN = "x-csrf-token";
  public static final String X_FORWARDED_FOR = "x-forwarded-for";
  public static final String TRUE_CLIENT_IP = "true-client-ip";
  public static final String CLIENT_TYPE = "client-type";
  public static final String APPSFLYER_ID = "appsflyer-id";
  public static final String X_FRAUD_SEVERITY = "x-fraud-severity";
  public static final String X_CHANNEL_NAME = "x-channel-name";
  public static final String X_DEVICE_MANUFACTURER = "x-device-manufacturer";
  public static final String X_CREDENTIAL_ID = "x-credential-id";



  public HTTPHeaderConfiguration(HttpHeaderService httpService){
    this.httpService = httpService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, FilterChain filterChain)
          throws ServletException, IOException{
    HttpHeaderDto httpHeaderDto = HttpHeaderDto.builder().build();

    if(httpServletRequest.getHeader(X_USER_ID) != null){
      httpHeaderDto.setXUserId(httpServletRequest.getHeader(X_USER_ID));
    }

    if(httpServletRequest.getHeader(X_ACCOUNT_ID) != null){
      httpHeaderDto.setXAccountId(httpServletRequest.getHeader(X_ACCOUNT_ID));
    }

    if(httpServletRequest.getHeader(X_ACCOUNT_ID_POINT) != null){
      httpHeaderDto.setXAccountIdPoint(httpServletRequest.getHeader(X_ACCOUNT_ID_POINT));
    }

    if(httpServletRequest.getHeader(X_USER_TYPE) != null){
      httpHeaderDto.setXUserType(UserType.valueOf((httpServletRequest.getHeader(X_USER_TYPE))));
    }

    if(httpServletRequest.getHeader(X_IDENTITY) != null){
      httpHeaderDto.setXIdentity(Identity.valueOf((httpServletRequest.getHeader(X_IDENTITY))));
    }

    if(httpServletRequest.getHeader(X_FIREBASE_ID) != null){
      httpHeaderDto.setXFirebaseId(httpServletRequest.getHeader(X_FIREBASE_ID));
    }

    if(httpServletRequest.getHeader(X_ADS_ID) != null){
      httpHeaderDto.setXAdsId(httpServletRequest.getHeader(X_ADS_ID));
    }

    if(httpServletRequest.getHeader(X_LATITUDE) != null){
      httpHeaderDto.setXLatitude(httpServletRequest.getHeader(X_LATITUDE));
    }

    if(httpServletRequest.getHeader(X_LONGITUDE) != null){
      httpHeaderDto.setXLongitude(httpServletRequest.getHeader(X_LONGITUDE));
    }

    if(httpServletRequest.getHeader(X_DEVICE_MODEL) != null){
      httpHeaderDto.setXDeviceModel(httpServletRequest.getHeader(X_DEVICE_MODEL));
    }

    if(httpServletRequest.getHeader(X_DEVICE_NUMBER) != null){
      httpHeaderDto.setXDeviceNumber(httpServletRequest.getHeader(X_DEVICE_NUMBER));
    }

    if(httpServletRequest.getHeader(X_OS_VERSION) != null){
      httpHeaderDto.setXOsVersion(httpServletRequest.getHeader(X_OS_VERSION));
    }

    if(httpServletRequest.getHeader(X_APP_VERSION) != null){
      httpHeaderDto.setXAppVersion(httpServletRequest.getHeader(X_APP_VERSION));
    }

    if (httpServletRequest.getHeader(X_CSRF_TOKEN) != null){
      httpHeaderDto.setXCsrfToken(httpServletRequest.getHeader(X_CSRF_TOKEN));
    }

    if (httpServletRequest.getHeader(X_FORWARDED_FOR) != null){
      httpHeaderDto.setXForwardedFor(httpServletRequest.getHeader(X_FORWARDED_FOR));
    }

    if (httpServletRequest.getHeader(TRUE_CLIENT_IP) != null){
      httpHeaderDto.setTrueClientIp(httpServletRequest.getHeader(TRUE_CLIENT_IP));
    }

    if (httpServletRequest.getHeader(APPSFLYER_ID) != null){
      httpHeaderDto.setAppsFlyerId(httpServletRequest.getHeader(APPSFLYER_ID));
    }

    if (httpServletRequest.getHeader(CLIENT_TYPE) != null){
      httpHeaderDto.setClientType(httpServletRequest.getHeader(CLIENT_TYPE));
    }

    if(httpServletRequest.getHeader(X_FRAUD_SEVERITY) != null){
      httpHeaderDto.setXFraudSeverity((httpServletRequest.getHeader(X_FRAUD_SEVERITY)));
    }

    if(httpServletRequest.getHeader(X_CHANNEL_NAME) != null){
      httpHeaderDto.setXChannelName((httpServletRequest.getHeader(X_CHANNEL_NAME)));
    }

    if(httpServletRequest.getHeader(X_DEVICE_MANUFACTURER) != null){
      httpHeaderDto.setXDeviceManufacturer((httpServletRequest.getHeader(X_DEVICE_MANUFACTURER)));
    }

    if (httpServletRequest.getHeader(X_CREDENTIAL_ID) != null) {
      httpHeaderDto.setXCredentialId(httpServletRequest.getHeader(X_CREDENTIAL_ID));
    }

    httpService.getHttpHeader(httpHeaderDto);
    filterChain.doFilter(httpServletRequest,httpServletResponse);
  }
}