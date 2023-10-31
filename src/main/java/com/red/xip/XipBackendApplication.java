package com.red.xip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄러
@SpringBootApplication
public class XipBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(XipBackendApplication.class, args);
	}

}
