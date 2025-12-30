package com.lemon.computer.config;



import com.lemon.computer.aspect.ApiOperationLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiOperationLogAutoConfiguration {
    @Bean
    public ApiOperationLogAspect apiOperationLogAspect() {
        return new ApiOperationLogAspect();
    }
}
