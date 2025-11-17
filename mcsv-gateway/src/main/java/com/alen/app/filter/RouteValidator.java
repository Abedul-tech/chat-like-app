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
            "/ws"
    );
    //If the URL is NOT in the public list, then the request is secured (authentication is required).
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri-> request.getURI().getPath().contains(uri));
}

/*
*
*             "/api/chat/history",
            "/api/chat/set-msgs-as-sent",
            "/api/auth/register",
            "/api/user/get-id-by-username",
            "/api/user/get-current-session",
            "/api/user/get-user-by-username",
            "/api/user/get-users-by-username",
            "/api/sessions/user",
            "/api/friendship/friends",
            "/api/friendship/get-pending-requests",
            "/api/friendship/send-request",
            "/api/friendship/accept-request",
            *
            * */