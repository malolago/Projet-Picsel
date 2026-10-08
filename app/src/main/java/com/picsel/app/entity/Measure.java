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

    public Measure(Double temperature, Double pressure, Date timestamp, String device) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.timestamp = timestamp;
        this.device = device;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
