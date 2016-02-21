package com.hw_6_weather;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class FragmentListCities extends Fragment  implements ExpandableListView.OnChildClickListener {


    private OnFragmentInteractionListener onFragmentInteractionListener;
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
        try {
            onFragmentInteractionListener = (OnFragmentInteractionListener) this.getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString()
                    + " The MainActivity activity must " +
                    "implement OnContactSelectedListener");
        }

        //read default city id from settings and open group in expandable list
        long cityId=((MainApplication) getActivity().getApplication()).getSettings().getCityId();
        int groupPosition = countryExpListAdapter.getGroupPositionByCityId(cityId);
        expandableListView.expandGroup(groupPosition);
        expandableListView.setSelectedGroup(groupPosition);

        return viewFragment;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Long cityId = countryExpListAdapter.getChildId(groupPosition, childPosition);
        Log.i("FragmentListCities.onChildClick", " cityId = " + cityId + ", groupPosition=" + groupPosition + ", childPosition=" + childPosition+", id="+id);
        onFragmentInteractionListener.onFragmentInteraction(cityId);
        return true;
    }

    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(long cityId);
    }


}
