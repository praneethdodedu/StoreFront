package com.storefront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class StoreFrontZipkinLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreFrontZipkinLoggerApplication.class, args);
	}
}
