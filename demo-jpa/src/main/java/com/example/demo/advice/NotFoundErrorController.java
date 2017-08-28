package com.example.demo.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.web.AjaxResponse;

@Controller
public class NotFoundErrorController implements ErrorController {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(NotFoundErrorController.class);

    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<AjaxResponse<Void>> handleError(HttpServletRequest request) {
        AjaxResponse<Void> ajaxResponse = AjaxResponse.getInstance();
        Object forwardRequestUri = request.getAttribute("javax.servlet.forward.request_uri");
        ajaxResponse.setMessage("该请求地址不存在，请检查URL");
        logger.warn("{} 该请求不存在，请检查URL", forwardRequestUri);
        ResponseEntity<AjaxResponse<Void>> responseEntity = new ResponseEntity<>(ajaxResponse, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

}
