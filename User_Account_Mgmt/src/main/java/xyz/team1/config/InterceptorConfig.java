package xyz.team1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.interceptor.RequestInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

    public RequestInterceptor requestInterceptor(FeignClientInterface feign) {
        return new RequestInterceptor(feign);
    }
}
