package com.picsel.app.repositories;

import com.picsel.app.entity.Measure;
import org.springframework.data.repository.CrudRepository;

public interface MeasureRepository extends CrudRepository<Measure, Long> {
}
