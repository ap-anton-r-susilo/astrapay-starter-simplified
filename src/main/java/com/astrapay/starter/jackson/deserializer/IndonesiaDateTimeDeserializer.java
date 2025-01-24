package com.astrapay.starter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IndonesiaDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public IndonesiaDateTimeDeserializer(){
        this(null);
    }

    protected IndonesiaDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return LocalDateTime.parse(jsonParser.getCodec().readValue(jsonParser, String.class), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    .atZone(ZoneOffset.UTC)
                    .withZoneSameInstant(ZoneId.of("Asia/Jakarta"))
                    .toLocalDateTime();
        }catch(DateTimeParseException exception) {
            return LocalDateTime.parse(jsonParser.getCodec().readValue(jsonParser, String.class), DateTimeFormatter.ISO_DATE_TIME);
        }
    }
}