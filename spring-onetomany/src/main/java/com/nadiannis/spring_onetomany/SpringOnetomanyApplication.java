package com.nadiannis.spring_onetomany;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Blog API",
		version = "1.0",
		description = "Documentation for Blog REST API v1.0"
))
public class SpringOnetomanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOnetomanyApplication.class, args);
	}

}
