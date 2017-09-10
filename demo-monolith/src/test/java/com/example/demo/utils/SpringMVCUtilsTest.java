package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.example.demo.MonolithApplicationTests;
import com.example.demo.util.SpringMVCUtils;

public class SpringMVCUtilsTest extends MonolithApplicationTests {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SpringMVCUtilsTest.class);

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Test
	public void testTest() {
		Map<String, List<RequestMethod>> urlMap = SpringMVCUtils.getAllUrl(requestMappingHandlerMapping);
		if (MapUtils.isNotEmpty(urlMap)) {
			for (String url : urlMap.keySet()) {
				logger.info("{}   {}", url, urlMap.get(url));
			}
		}
	}

}
