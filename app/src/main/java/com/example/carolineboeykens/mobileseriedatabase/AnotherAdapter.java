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
 * Created by carolineboeykens on 31/12/2017.
 */

class SingleEpisode {
    int id;
    String name;
    int season;
    int number;

    SingleEpisode(int Id, String Name, int Season, int Number){
        this.id = Id;
        this.name = Name;
        this.season = Season;
        this.number = Number;
    }
}

class AnotherAdapter extends BaseAdapter {

    static ArrayList<SingleEpisode> episodes = new ArrayList<SingleEpisode>();
    Context context;

    AnotherAdapter(Context Context){
        context = Context;
        episodes = new ArrayList<SingleEpisode>();
    }

    @Override
    public int getCount() {
        return episodes.size();
    }

    @Override
    public Object getItem(int position) {
        return episodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder")
        View v = layoutInflater.inflate(R.layout.single_episode, null);

        TextView textEpisodeName = (TextView) v.findViewById(R.id.episodeNameTextView);
        SingleEpisode episode = episodes.get(position);
        textEpisodeName.setText(episode.season + " x " + episode.number + " : " + episode.name);

        return v;

    }
}
