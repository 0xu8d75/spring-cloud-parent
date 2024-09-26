package com.u8d75.user;

import com.u8d75.core.spring.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * main
 */
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableWebMvc
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
@MapperScan({"com.u8d75.user.dao"})
@Import(SpringContextHolder.class)
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}

