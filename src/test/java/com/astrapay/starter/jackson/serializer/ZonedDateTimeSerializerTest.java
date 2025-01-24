package com.astrapay.starter.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;

public class ZonedDateTimeSerializerTest {

    @Test
    public void serializeTest() throws IOException {
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        SerializerProvider serializerProvider = mock(SerializerProvider.class);
        ZonedDateTimeSerializer zonedDateTimeSerializer = new ZonedDateTimeSerializer();
        final ZonedDateTime now = ZonedDateTime.now();
        doNothing().when(jsonGenerator).writeString(now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        zonedDateTimeSerializer.serialize(now, jsonGenerator, serializerProvider);
        verify(jsonGenerator, times(1)).writeString(now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
