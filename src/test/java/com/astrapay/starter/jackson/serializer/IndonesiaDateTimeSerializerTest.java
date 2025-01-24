package com.astrapay.starter.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;

class IndonesiaDateTimeSerializerTest {

    @Test
    void serializeTest() throws IOException {
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        SerializerProvider serializerProvider = mock(SerializerProvider.class);
        IndonesiaDateTimeSerializer indonesiaDateTimeSerializer = new IndonesiaDateTimeSerializer();
        final LocalDateTime now = LocalDateTime.now();
        doNothing().when(jsonGenerator).writeString(now.format(DateTimeFormatter.ISO_DATE_TIME));
        indonesiaDateTimeSerializer.serialize(now, jsonGenerator, serializerProvider);
        verify(jsonGenerator, times(1)).writeString(now.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
