package com.alen.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class McsvGatewayApplication {
	//You're using Spring Cloud Gateway, which is built on the Reactive stack (Spring WebFlux), not on Spring MVC.
	//Review differences between blocking and non blocking
	public static void main(String[] args) {
		SpringApplication.run(McsvGatewayApplication.class, args);
	}

}
