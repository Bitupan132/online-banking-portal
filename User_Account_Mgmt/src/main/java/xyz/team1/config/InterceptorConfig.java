package xyz.team1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.interceptor.RequestInterceptor;

@Configuration
public class InterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor(FeignClientInterface feign) {
        return new RequestInterceptor(feign);
    }
}
