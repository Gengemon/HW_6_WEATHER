package com.hw_6_weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {

    ExpandableListView expandableListView;
    CountryExpListAdapter countryExpListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParserXMLToCountryData pars = new ParserXMLToCountryData(this);

        countryExpListAdapter = new CountryExpListAdapter(getApplicationContext(),pars.getCountryList());
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(countryExpListAdapter);

        expandableListView.setOnChildClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String city = countryExpListAdapter.getChild(groupPosition,childPosition).getCity();
        Long city_id = countryExpListAdapter.getChildId(groupPosition, childPosition);

        Toast.makeText(getApplicationContext(), city + " " + city_id, Toast.LENGTH_SHORT).show();
        //citySelectedCallback.onFragmentInteraction(guid);
        return true;
    }
}
