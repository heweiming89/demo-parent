package com.example.demo.config;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.example.demo.constant.ConfigConstant;
import com.example.demo.enums.DisplayedEnum;
import com.example.demo.web.json.serializer.DisplayedEnumSerializer;
import com.example.demo.web.json.serializer.NullValueBeanSerializerModifier;
import com.example.demo.web.json.serializer.XssStringJsonSerializer;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigDecimalDeserializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true)//
                .dateFormat(new SimpleDateFormat(ConfigConstant.DATE_TIME_FORMAT_PATTERN));
        // .modulesToInstall(new ParameterNamesModule())
        // builder.serializationInclusion(JsonInclude.Include.NON_NULL);

        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(ConfigConstant.DATE_FORMAT_PATTERN);
        DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern(ConfigConstant.TIME_FORMAT_PATTERN);
        DateTimeFormatter localDateTimeformatter = DateTimeFormatter.ofPattern(ConfigConstant.DATE_TIME_FORMAT_PATTERN);

        Map<Class<?>, JsonSerializer<?>> serializers = new HashMap<>();

        serializers.put(LocalDate.class, new LocalDateSerializer(localDateFormatter));
        serializers.put(LocalTime.class, new LocalTimeSerializer(localTimeFormatter));
        serializers.put(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeformatter));

        serializers.put(String.class, new XssStringJsonSerializer());

        serializers.put(DisplayedEnum.class, new DisplayedEnumSerializer());

        builder.serializersByType(serializers);

        Map<Class<?>, JsonDeserializer<?>> deserializers = new LinkedHashMap<>();
        deserializers.put(BigDecimal.class, new BigDecimalDeserializer());
        deserializers.put(LocalDate.class, new LocalDateDeserializer(localDateFormatter));
        deserializers.put(LocalTime.class, new LocalTimeDeserializer(localTimeFormatter));
        deserializers.put(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeformatter));

        builder.deserializersByType(deserializers);

        return builder;
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(Boolean.FALSE).build();
        // 自定义null 序列化
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory().withSerializerModifier(new NullValueBeanSerializerModifier()));
        return objectMapper;
    }

    @Bean
    public XmlMapper xmlMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(Boolean.TRUE).build();
    }

}
