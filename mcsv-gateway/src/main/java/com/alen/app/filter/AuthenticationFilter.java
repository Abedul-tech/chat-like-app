package com.alen.app.filter;

import com.alen.app.dto.TokenDto;
import com.alen.app.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RestTemplate template;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    //Call the constructor of the parent class (AbstractGatewayFilterFactory) and pass it Config.class as an argument
    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //exchange= represents the http request
        //chain= let continue the flow to the next service or filter
        return ((exchange, chain) -> {
            //Creation of variable to pass data through my request
            ServerHttpRequest request=null;
            //If it requires authentication
            if(validator.isSecured.test(exchange.getRequest())){
                //If the request should be secured but doesn’t contain an Authorization header, throw an error.
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    LOGGER.error("TOKEN NOT FOUND");
                    return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
                }

                String authHeader= exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader=authHeader.substring(7);
                }
                //Using the auth microservice
                TokenDto token= TokenDto
                        .builder()
                        .token(authHeader)
                        .build();
                //REST CALL TO AUTH MCSV
                //Url, body of request and return type variable.
                boolean isValid = template.postForObject("http://localhost:8060/api/auth/validate",token,Boolean.class);
                //Using Boolean.TRUE.equals() helps avoid NullPointerException in case the server returns null
                if(isValid){
                    // token is valid
                    System.out.println("SUCCESSFULLY AUTHENTICATED");

                    //Set username as header in the request+'s header
                    //here we set the information we want to pass
                    request = exchange.getRequest()
                            .mutate()
                            .header("loggedInUser",jwtUtil.getSubject(authHeader))
                            .build();
                    return chain.filter(exchange);
                }else{
                    // token is invalid or null
                    LOGGER.error("TOKEN IS MALFORMED OR EXPIRED");
                    //WE NEED TO SET "RETURN"
                    //Otherwise, even an invalid token will still call chain.filter(exchange) and reach your backend services!
                    return onError(exchange,"Token is not valid..!",HttpStatus.UNAUTHORIZED);
                }
            }
            // No authentication needed: just continue
            return chain.filter(exchange);
        });
    }
    // Mono<Void> means it doesn’t emit any value; it only signals when the task is complete (writing the response, in this case).
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatusCode httpStatus) {
        // Set the HTTP status code of the response.
        exchange.getResponse().setStatusCode(httpStatus);
        //It tells that response body will be plain text.
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE,"text/plain");
        // DataBuffer represents chunks of data in bytes.
        // err.getBytes() converts the error string into a byte array.
        // bufferFactory().wrap(...) wraps that byte array into a DataBuffer suitable for writing to the HTTP response.
        // Using the response's buffer factory ensures compatibility and proper resource management.
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(err.getBytes());

        // Return a Mono<Void> to signal that the write operation completes asynchronously.
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
    public static class Config{
    }
}
