package com.hw_6_weather;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public class ParserXML {

    public static final String URL_XML_WEATHER_DATA = "http://export.yandex.ru/weather-ng/forecasts/%d.xml";

    private static final String KEY_COUNTRY = "country";
    private static final String KEY_COUNTRY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_CITY_ID = "id";

    private static final String KEY_WEATHER_CITY = "city";
    private static final String KEY_WEATHER_LINK = "link";
    private static final String KEY_WEATHER_FORECAST = "forecast";
    private static final String KEY_WEATHER_TEMPERATURE = "temperature";
    private static final String KEY_WEATHER_TEMPERATURE_COLOR = "color";
    private static final String KEY_WEATHER_TYPE = "weather_type";
    private static final String KEY_WEATHER_WIND_SPEED = "wind_speed";

    public ParserXML(){

    }

    public static String getUrlCityXML(long cityId){
        return String.format(URL_XML_WEATHER_DATA, cityId);
    }

    public ArrayList<CountryData> getCountryList(Context context, int xmlResourceId){
        String country;
        String city;
        int id;
        CountryData countryData;
        ArrayList<CountryData> countryList = new ArrayList();

        try {
            XmlPullParser parser = context.getResources().getXml(xmlResourceId);

            Log.i("ParserXML.getCountryList", "Loaded XML");
            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals(KEY_COUNTRY)) {
                    country = parser.getAttributeValue(null, KEY_COUNTRY_NAME);
                    countryData = new CountryData(country);
                    countryList.add(countryData);
                    Log.i("ParserXML.getCountryList","country = "+country);
                }
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals(KEY_CITY)) {
                    id = Integer.parseInt(parser.getAttributeValue(null, KEY_CITY_ID));
                    parser.next();
                    if ( parser.getEventType() == XmlPullParser.TEXT ){
                        city = parser.getText();
                        countryList.get(countryList.size()-1).addCity(city, id);
                        Log.i("ParserXML.getCountryListd","city  = " + city + " id = " +  id);
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

    public static WeatherData getWeatherInfo(String xmlData) {
        WeatherData weatherData = new WeatherData();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            BufferedReader reader = new BufferedReader(new StringReader(xmlData));
            parser.setInput(reader);

            Log.i("ParserXML.getWeatherInfo", "Loaded XML weather");
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if(parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_FORECAST)){
                    weatherData.setLink(parser.getAttributeValue(null, KEY_WEATHER_LINK));
                    Log.i("ParserXML.getWeatherInfo.weather", weatherData.getLink());
                    weatherData.setCity(parser.getAttributeValue(null, KEY_WEATHER_CITY));
                    Log.i("ParserXML.getWeatherInfo.weather", weatherData.getCity());
                }
                if(parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_TEMPERATURE)){
                    weatherData.setTemperatureColor(parser.getAttributeValue(null, KEY_WEATHER_TEMPERATURE_COLOR));
                    Log.i("ParserXML.getWeatherInfo.weather", weatherData.getTemperatureColor());
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.TEXT){
                        weatherData.setTemperature(parser.getText());
                        Log.i("ParserXML.getWeatherInfo.weather", weatherData.getTemperature());
                    }
                }
                if (parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_TYPE)){
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.TEXT) {
                        weatherData.setWeatherType(parser.getText());
                        Log.i("ParserXML.getWeatherInfo.weather", weatherData.getWeatherType());
                    }
                }
                if (parser.getEventType()==XmlPullParser.START_TAG && parser.getName().equals(KEY_WEATHER_WIND_SPEED)){
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.TEXT) {
                        weatherData.setWindSpeed(parser.getText());
                        Log.i("ParserXML.getWeatherInfo.weather", weatherData.getWindSpeed());
                        break;
                    }
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

        return weatherData;
    }
}
