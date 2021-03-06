package com.chanhonlun.basecms.config;

import com.chanhonlun.basecms.interceptor.ErrorPopupInterceptor;
import com.chanhonlun.basecms.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private ErrorPopupInterceptor errorPopupInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(errorPopupInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/pages/**");
    }
}
