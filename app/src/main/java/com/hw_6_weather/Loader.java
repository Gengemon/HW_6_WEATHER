package com.hw_6_weather;


import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Loader {

    public Loader() {

    }

    public static String downloadData(Long cityId) throws IOException {
        InputStream inputStream = null;
        String urlCity = null;
        String inputStreamXML = null;
        String stringXML = null;
        try {
            URL url = new URL(urlCity);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            Log.i("Loader", "Downloaded XML");
            return IOUtils.toString(inputStream, "UTF-8");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


}
