package com.example.demo.web;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AjaxResponse", description = "AjaxResponse模型")
public class AjaxResponse<T> {

    public static <T> AjaxResponse<T> getInstance() {
        return new AjaxResponse<>();
    }

    @ApiModelProperty(value = "是否成功", dataType = "Boolean", example = "true", required = true)
    private boolean success;

    @ApiModelProperty(value = "状态码", dataType = "String", example = "200", required = true)
    private String code;

    @ApiModelProperty(value = "错误信息", dataType = "String", example = "请求成功", required = true)
    private String message;

    @SuppressWarnings("unused")
    private LocalDateTime dateTime;
    
    @ApiModelProperty(value = "返回参数", dataType = "T", example = "", required = true)
    private T data;

    private AjaxResponse() {

    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AjaxResponse<T> withCode(String code) {
        this.code = code;
        return this;
    }

    public AjaxResponse<T> withData(T data) {
        this.data = data;
        return this;
    }

    public AjaxResponse<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public AjaxResponse<T> withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
