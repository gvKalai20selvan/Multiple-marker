package com.samplejsonparsing.adapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.samplejsonparsing.R;
import com.samplejsonparsing.model.CountryData;

import java.util.ArrayList;
public class CustomAdapterForListView extends BaseAdapter {
    private Activity activity;
    private ArrayList<CountryData> countryDataArrayList;
    private LayoutInflater inflater;


    public CustomAdapterForListView(Activity activity, ArrayList<CountryData> countryDataArrayList) {
        this.activity = activity;
        this.countryDataArrayList = countryDataArrayList;
    }
    public int getCount() {
        return countryDataArrayList.size();
    }

    public Object getItem(int location) {
        return countryDataArrayList.get(location);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_adapter_view, null);
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.iv_country_flag);
        TextView title = (TextView) convertView.findViewById(R.id.tv_country_name);
        Glide.with(activity).load(countryDataArrayList.get(position).getCou_image()).into(thumbNail);
        title.setText(countryDataArrayList.get(position).getCou_name());
        return convertView;
    }
}
