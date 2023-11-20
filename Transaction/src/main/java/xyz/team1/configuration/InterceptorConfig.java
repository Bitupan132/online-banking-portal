package xyz.team1.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.interceptor.RequestInterceptor;

@Configuration
public class InterceptorConfig {

    public HandlerInterceptor requestInterceptor(FeignClientInterface feign) {
    	RequestInterceptor interceptor = new RequestInterceptor(feign);
        System.out.println("RequestInterceptor registered!");
        return interceptor;
    }
}
