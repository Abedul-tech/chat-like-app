package com.alen.mcsv_presence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class McsvPresenceApplication {
	public static void main(String[] args) {
		SpringApplication.run(McsvPresenceApplication.class, args);
	}

}
