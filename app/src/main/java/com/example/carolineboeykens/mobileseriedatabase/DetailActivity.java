package com.example.carolineboeykens.mobileseriedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ListView listviewEpisodes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent in = getIntent();

        //get listitem id
        int index = in.getIntExtra("mobile.mobileseriedatabase.ID", -1);

        //get showname
        singleShow temporary = customAdapter.shows.get(index);
        String showName = temporary.name;

        //output
        TextView showNameTextView = findViewById(R.id.showNameTextView);
        showNameTextView.setText(showName);

        //getshowId
        int showId = temporary.id;

        //listview episodes - adapter
        listviewEpisodes = (ListView) findViewById(R.id.episodeList);
        final anotherAdapter anotherAdapter = new anotherAdapter(this);
        listviewEpisodes.setAdapter(anotherAdapter);
        //episodesAdapter.notifyDataSetChanged();
        anotherAdapter.episodes.add(new singleEpisode(412, "Episode test name", 00, 00));

        //get episodesById - AsyncTask DoInBackground
        APIFetchEpisodesById getEpisodes = new APIFetchEpisodesById();
        getEpisodes.execute(showId);


    }

}
