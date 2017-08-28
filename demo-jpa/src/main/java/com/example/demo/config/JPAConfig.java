package com.example.demo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.example.demo.dao" })
@EntityScan(basePackages = { "com.example.demo.entity" })
public class JPAConfig {

}
