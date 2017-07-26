package com.example.demo.config;

import com.example.demo.constant.ConfigConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.AsyncConfigurer;
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

import javax.sql.DataSource;
import java.util.concurrent.Executor;

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
public class SpringContextConfig implements TransactionManagementConfigurer, AsyncConfigurer {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(SpringContextConfig.class);

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
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // 异步线程池
        executor.setCorePoolSize(5); // 线程池维护线程的最少数量
        executor.setKeepAliveSeconds(200); // 允许的空闲时间
        executor.setMaxPoolSize(10); // 线程池维护线程的最大数量
        executor.setQueueCapacity(20); // 缓存队列
        return executor;
    }

    @Bean
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> logger.error(String.format("调用异步时发生意外错误 " + "方法 '%s'.", method), ex);
    }
}
