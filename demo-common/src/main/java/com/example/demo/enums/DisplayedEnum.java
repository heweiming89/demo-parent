package com.example.demo.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public interface DisplayedEnum<T extends Enum<T>> extends java.io.Serializable {
    
    public static final Logger logger = LoggerFactory.getLogger(DisplayedEnum.class);

    String DEFAULT_VALUE_NAME = "value";

    String DEFAULT_LABEL_NAME = "label";

    default String getValue() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    default String getLabel() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_LABEL_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T valueOfEnum(Class<T> enumClass, String value) {
        if (value == null)
            throw new IllegalArgumentException("DisplayedEnum value should not be null");
        if (enumClass.isAssignableFrom(DisplayedEnum.class))
            throw new IllegalArgumentException("illegal DisplayedEnum type");
        if (!enumClass.isEnum()){
            throw new IllegalArgumentException(enumClass.getSimpleName() + " 必须是枚举类型");
        }
        T[] enums = enumClass.getEnumConstants();
        for (T t : enums) {
            DisplayedEnum displayedEnum = (DisplayedEnum) t;
            if (displayedEnum.getValue().equals(value))
                return (T) displayedEnum;
        }
        logger.warn("枚举类{} value为{}的枚举不存在", enumClass.getSimpleName(), value);
        return null;
    }
    
    default String display(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(DEFAULT_VALUE_NAME).append(" = ").append(getValue());
        sb.append(", ").append(DEFAULT_LABEL_NAME).append(" = ").append(getLabel());
        sb.append("]");
        return sb.toString();
    }
    

}
