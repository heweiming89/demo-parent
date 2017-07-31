package com.example.demo.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ToStringUtils {

    public static String toStringForJSONStyle(Object object) {
        String jsonStr = ToStringBuilder.reflectionToString(object, ToStringStyle.JSON_STYLE);
        return jsonStr;
    }

}
