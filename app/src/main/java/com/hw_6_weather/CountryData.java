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

        public void addCity(String city, int id){
            CityData cityData = new CityData(city, id);
            cityList.add(cityData);
        }
    }

