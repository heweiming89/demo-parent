package com.example.demo.web.json.deserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.example.demo.constant.DateFormatConstant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringToDateDeserializer extends JsonDeserializer<Date> {
    
    private static final Logger logger = LoggerFactory.getLogger(StringToDateDeserializer.class);

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String source = p.getText();
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        try {
            Date date = DateUtils.parseDate(source, DateFormatConstant.DATE_FORMAT_PATTERN);
            return date;
        } catch (ParseException e) {
            logger.error("解析日期字符串 {} 失败， {} 格式不匹配", source, DateFormatConstant.DATE_FORMAT_PATTERN);
            throw new IllegalArgumentException(
                    String.format("解析日期字符串 %s 失败， %s 格式不匹配", source, DateFormatConstant.DATE_FORMAT_PATTERN));
        }
    }

}
