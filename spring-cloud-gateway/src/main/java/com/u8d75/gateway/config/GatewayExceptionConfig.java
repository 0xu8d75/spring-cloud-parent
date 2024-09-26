package com.u8d75.gateway.config;

import com.u8d75.gateway.exception.GatewayJsonBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * GatewayExceptionConfig
 */
@Configuration
public class GatewayExceptionConfig {

    private List<ViewResolver> viewResolvers;

    private ServerCodecConfigurer serverCodecConfigurer;

    public GatewayExceptionConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                  ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * gatewayJsonBlockExceptionHandler
     *
     * @return GatewayJsonBlockExceptionHandler
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GatewayJsonBlockExceptionHandler gatewayJsonBlockExceptionHandler() {
        return new GatewayJsonBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

}
