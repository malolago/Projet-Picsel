package com.picsel.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;

@RestController
@SpringBootApplication
public class AppApplication {
	private final MeterRegistry meterRegistry;

	public AppApplication(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	@GetMapping("/")
	public String get() {
		return "Hello, World!";
	}

	@GetMapping("/metrics")
	public String test() {
		return meterRegistry.getMeters().stream().toList().toString();
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
