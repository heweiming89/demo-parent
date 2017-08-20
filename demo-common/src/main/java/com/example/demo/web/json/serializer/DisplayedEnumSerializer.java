package com.example.demo.web.json.serializer;

import java.io.IOException;

import com.example.demo.enums.DisplayedEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("rawtypes")
public class DisplayedEnumSerializer extends JsonSerializer<DisplayedEnum> {

    @Override
    public void serialize(DisplayedEnum value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeStringField("className", value.getClass().getSimpleName());
        gen.writeStringField("label", value.getLabel());
        gen.writeStringField("value", value.getValue());
        gen.writeEndObject();
        
    }

}
