package com.example.demo.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.MonolithApplicationTests;
import com.example.demo.controller.HelloWorldController.User;

public class RestTemplateConfigTest extends MonolithApplicationTests {

	@Autowired
	private RestTemplate restTemplate;
	
	@Test
	public void testRestTemplate() {
		
		ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://localhost:8080/demo/user", User.class);
		User user = responseEntity.getBody();
		System.out.println(user);
		
	}

}
