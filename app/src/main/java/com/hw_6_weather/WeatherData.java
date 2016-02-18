package com.hw_6_weather;


public class WeatherData {

    private String city;
    private String temperature;
    private String temperatureColor;
    private String weatherType;
    private String windSpeed;
    private String link;

    public WeatherData(){

    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setTemperatureColor(String temperatureColor) {
        this.temperatureColor = temperatureColor;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getLink() {
        return link;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTemperatureColor() {
        return temperatureColor;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public String getWindSpeed() {
        return windSpeed;
    }


}
