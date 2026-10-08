package com.picsel.app;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picsel.app.entity.Measure;
import com.picsel.app.repositories.MeasureRepository;

@RestController
@SpringBootApplication
public class AppApplication {
	private final MeasureRepository repository;

	public AppApplication(MeasureRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/")
	public Iterable<Measure> get() {
		repository.save(new Measure(1.0, 1.0, new Date(), "test"));
		return repository.findAll();
		// return "Hello, World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
