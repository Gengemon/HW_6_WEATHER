package com.hw_6_weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CountryExpListAdapter extends BaseExpandableListAdapter {

    ArrayList<CountryData> countryList;
    private Context context;

    CountryExpListAdapter(Context context, ArrayList<CountryData> countryList){
        this.countryList = countryList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return countryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return countryList.get(groupPosition).getCityList().size();
    }

    @Override
    public CountryData getGroup(int groupPosition) {
        return countryList.get(groupPosition);
    }

    @Override
    public CityData getChild(int groupPosition, int childPosition) {
        return countryList.get(groupPosition).getCityList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return countryList.get(groupPosition).getCityList().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_group, null);
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.text_group);
        textGroup.setText(getGroup(groupPosition).getCountry());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_child, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.text_child);
        textChild.setText(getChild(groupPosition,childPosition).getCity());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
