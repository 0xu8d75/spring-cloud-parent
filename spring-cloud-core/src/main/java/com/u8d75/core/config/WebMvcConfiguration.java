package com.u8d75.core.config;

import com.u8d75.core.interceptor.LogInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * WebMvcConfiguration
 */
@AutoConfiguration
@ConditionalOnClass(HttpServletRequest.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * @return MappedInterceptor
     */
    @Bean
    public MappedInterceptor logInterceptor() {
        return new MappedInterceptor(null, new LogInterceptor());
    }

}