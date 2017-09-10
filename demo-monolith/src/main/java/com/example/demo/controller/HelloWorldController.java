package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.web.AjaxResponse;

@RestController
public class HelloWorldController {

	@GetMapping(value = "/api/hello")
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

	@GetMapping(value = "/user")
	public User user() {
		User user = new User();
		user.setAge((byte) 11);
		user.setId(11111);
		user.setShouru(88888.88);
		user.setUsername("张三");
		return user;
	}

	public static class User {

		private Integer id;
		private String username;
		private Byte age;
		private Double shouru;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Byte getAge() {
			return age;
		}

		public void setAge(Byte age) {
			this.age = age;
		}

		public Double getShouru() {
			return shouru;
		}

		public void setShouru(Double shouru) {
			this.shouru = shouru;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		}

	}

}
