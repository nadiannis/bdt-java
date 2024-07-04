package com.nadiannis.spring_docker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringDockerApplication {

	@Value("${spring.application.name}")
	private String appName;

	@RequestMapping("/")
	public String home() {
		return "Hello, World! I'm from " + appName + ". I'm learning Docker here!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDockerApplication.class, args);
	}

}
