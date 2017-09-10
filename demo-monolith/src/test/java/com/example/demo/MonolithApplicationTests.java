package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("unused")
public class MonolithApplicationTests {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(MonolithApplicationTests.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler;

	@Test
	public void contextLoads() {

	}

	@Test
	public void restTemplate() {

	}

	@Test
	public void taskExecutor() {
		System.err.println(asyncUncaughtExceptionHandler);
	}

	@Test
	public void testResource() throws IOException {
		File file;
//		file = ResourceUtils.getFile("classpath:/META-INF/resources/webjars/demo-console/1.0.0-SNAPSHOT/index.html");
		file = ResourceUtils.getFile("classpath:/META-INF/resources/webjars/jquery/3.2.1/webjars-requirejs.js");
		String content = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
		System.out.println(file.getPath());
		System.out.println(content);
		
	}

}
