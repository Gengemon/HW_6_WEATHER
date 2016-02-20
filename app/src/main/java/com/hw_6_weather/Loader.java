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

    public static String downloadData(String urlCityXML) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlCityXML);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            Log.i("downloadData", "url.openConnection()");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();
            Log.i("downloadData", "connect()");
            inputStream = httpURLConnection.getInputStream();
            Log.i("downloadData", "Download url");
            return IOUtils.toString(inputStream, "UTF-8");
            //return "222";
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


}
