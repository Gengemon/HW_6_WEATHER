package com.hw_6_weather;

import android.app.Application;

public class MainApplication extends Application {

    private Settings settings;

    @Override
    public void onCreate() {
        settings = new Settings(this);
        super.onCreate();
    }

    public Settings getSettings() {
        return settings;
    }
}
