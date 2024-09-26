package com.u8d75.gateway;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

/**
 * main
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * define swagger group
     *
     * @param locator                   RouteDefinitionLocator
     * @param swaggerUiConfigParameters swaggerUiConfigParameters
     * @return  service list
     */
    @Bean
    @Lazy(false)
    public Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> apis(RouteDefinitionLocator locator, SwaggerUiConfigParameters swaggerUiConfigParameters) {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        if (null != definitions) {
            definitions.stream().forEach(routeDefinition -> {
                AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl(routeDefinition.getId(), DEFAULT_API_DOCS_URL + routeDefinition.getId(), routeDefinition.getId() + "服务");
                if (!routeDefinition.getId().equals("openapi")) {
                    urls.add(swaggerUrl);
                }
            });
            swaggerUiConfigParameters.setUrls(urls);
        }
        return urls;
    }
}
