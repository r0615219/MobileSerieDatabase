package com.example.carolineboeykens.mobileseriedatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by carolineboeykens on 30/12/2017.
 */

class SingleShow {
    int id;
    String name;
    String premieredDate;
    String description;

    SingleShow(int Id, String Name, String PremieredDate, String Description) {
        this.id = Id;
        this.name = Name;
        this.premieredDate = PremieredDate;
        this.description = Description;
    }
}

class CustomAdapter extends BaseAdapter {

    static ArrayList<SingleShow> shows = new ArrayList<SingleShow>();
    Context c;

    CustomAdapter(Context context){
        c = context;
        shows = new ArrayList<SingleShow>();
    }

    @Override
    public int getCount() {
        return shows.size();
    }

    @Override
    public Object getItem(int i) {
        return shows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder")
        View v = layoutInflater.inflate(R.layout.single_show, null);

        TextView textShowName = (TextView) v.findViewById(R.id.textShowName);
        TextView textPremieredDate = (TextView) v.findViewById(R.id.textPremieredDate);

        SingleShow tmp = shows.get(i);

        textShowName.setText(tmp.name);
        textPremieredDate.setText(tmp.premieredDate);

        return v;
    }
}

