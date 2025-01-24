package com.astrapay.starter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZonedDateTimeDeserializerTest {

    @Test
    public void deserializeTest() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        ZonedDateTimeDeserializer zonedDateTimeDeserializer = new ZonedDateTimeDeserializer();
        ObjectCodec objectCodec = mock(ObjectCodec.class);
        when(jsonParser.getCodec()).thenReturn(objectCodec);
        final String t = "2021-11-28T14:28:30+07:00";
        when(jsonParser.getCodec().readValue(jsonParser, String.class)).thenReturn(t);
        assertEquals(t, zonedDateTimeDeserializer.deserialize(jsonParser, deserializationContext).toString());
    }
}
