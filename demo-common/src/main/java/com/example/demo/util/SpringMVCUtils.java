package com.example.demo.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public final class SpringMVCUtils {

	public static Map<String, List<RequestMethod>> getAllUrl(
			RequestMappingHandlerMapping requestMappingHandlerMapping) {
		Map<RequestMappingInfo, HandlerMethod> methodMap = requestMappingHandlerMapping.getHandlerMethods();
		Set<RequestMappingInfo> mappingInfoSet = methodMap.keySet();
		Map<String, List<RequestMethod>> urlMap = new LinkedHashMap<>();
		for (RequestMappingInfo info : mappingInfoSet) {
			HandlerMethod handlerMethod = methodMap.get(info);
			Class<?> controller = handlerMethod.getBeanType();
			Class<?> returnType = handlerMethod.getMethod().getReturnType();
			RestController restController = controller.getAnnotation(RestController.class);
			ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
			if (restController != null || responseBody != null || returnType == ResponseEntity.class) {
				PatternsRequestCondition patternsCondition = info.getPatternsCondition();
				RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
				Set<String> patterns = patternsCondition.getPatterns();
				Set<RequestMethod> methods = methodsCondition.getMethods();
				if (CollectionUtils.isNotEmpty(patterns) && CollectionUtils.isNotEmpty(methods)) {
					for (String url : patterns) {
						List<RequestMethod> list = new ArrayList<>();
						for (RequestMethod requestMethod : methods) {
							list.add(requestMethod);
						}
						urlMap.put(url, list);
					}
				}
			}
		}
		return urlMap;
	}

}
