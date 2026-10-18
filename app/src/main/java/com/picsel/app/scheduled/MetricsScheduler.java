package com.picsel.app.scheduled;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import io.micrometer.core.instrument.MeterRegistry;

public class MetricsScheduler {
    private MeterRegistry registry;

    MetricsScheduler(MeterRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(fixedRate = 1000)
    @Async
    public void saveMetrics() {
        this.registry.forEachMeter(consumer -> consumer.getId().getType());
    }
}
