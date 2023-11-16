package com.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> openApiEndpoints = List.of(
            "/auth/*",
            "/user/add",
            "/eureka",
            "/user/get/*"
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> pathMatches(uri, request.getURI().getPath()));

   
    private boolean pathMatches(String pattern, String path) {
        boolean matches = pathMatcher.match(pattern, path);

        if (matches) {
            System.out.println("Path '" + path + "' matches pattern '" + pattern + "'");
        } else {
            System.out.println("Path '" + path + "' does not match pattern '" + pattern + "'");
        }

        return matches;
    }

}
