package com.hw_6_weather;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity  implements FragmentListCities.OnFragmentInteractionListener
                                                                ,FragmentWeather.OnFragmentInteractionListenerWeather{

    private FragmentListCities fragmentListCities;
    private FragmentWeather fragmentWeather;
    private FragmentWeatherWeb fragmentWeatherWeb;

    public static final String CITY_ID = "CITY_ID";
    public static final String URL_WEATHER_WEB = "URL_WEATHER_WEB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentListCities = new FragmentListCities();
        fragmentWeather = new FragmentWeather();
        fragmentWeatherWeb = new FragmentWeatherWeb();

        showFragment(fragmentListCities);
        Log.i("MainActivity.onCreate", "getBackStackEntryCount" + getFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void onStart() {
        super.onStart();
        long defaultCityId=((MainApplication) getApplication()).getSettings().getCityId();
        Log.i("MainActivity.onStart", "defaultCityId=" + defaultCityId);
        if (defaultCityId!=0){
            onFragmentInteraction(defaultCityId);
        }
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction;
        transaction = getFragmentManager().beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.i("MainActivity.showFragment", "2 getBackStackEntryCount()=" + getFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void onBackPressed() {
        Log.i("MainActivity","onBackPressed() - "+getFragmentManager().getBackStackEntryCount());
        if (getFragmentManager().getBackStackEntryCount() > 1){
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(long cityId) {
        Log.i("MainActivity.onFragmentInteraction", "onFragmentInteraction cityId = " + cityId);
        Bundle extras = new Bundle();
        extras.putLong(MainActivity.CITY_ID, cityId);
        fragmentWeather.setArguments(extras);
        showFragment(fragmentWeather);
    }

    @Override
    public void onFragmentInteractionWeather(String urlWeatherWeb) {
        Log.i("MainActivity.onFragmentInteractionW", "urlWeatherWeb = " + urlWeatherWeb);
        Bundle extras = new Bundle();
        extras.putString(MainActivity.URL_WEATHER_WEB, urlWeatherWeb);
        fragmentWeatherWeb.setArguments(extras);
        showFragment(fragmentWeatherWeb);
    }
}
