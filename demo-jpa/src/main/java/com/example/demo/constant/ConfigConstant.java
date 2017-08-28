package com.example.demo.constant;

public class ConfigConstant {

    public final static String SCAN_BASE_PACKAGES = "com.example.demo";

    public static final String SWAGGER2_API_DOC_PACKAGES = SCAN_BASE_PACKAGES + ".controller";

    public final static String MAPPER_BASE_PACKAGES = SCAN_BASE_PACKAGES + ".mapper";

    public final static String DAO_BASE_PACKAGES = SCAN_BASE_PACKAGES + ".dao";

    public final static String SERVLET_BASE_PACKAGES = SCAN_BASE_PACKAGES + ".servlet";
    
    public final static String FILTER_BASE_PACKAGES = SCAN_BASE_PACKAGES + ".filter";
    
    public final static String LISTENER_BASE_PACKAGES = SCAN_BASE_PACKAGES + ".listener";

    public final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public final static String TIME_FORMAT_PATTERN = "HH:mm:ss";

    public final static String DATE_TIME_FORMAT_PATTERN = DATE_FORMAT_PATTERN + " " + TIME_FORMAT_PATTERN;

}
