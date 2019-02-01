package com.chanhonlun.basecms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.chanhonlun.basecms.repository", "${com.chanhonlun.project.repository}"},
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class DefaultJpaConfiguration {
}