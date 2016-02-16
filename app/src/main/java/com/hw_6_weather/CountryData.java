package com.hw_6_weather;

import java.util.ArrayList;

public class CountryData {

        private String country;
        private ArrayList<CityData> cityList = new ArrayList();

        public CountryData(String country) {
            this.country=country;
        }

        public String getCountry() {
            return country;
        }

        public ArrayList<CityData> getCityList() {
            return cityList;
        }
        public CityData getLastCity() {
            return cityList.get(cityList.size()-1);
        }

        public void addCity(String city, int id, String country){
            CityData cityData = new CityData(city, id, country);
            cityList.add(cityData);
        }
    }

