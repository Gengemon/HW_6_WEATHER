package com.hw_6_weather;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    ExpandableListView expandableListView;
    AdapterCountryExpList countryExpListAdapter;

    private Fragment fragment1;
    private Fragment fragment2;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new FragmentListCities();
        fragment2 = new FragmentWeather();

        transaction = getFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        transaction.replace(R.id.fragment, fragment1);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void onClick(View v){
        transaction = getFragmentManager().beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        if(fragment1.isVisible()){
            transaction.replace(R.id.fragment, fragment2);
            Log.i("parser", "fragment2");
        }else{
            transaction.replace(R.id.fragment, fragment1);
            Log.i("parser", "fragment1");
        }
        transaction.commit();
    }

}
