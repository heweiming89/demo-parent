package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("unused")
public class DemoApplicationTests {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

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

}
