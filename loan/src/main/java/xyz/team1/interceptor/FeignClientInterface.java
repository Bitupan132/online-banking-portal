package xyz.team1.interceptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name="Authentication", url="http://localhost:8989")
public interface FeignClientInterface {
	@GetMapping("/auth/validate")
	String validateToken(@RequestParam String token);
}