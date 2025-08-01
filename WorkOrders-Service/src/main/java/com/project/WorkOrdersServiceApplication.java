package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WorkOrdersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOrdersServiceApplication.class, args);
	}

}
