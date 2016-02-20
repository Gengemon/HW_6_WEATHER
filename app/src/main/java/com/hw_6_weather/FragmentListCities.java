package com.hw_6_weather;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
            Log.i("onFragmentInteraction", " (OnFragmentInteractionListener) activity");
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString()
                    + " The MainActivity activity must " +
                    "implement OnContactSelectedListener");
        }
        Log.i("onFragmentInteraction", " onAttach end");
        return viewFragment;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String city = countryExpListAdapter.getChild(groupPosition,childPosition).getCity();
        Long cityId = countryExpListAdapter.getChildId(groupPosition, childPosition);

        Log.i("onFragmentInteraction", " before onChildClick");
        //Toast.makeText(getActivity(),"cityId = " + cityId.toString(),Toast.LENGTH_SHORT).show();
        onFragmentInteractionListener.onFragmentInteraction(cityId);
        Log.i("onFragmentInteraction", "after onChildClick");
        return true;
    }

    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(long cityId);
    }


}
