package com.astrapay.starter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndonesiaDateTimeDeserializerTest {

    @Test
    void deserializeTest() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        IndonesiaDateTimeDeserializer indonesiaDateTimeDeserializer = new IndonesiaDateTimeDeserializer();
        ObjectCodec objectCodec = mock(ObjectCodec.class);
        when(jsonParser.getCodec()).thenReturn(objectCodec);
        final String t = "2022-01-24T20:12:34.123Z";
        when(jsonParser.getCodec().readValue(jsonParser, String.class)).thenReturn(t);
        assertEquals("2022-01-25T03:12:34.123", indonesiaDateTimeDeserializer.deserialize(jsonParser, deserializationContext).toString());
    }

    @Test
    void deserializeTestWithOffset() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        IndonesiaDateTimeDeserializer indonesiaDateTimeDeserializer = new IndonesiaDateTimeDeserializer();
        ObjectCodec objectCodec = mock(ObjectCodec.class);
        when(jsonParser.getCodec()).thenReturn(objectCodec);
        final String t = "2022-01-24T20:12:34+07";
        when(jsonParser.getCodec().readValue(jsonParser, String.class)).thenReturn(t);
        assertEquals("2022-01-25T03:12:34", indonesiaDateTimeDeserializer.deserialize(jsonParser, deserializationContext).toString());
    }

    @Test
    void deserializeTestFailed() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        IndonesiaDateTimeDeserializer indonesiaDateTimeDeserializer = new IndonesiaDateTimeDeserializer();
        ObjectCodec objectCodec = mock(ObjectCodec.class);
        when(jsonParser.getCodec()).thenReturn(objectCodec);
        final String t = "2022-01-24T20:12:34";
        when(jsonParser.getCodec().readValue(jsonParser, String.class)).thenReturn(t);
        assertEquals("2022-01-24T20:12:34", indonesiaDateTimeDeserializer.deserialize(jsonParser, deserializationContext).toString());
    }
}
