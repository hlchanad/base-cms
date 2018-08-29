package com.chanhonlun.springboottest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.chanhonlun.springboottest.repository",
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class DefaultJpaConfiguration { }