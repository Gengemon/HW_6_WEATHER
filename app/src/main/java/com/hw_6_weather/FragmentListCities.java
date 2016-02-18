package com.hw_6_weather;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class FragmentListCities extends Fragment  implements ExpandableListView.OnChildClickListener {

    ExpandableListView expandableListView;
    AdapterCountryExpList countryExpListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_list_cities, container, false);

        ParserXML pars = new ParserXML();

        countryExpListAdapter = new AdapterCountryExpList(this.getActivity(),pars.getCountryList(this.getActivity(), R.xml.country_city));
        expandableListView = (ExpandableListView) viewFragment.findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(countryExpListAdapter);

        expandableListView.setOnChildClickListener(this);

        return viewFragment;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String city = countryExpListAdapter.getChild(groupPosition,childPosition).getCity();
        Long cityId = countryExpListAdapter.getChildId(groupPosition, childPosition);
        String weatherXML = null;
        WeatherData weatherData = new WeatherData();
        weatherData = ParserXML.getWeatherInfo(this.getActivity(), weatherXML);
        Toast.makeText(this.getActivity(), city + " " + cityId + " температура:" + weatherData.getTemperature(), Toast.LENGTH_SHORT).show();
        //citySelectedCallback.onFragmentInteraction(guid);
        return true;
    }
}
