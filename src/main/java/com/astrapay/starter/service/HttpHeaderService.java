package com.astrapay.starter.service;

import com.astrapay.starter.dto.HttpHeaderDto;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class HttpHeaderService {
    private HttpHeaderDto httpHeaderDto;

    public HttpHeaderDto getHttpHeader(HttpHeaderDto httpHeaderDtoRequest) {
        if (httpHeaderDto == null) {
            this.httpHeaderDto = httpHeaderDtoRequest;
        }
        return this.httpHeaderDto;
    }

    public HttpHeaderDto getHttpHeader() {
        return this.httpHeaderDto;
    }

}