package com.example.demo.config;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.constant.ConfigConstant;
import com.example.demo.enums.DisplayedEnum;
import com.example.demo.web.json.serializer.DisplayedEnumSerializer;
import com.example.demo.web.json.serializer.NullValueBeanSerializerModifier;
import com.example.demo.web.json.serializer.XssStringJsonSerializer;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigDecimalDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import(value = { RestTemplateConfig.class, Swagger2Config.class })
@ServletComponentScan(basePackages = ConfigConstant.LISTENER_BASE_PACKAGES)
@EnableWebMvc // 启用 Spring MVC
@ComponentScan(basePackages = { ConfigConstant.SCAN_BASE_PACKAGES }, useDefaultFilters = false, includeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = Controller.class),
        @Filter(type = FilterType.ANNOTATION, value = RestController.class),
        @Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class) })
@EnableSwagger2
public class WebContextConfig extends WebMvcConfigurerAdapter {

    @SuppressWarnings("unused")
    @Autowired
    private Environment env;

    @Bean /* 文件上传配置 */
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        return multipartResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable(); // 配置静态资源的处理
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true)//
                .dateFormat(new SimpleDateFormat(ConfigConstant.DATE_TIME_FORMAT_PATTERN));
        // .modulesToInstall(new ParameterNamesModule())
        // builder.serializationInclusion(JsonInclude.Include.NON_NULL);

        Map<Class<?>, JsonSerializer<?>> serializers = new HashMap<>();
        serializers.put(DisplayedEnum.class, new DisplayedEnumSerializer());
        serializers.put(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(ConfigConstant.DATE_FORMAT_PATTERN)));
        serializers.put(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(ConfigConstant.TIME_FORMAT_PATTERN)));
        serializers.put(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(ConfigConstant.DATE_TIME_FORMAT_PATTERN)));
        serializers.put(String.class, new XssStringJsonSerializer());

        builder.serializersByType(serializers);

        Map<Class<?>, JsonDeserializer<?>> deserializers = new LinkedHashMap<>();
        deserializers.put(BigDecimal.class, new BigDecimalDeserializer());

        builder.deserializersByType(deserializers);

        MappingJackson2HttpMessageConverter jsonHmc = new MappingJackson2HttpMessageConverter();
        jsonHmc.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8, MediaType.TEXT_HTML));
        ObjectMapper objectMapper = builder.build();
        // 自定义null 序列化
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory().withSerializerModifier(new NullValueBeanSerializerModifier()));
        jsonHmc.setObjectMapper(objectMapper);
        converters.add(jsonHmc);

        MappingJackson2XmlHttpMessageConverter xmlHmc = new MappingJackson2XmlHttpMessageConverter();
        xmlHmc.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_XML, MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_XHTML_XML, MediaType.TEXT_XML));
        xmlHmc.setObjectMapper(builder.createXmlMapper(Boolean.TRUE).build());
        converters.add(xmlHmc);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true).maxAge(TimeUnit.DAYS.toMillis(1));
    }

}
