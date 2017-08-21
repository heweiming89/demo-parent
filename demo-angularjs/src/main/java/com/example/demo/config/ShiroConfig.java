package com.example.demo.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;

import com.example.demo.shiro.realm.UserRealm;

public class ShiroConfig {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

	private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

	@Bean
	public UserRealm ndmUserRealm() {
		UserRealm userRealm = new UserRealm();
		return userRealm;
	}

	/*
	 * @Bean(name = "shiroEhcacheManager") public EhCacheManager
	 * getEhCacheManager() { EhCacheManager em = new EhCacheManager();
	 * em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml"); return em; }
	 */

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
		webSecurityManager.setRealm(ndmUserRealm());
		// dwsm.setCacheManager(getEhCacheManager());
		return webSecurityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(getDefaultWebSecurityManager());
		return authorizationAttributeSourceAdvisor;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// filterChainDefinitionMap.put("/sa/**", "authc");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/verifyCode", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		// filterChainDefinitionMap.put("/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

}
