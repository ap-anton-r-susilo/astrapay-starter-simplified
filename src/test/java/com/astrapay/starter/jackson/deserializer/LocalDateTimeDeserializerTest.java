package com.astrapay.starter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocalDateTimeDeserializerTest {

    @Test
    public void deserializeTest() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer();
        ObjectCodec objectCodec = mock(ObjectCodec.class);
        when(jsonParser.getCodec()).thenReturn(objectCodec);
        final String t = "2021-11-28T14:28:30";
        when(jsonParser.getCodec().readValue(jsonParser, String.class)).thenReturn(t);
        assertEquals(t, localDateTimeDeserializer.deserialize(jsonParser, deserializationContext).toString());
    }

    @Test
    public void deserializeTestWithOffset() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer();
        ObjectCodec objectCodec = mock(ObjectCodec.class);
        when(jsonParser.getCodec()).thenReturn(objectCodec);
        final String t = "2021-11-28T14:28:30+07";
        when(jsonParser.getCodec().readValue(jsonParser, String.class)).thenReturn(t);
        assertEquals("2021-11-28T14:28:30", localDateTimeDeserializer.deserialize(jsonParser, deserializationContext).toString());
    }
}
