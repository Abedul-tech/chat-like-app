package com.alen.app.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    //Defining endpoints don't require authentication
    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/user/by-username",
            "/ws"
    );
    //If the URL is NOT in the public list, then the request is secured (authentication is required).
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri-> request.getURI().getPath().contains(uri));
}
