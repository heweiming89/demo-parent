package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UrlPathHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("unused")
public class MonolithAppTest {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Test
	public void test() {
		PathMatcher pathMatcher = requestMappingHandlerMapping.getPathMatcher();
		UrlPathHelper urlPathHelper = requestMappingHandlerMapping.getUrlPathHelper();
		System.out.println(requestMappingHandlerMapping);
	}

}
