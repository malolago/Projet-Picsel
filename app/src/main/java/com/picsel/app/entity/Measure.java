package com.picsel.app.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getDevice() {
        return device;
    }

    private String device;

    public Long getId() {
        return id;
    }

    public Double getTemperature() {
        return temperature;
    }

    private Double temperature;

    public Double getPressure() {
        return pressure;
    }

    private Double pressure;

    public Date getTimestamp() {
        return timestamp;
    }

    private Date timestamp;

    protected Measure() {
    }

    public Measure(Double temperature, Double pressure) {
        this.temperature = temperature;
        this.pressure = pressure;
    }
}
