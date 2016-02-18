package com.hw_6_weather;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

public class ParserXML {

    private static final String KEY_COUNTRY = "country";
    private static final String KEY_COUNTRY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_CITY_ID = "id";


    private static final String KEY_WEATHER_CITY = "city";
    private static final String KEY_WEATHER_LINK = "city";
    private static final String KEY_WEATHER_FORECAST = "city";
    private static final String KEY_WEATHER_TEMPERATURE = "city";
    private static final String KEY_WEATHER_COLOR = "city";
    private static final String KEY_WEATHER_TYPE = "city";
    private static final String KEY_WEATHER_WINF_SPEED = "city";

    public ParserXML(){

    }

    public ArrayList<CountryData> getCountryList(Context context, int xmlResourceId){
        String country;
        String city;
        int id;
        CountryData countryData;
        ArrayList<CountryData> countryList = new ArrayList();

        try {
            XmlPullParser parser = context.getResources().getXml(xmlResourceId);

            Log.i("parser", "Loaded XML");
            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals(KEY_COUNTRY)) {
                    country = parser.getAttributeValue(null, KEY_COUNTRY_NAME);
                    countryData = new CountryData(country);
                    countryList.add(countryData);
                    Log.i("parser.country", country);
                }
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals(KEY_CITY)) {
                    id = Integer.parseInt(parser.getAttributeValue(null, KEY_CITY_ID));
                    parser.next();
                    if ( parser.getEventType() == XmlPullParser.TEXT ){
                        city = parser.getText();
                        countryList.get(countryList.size()-1).addCity(city, id);
                        Log.i("parser.city id", city + " " +  id);
                    }
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            Toast.makeText(context,
                    R.string.exception_parser_message + t.toString(), Toast.LENGTH_LONG)
                    .show();
        }
        return countryList;
    }

    public static WeatherData getWeatherInfo(Context context, String stringXML) {
        WeatherData weatherData = new WeatherData();
        String lang;
        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.weather_temp);
            Log.i("parser", "Loaded XML weather");
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if(parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_FORECAST)){
                    weatherData.setLink(parser.getAttributeValue(null, KEY_WEATHER_LINK));
                    Log.i("parser", weatherData.getLink());
                    weatherData.setCity(parser.getAttributeValue(null, KEY_WEATHER_CITY));
                    Log.i("parser", weatherData.getCity());
                }
                if(parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_TEMPERATURE)){
                    weatherData.setTemperatureColor(parser.getAttributeValue(null, KEY_WEATHER_COLOR));
                    Log.i("parser", weatherData.getTemperatureColor());
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.TEXT){
                        weatherData.setTemperature(parser.getText());
                        Log.i("parser", weatherData.getTemperature());
                    }
                }
                if (parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_TYPE)){
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.TEXT) {
                        weatherData.setWeatherType(parser.getText());
                        Log.i("parser", weatherData.getWeatherType());
                    }
                }
                if (parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_WINF_SPEED)){
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.TEXT) {
                        weatherData.setWindSpeed(parser.getText());
                        Log.i("parser", weatherData.getWindSpeed());
                        break;
                    }
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            Toast.makeText(context,
                    R.string.exception_parser_message + t.toString(), Toast.LENGTH_LONG)
                    .show();
        }
        return weatherData;
    }
}
