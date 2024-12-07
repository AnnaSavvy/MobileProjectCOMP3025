package com.anna.dailyroute;

public class WeatherResponse {
    private Main main;
    private String name;

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public class Main {
        private float temp;

        public float getTemp() {
            return temp;
        }
    }
}