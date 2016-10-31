package com.example.samy.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class CustomListAdapter extends BaseAdapter{


    private ArrayList<DailyListItems> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<DailyListItems> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
    return listData.size();
    }

    @Override
    public Object getItem(int position) {
    return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
    return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.itinerary_row, null);
            holder = new ViewHolder();
            holder.place = (TextView) convertView.findViewById(R.id.placeText);
            holder.method = (TextView) convertView.findViewById(R.id.methodText);
            holder.cost = (TextView) convertView.findViewById(R.id.costText);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.place.setText(listData.get(position).getPlace());
        holder.method.setText(listData.get(position).getMethod());
        holder.cost.setText(listData.get(position).getCost().toString());

        return convertView;
    }

    static class ViewHolder {
    TextView place;
    TextView method;
    TextView cost;
    }
}
