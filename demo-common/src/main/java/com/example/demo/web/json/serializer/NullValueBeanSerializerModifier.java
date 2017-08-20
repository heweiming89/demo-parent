package com.example.demo.web.json.serializer;

import java.lang.reflect.Array;
import java.util.List;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class NullValueBeanSerializerModifier extends BeanSerializerModifier {

    protected NullValueSerializer nullNumberSerializer = new NullValueSerializer(Number.class);
    protected NullValueSerializer nullStringSerializer = new NullValueSerializer(String.class);
    protected NullValueSerializer nullArraySerializer = new NullValueSerializer(Array.class);
    protected NullValueSerializer nullBooleanSerializer = new NullValueSerializer(Boolean.class);

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter beanPropertyWriter : beanProperties) {
            Class<?> propertyType = beanPropertyWriter.getType().getRawClass();
            if (NullValueSerializer.isArrayType(propertyType)) {
                beanPropertyWriter.assignNullSerializer(nullArraySerializer);
            } else if (NullValueSerializer.isNumberType(propertyType)) {
                beanPropertyWriter.assignNullSerializer(nullNumberSerializer);
            } else if (NullValueSerializer.isBooleanType(propertyType)) {
                beanPropertyWriter.assignNullSerializer(nullBooleanSerializer);
            } else if (NullValueSerializer.isStringType(propertyType)) {
                beanPropertyWriter.assignNullSerializer(nullStringSerializer);
            }
        }
        return super.changeProperties(config, beanDesc, beanProperties);

    }

}
