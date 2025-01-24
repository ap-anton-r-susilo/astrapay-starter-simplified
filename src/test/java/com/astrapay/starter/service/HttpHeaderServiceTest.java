package com.astrapay.starter.service;

import com.astrapay.starter.dto.HttpHeaderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HttpHeaderServiceTest {

    @InjectMocks
    private HttpHeaderService httpHeaderService;

    @Test
    void getHttpHeader() {
        HttpHeaderDto httpHeaderDto = HttpHeaderDto.builder().build();
        HttpHeaderDto httpHeaderDto1 = httpHeaderService.getHttpHeader(httpHeaderDto);
        assertEquals(httpHeaderDto1, httpHeaderDto);
    }

    @Test
    void testGetHttpHeader() {
        HttpHeaderDto httpHeaderDto1 = httpHeaderService.getHttpHeader();
        assertEquals(null, httpHeaderDto1);
    }
}