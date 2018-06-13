package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao;

public class Main {
    private double temp;
    private double pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;

    public Main(double temp, double pressure, int humidity, double temp_min, double temp_max) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTempMin() {
        return temp_min;
    }

    public void setTempMin(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTempMax() {
        return temp_max;
    }

    public void setTempMax(double temp_max) {
        this.temp_max = temp_max;
    }
}
