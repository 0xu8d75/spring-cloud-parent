package com.u8d75.gateway.config;

import com.u8d75.gateway.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * GatewayConfig
 */
@Configuration
public class GatewayConfig {

    /**
     * @param environment environment
     * @return LoginFilter
     */
    @Bean
    public LoginFilter loginFilter(Environment environment) {
        return new LoginFilter(environment);
    }
}
