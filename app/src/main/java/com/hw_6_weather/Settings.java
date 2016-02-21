package com.hw_6_weather;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    private static final String DEFAULT_CITY_ID_KEY="CITY_ID";
    private static final int DEFAULT_CITY_ID=33345;
    private SharedPreferences sharedPreferences;
    public Settings(Application app) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
    }
    public void setCityId(long cityId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(DEFAULT_CITY_ID_KEY, cityId);
        editor.apply();
    }

    public long getCityId() {
        return sharedPreferences.getLong(DEFAULT_CITY_ID_KEY, DEFAULT_CITY_ID);
    }
}

