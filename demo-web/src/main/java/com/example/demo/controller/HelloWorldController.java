package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.web.AjaxResponse;

@RestController
public class HelloWorldController {

    @GetMapping(value = "/hello")
    public ResponseEntity<AjaxResponse<Map<String, Object>>> helloWorld() {
        AjaxResponse<Map<String, Object>> ajaxResponse = AjaxResponse.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("date", new Date());
        data.put("localDate", LocalDate.now());
        data.put("localTime", LocalTime.now());
        data.put("localDateTime", LocalDateTime.now());
        ajaxResponse.setData(data);
        return new ResponseEntity<>(ajaxResponse, HttpStatus.OK);
    }

}
