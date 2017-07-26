package com.example.demo.advice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.web.AjaxResponse;

@RestControllerAdvice
@Controller
public class ExceptionHandlerController {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    /**
     * 将异常信息封装到AjaxResponse,返回给调用者
     *
     * @param e
     *            异常对象
     * @return AjaxResponse
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<AjaxResponse<Void>> exceptionHandler(Exception e, HttpServletRequest request) {
        AjaxResponse<Void> ajaxResponse = AjaxResponse.getInstance();
        ajaxResponse.setSuccess(Boolean.FALSE);
        logger.error("请求 {} 时发生异常 {} {}", request.getRequestURI(), e.getMessage(), e);
        if (e instanceof BindException) {
            List<FieldError> errors = ((BindException) e).getFieldErrors();
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : errors) {
                String defaultMessage = fieldError.getDefaultMessage();
                sb.append(defaultMessage);
            }
            ajaxResponse.setMessage(sb.toString());
            return new ResponseEntity<AjaxResponse<Void>>(ajaxResponse, HttpStatus.BAD_REQUEST);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            String requestUri = request.getRequestURI();
            String queryType = request.getMethod();
            ajaxResponse.setMessage(requestUri + " 不支持 " + queryType + " 方式请求");
            return new ResponseEntity<AjaxResponse<Void>>(ajaxResponse, HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            // 错误原因
            ajaxResponse.setMessage(e.getMessage());
        }

        return new ResponseEntity<AjaxResponse<Void>>(ajaxResponse, HttpStatus.INTERNAL_SERVER_ERROR); // TODO 根据异常细分返回状态码
    }

    @GetMapping(value = "/unauthorized")
    public ResponseEntity<AjaxResponse<Void>> unauthorized() {
        AjaxResponse<Void> ajaxResponse = AjaxResponse.getInstance();
        ajaxResponse.setMessage("你没有权限访问该资源，请登录或检查用户Cookie信息");
        return new ResponseEntity<AjaxResponse<Void>>(ajaxResponse, HttpStatus.UNAUTHORIZED);
    }

}
