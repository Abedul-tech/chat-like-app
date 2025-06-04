package com.alen.mcsv_message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class McsvMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsvMessageApplication.class, args);
	}

}
