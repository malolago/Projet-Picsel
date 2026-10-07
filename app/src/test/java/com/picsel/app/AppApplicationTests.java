package com.picsel.app;

import com.picsel.app.entity.Measure;
import com.picsel.app.repositories.MeasureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppApplicationTests {

	@Autowired
	private MeasureRepository measureRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void savesMeasureWithGeneratedId() {
		Measure savedMeasure = measureRepository.save(new Measure(21.5, 101.3));
		Measure loadedMeasure = measureRepository.findById(savedMeasure.getId()).orElseThrow();

		assertThat(savedMeasure.getId()).isNotNull();
		assertThat(loadedMeasure.getTemperature()).isEqualTo(21.5);
		assertThat(loadedMeasure.getPressure()).isEqualTo(101.3);
	}

}
