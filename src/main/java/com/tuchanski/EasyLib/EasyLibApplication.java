package com.tuchanski.EasyLib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "EasyLib OpenAPI", version = "1", description = "Manage your own personal library using EasyLib OpenAPI"))
@EnableScheduling
public class EasyLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyLibApplication.class, args);
	}

}
