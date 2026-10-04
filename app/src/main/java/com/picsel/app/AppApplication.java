package com.picsel.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AppApplication {
	@GetMapping("/")
	public String get() {
		return "Hello, World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
