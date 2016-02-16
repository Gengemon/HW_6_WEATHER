package com.hw_6_weather;

public class CityData {

        private String city;
        private int id;

        public CityData (String city, int id, String country) {
            this.city = city;
            this.id = id;
        }

        public String getCity() {
            return city;
        }
        public void setCity(String city){
            this.city = city;
        }

        public int getId() {
            return id;
        }

    }

