package com.hw_6_weather;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class ParserXMLToCountryData {

    private  ArrayList<CountryData> countryList = new ArrayList();

    public ParserXMLToCountryData(Context context){
        String country;
        String city;
        int id;
        CountryData countryData;
        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.country_city);

            Log.i("parser", "Loaded XML");
            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("country")) {
                    country = parser.getAttributeValue(null, "name");
                    countryData = new CountryData(country);
                    countryList.add(countryData);
                    Log.i("parser.country", country);
                }
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("city")) {
                    id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                    city = parser.getText();
                    country = parser.getAttributeValue(null, "country");
                    city = city + id + country;
                    countryList.get(countryList.size()-1).addCity(null, id, country);
                    Log.i("parser.city", city);
                }
                if ( parser.getEventType() == XmlPullParser.TEXT ){
                    city = parser.getText();
                    countryList.get(countryList.size()-1).getLastCity().setCity(city);
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            Toast.makeText(context,
                    R.string.exception_parser_message + t.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    public ArrayList<CountryData> getCountryList(){
        return countryList;
    }

}
