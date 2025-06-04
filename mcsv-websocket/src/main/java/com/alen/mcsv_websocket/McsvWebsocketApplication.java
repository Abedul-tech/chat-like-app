package com.alen.mcsv_websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class McsvWebsocketApplication {
	public static void main(String[] args) {
		SpringApplication.run(McsvWebsocketApplication.class, args);
	}

}
