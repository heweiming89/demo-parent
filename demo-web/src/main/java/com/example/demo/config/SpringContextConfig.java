package com.example.demo.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.constant.ConfigConstant;

@Configuration
@MapperScan(basePackages = {ConfigConstant.MAPPER_BASE_PACKAGES, ConfigConstant.DAO_BASE_PACKAGES})
@ComponentScan(basePackages = {ConfigConstant.SCAN_BASE_PACKAGES}, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class),
        @Filter(type = FilterType.ANNOTATION, value = Controller.class),
        @Filter(type = FilterType.ANNOTATION, value = RestController.class),
        @Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class)})
@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
public class SpringContextConfig implements TransactionManagementConfigurer {

    @SuppressWarnings("unused")
    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Bean(name = "transactionManager")
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor(); // 异步线程池
        taskExecutor.setCorePoolSize(5); // 核心线程数
        taskExecutor.setMaxPoolSize(10); // 最大线程数
        taskExecutor.setQueueCapacity(25); // 队列最大长度
        return taskExecutor;
    }
}
