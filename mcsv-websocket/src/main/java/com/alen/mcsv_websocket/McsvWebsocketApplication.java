package com.alen.mcsv_websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
/*this annotation includes:
	@Configuration // Makes this class a source of Spring beans
	@EnableAutoConfiguration // Enables Spring Boot’s auto-config
	@ComponentScan // Finds @Component, @Service, @Repository, etc.
* */
@EnableDiscoveryClient
public class McsvWebsocketApplication {
	public static void main(String[] args) {
		SpringApplication.run(McsvWebsocketApplication.class, args);
	}

}
