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
    private static final int CONNECT_TIMEOUT = 10000;
    public Loader() {

    }

    public static String downloadData(String urlCityXML) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlCityXML);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            return IOUtils.toString(inputStream, "UTF-8");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


}
