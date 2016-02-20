package com.hw_6_weather;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

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

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        transaction.replace(R.id.fragment_container, fragmentListCities);
        //transaction.addToBackStack(null);
        Log.i("addToBackStack onCreate", ""+ getFragmentManager().getBackStackEntryCount());
        transaction.commit();
    }

    public void nextFragment(View v){
        FragmentTransaction transaction;
        transaction = getFragmentManager().beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        if(fragmentListCities.isVisible()){
            transaction.replace(R.id.fragment_container, fragmentWeather);
            Log.i("nextFragment", "fragmentWeather");
        }else{
            if(fragmentWeather.isVisible()){
                transaction.replace(R.id.fragment_container, fragmentWeatherWeb);
                Log.i("nextFragment", "fragmentWeatherWeb");
            }
            else {
                transaction.replace(R.id.fragment_container, fragmentListCities);
                Log.i("nextFragment", "fragmentListCities");
            }
        }
        transaction.addToBackStack(null);
        Log.i("addToBackStack nextFragment", "" + getFragmentManager().getBackStackEntryCount());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Log.i("addToBackStack","onBackPressed()");
        if (getFragmentManager().getBackStackEntryCount() > 0){
            Log.i("addToBackStack","popBackStack()"+getFragmentManager().getBackStackEntryCount());
            getFragmentManager().popBackStack();
        }
        else {
            Log.i("addToBackStack","super.onBackPressed()");
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(long cityId) {
        Log.i("onFragmentInteraction", "onFragmentInteraction cityId = " + cityId);
        Bundle extras = new Bundle();
        extras.putLong(MainActivity.CITY_ID, cityId);
        fragmentWeather.setArguments(extras);
        nextFragment(null);
    }

    @Override
    public void onFragmentInteractionWeather(String urlWeatherWeb) {
        Log.i("onFragmentInteractionW", "urlWeatherWeb = " + urlWeatherWeb);
        Bundle extras = new Bundle();
        extras.putString(MainActivity.URL_WEATHER_WEB, urlWeatherWeb);
        fragmentWeatherWeb.setArguments(extras);
        nextFragment(null);
    }
}
