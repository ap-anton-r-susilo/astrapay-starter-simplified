package com.astrapay.starter.configuration;

import com.astrapay.starter.service.HttpHeaderService;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;

import static com.astrapay.starter.configuration.HTTPHeaderConfiguration.X_CREDENTIAL_ID;
import static com.astrapay.starter.configuration.HTTPHeaderConfiguration.X_IDENTITY;

/**
 * @author Raymond Sugiarto
 * @since 2021-11-18
 */
public class RestTemplateInterceptorConfiguration implements ClientHttpRequestInterceptor {

    public static final String X_USER_ID = "x-user-id";
    public static final String X_ACCOUNT_ID = "x-account-id";
    public static final String X_ACCOUNT_ID_POINT = "x-account-id-point";

    public static final String X_USER_TYPE = "x-user-type";

    private HttpHeaderService httpService;

    private String cookie;

    public RestTemplateInterceptorConfiguration(HttpHeaderService httpService) {
        this.httpService = httpService;
    }

    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest httpRequest, byte[] body, @NotNull ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        if (cookie != null) {
            httpRequest.getHeaders().add(HttpHeaders.COOKIE, cookie);
        }

        if(!Objects.isNull(httpService.getHttpHeader())){
            httpRequest.getHeaders().add(X_USER_ID, String.valueOf(httpService.getHttpHeader().getXUserId()));
            httpRequest.getHeaders().add(X_ACCOUNT_ID, String.valueOf(httpService.getHttpHeader().getXAccountId()));
            httpRequest.getHeaders().add(X_ACCOUNT_ID_POINT, String.valueOf(httpService.getHttpHeader().getXAccountIdPoint()));
            if(Objects.nonNull(httpService.getHttpHeader().getXUserType())){
                httpRequest.getHeaders().add(X_USER_TYPE, String.valueOf(httpService.getHttpHeader().getXUserType()));
            }
            if(Objects.nonNull(httpService.getHttpHeader().getXIdentity())){
                httpRequest.getHeaders().add(X_IDENTITY, String.valueOf(httpService.getHttpHeader().getXIdentity()));
            }
            if (Objects.nonNull(httpService.getHttpHeader().getXCredentialId())) {
                httpRequest.getHeaders().add(X_CREDENTIAL_ID, String.valueOf(httpService.getHttpHeader().getXCredentialId()));
            }
        }
        httpRequest.getHeaders().add(CorrelationConfiguration.CORRELATION_ID_HEADER_NAME, MDC.get(CorrelationConfiguration.CORRELATION_ID_LOG_VAR_NAME));

        var response = clientHttpRequestExecution.execute(httpRequest, body);

        if (cookie == null) {
            cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        }
        return response;
    }
}
