package com.example.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = CustomizedConfigProperties.CUSTOMIZED_CONFIG_PROPERTIES_PREFIX)
@Validated
public class CustomizedConfigProperties {

    protected final static String CUSTOMIZED_CONFIG_PROPERTIES_PREFIX = "";

}
