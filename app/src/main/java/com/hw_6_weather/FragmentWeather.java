package com.hw_6_weather;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;

public class FragmentWeather extends Fragment implements View.OnClickListener {

    private View viewFragment;
    private WeatherData weatherData;
    long defaultCityId;
    long cityId;

    private OnFragmentInteractionListenerWeather onFragmentInteractionListenerWeather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFragment = inflater.inflate(R.layout.fragment_weather, container, false);
        defaultCityId=((MainApplication) getActivity().getApplication()).getSettings().getCityId();
        Button buttonGoWeatherWeb = (Button) viewFragment.findViewById(R.id.button_go_weather_web);
        buttonGoWeatherWeb.setOnClickListener(this);
        try {
            onFragmentInteractionListenerWeather = (OnFragmentInteractionListenerWeather) this.getActivity();
            Log.i("FragmentWeather", " (OnFragmentInteractionListenerWeather) activity");
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString()
                    + " The MainActivity activity must " +
                    "implement OnContactSelectedListener");
        }
        return viewFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        // (0) get arguments +
        Bundle args = getArguments();
        if (args != null) {
            cityId = args.getLong(MainActivity.CITY_ID);
            Log.i("FragmentWeather.onStart", "cityId = "+cityId);
            // (1) test internet connection
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // (2) get URL city XML +
                String urlCityXML = ParserXML.getUrlCityXML(cityId);
                Log.i("FragmentWeather", "getLinkCityXML = " + urlCityXML);
                // (3) load XML from internet +
                GetXMLData task;
                String xmlData = null;
                try {
                    task = new GetXMLData();
                    task.execute(urlCityXML);
                    xmlData = task.get();
                    Log.i("FragmentWeather", "task.get() = " + xmlData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // (4) parse XML +
                weatherData = ParserXML.getWeatherInfo(xmlData);
                // (5) set data to view +
                updateViewData(weatherData);
            } else {
                Dialog.showAlert(viewFragment.getContext(), R.string.connection_error_title, R.string.connection_error_message);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.i("FragmentWeather.onClick", "onClick =" + v.getId());
        if(v.getId()==R.id.button_go_weather_web){
            onFragmentInteractionListenerWeather.onFragmentInteractionWeather(weatherData.getLink());
        }
    }

    @Override
    public void onDestroyView(){
        CheckBox checkBoxDefaultCity = (CheckBox) viewFragment.findViewById(R.id.checkbox_default_city);
        if (checkBoxDefaultCity.isChecked()){
            ((MainApplication)getActivity().getApplication()).getSettings().setCityId(cityId);
            defaultCityId=cityId;
            Log.i("FragmentWeather.onDestroyView", "setCityId(cityId) - " + cityId);
        }
        super.onDestroyView();
    }

    public class GetXMLData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... url) {
            String loadXML;
            try {
                Log.i("FragmentWeather.doInBackground", "url =" + url[0]);
                loadXML = Loader.downloadData(url[0]);
                return loadXML;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("FragmentWeather", "onPostExecute");
        }
    }
    private void updateViewData(WeatherData weatherData){
        TextView textCity = (TextView)  viewFragment.findViewById(R.id.weather_city);
        textCity.setText(weatherData.getCity());
        Log.i("FragmentWeather.updateViewData", "weather_city = " + weatherData.getCity());

        TextView textLink = (TextView) viewFragment.findViewById(R.id.weather_link);
        textLink.setText(weatherData.getLink());

        TextView textTemperature = (TextView) viewFragment.findViewById(R.id.weather_temperature);
        textTemperature.setText(weatherData.getTemperature().toString());

        int color = Color.parseColor("#"+weatherData.getTemperatureColor());
        textTemperature.setTextColor(color);

        TextView textWeatherType = (TextView) viewFragment.findViewById(R.id.weather_weather_type);
        textWeatherType.setText(weatherData.getWeatherType());

        TextView textWindSpeed = (TextView) viewFragment.findViewById(R.id.weather_wind_speed);
        textWindSpeed.setText(weatherData.getWindSpeed().toString());

        CheckBox checkBoxDefaultCity = (CheckBox) viewFragment.findViewById(R.id.checkbox_default_city);
        checkBoxDefaultCity.setChecked(cityId==defaultCityId);
    }

    public interface OnFragmentInteractionListenerWeather {

        public void onFragmentInteractionWeather(String urlWeatherWeb);
    }
}
