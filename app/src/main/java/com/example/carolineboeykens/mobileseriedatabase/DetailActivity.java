package com.example.carolineboeykens.mobileseriedatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    ListView listviewEpisodes;
    AnotherAdapter anotherAdapter;

    @SuppressLint("StaticFieldLeak")
    public class APIFetchEpisodesById extends AsyncTask<Integer, Void, String> {
        String data = "";
        String result = "";

        @Override
        protected String doInBackground(Integer... showId) {

            try {
                AnotherAdapter.episodes.clear();

                //api url
                result = "http://api.tvmaze.com/shows/" + showId[0] + "/episodes";
                URL url = new URL(result);

                //connect to url
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();

                //read data from url
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                //get data and store it in variable
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                //read data line by line
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                    //get the data inside the object
                    int episodeId = (int) jsonObject.getInt("id");
                    String singleEpisodeName = (String) jsonObject.getString("name");
                    int singleEpisodeSeason = (int) jsonObject.getInt("season");
                    int singleEpisodeNumber = (int) jsonObject.getInt("number");

                    //add the data to the listview
                    AnotherAdapter.episodes.add(new SingleEpisode(episodeId, singleEpisodeName, singleEpisodeSeason, singleEpisodeNumber));
                }
                anotherAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //listview episodes - adapter
        listviewEpisodes = (ListView) findViewById(R.id.episodeList);
        anotherAdapter = new AnotherAdapter(this);
        listviewEpisodes.setAdapter(anotherAdapter);
        anotherAdapter.notifyDataSetChanged();

        Intent in = getIntent();

        //get listitem id
        int index = in.getIntExtra("mobile.mobileseriedatabase.ID", -1);

        //get showname
        SingleShow temporary = CustomAdapter.shows.get(index);
        String showName = temporary.name;

        //output
        TextView showNameTextView = findViewById(R.id.showNameTextView);
        showNameTextView.setText(showName);

        //getshowId
        int showId = temporary.id;

        //get episodesById - AsyncTask DoInBackground
        APIFetchEpisodesById getEpisodes = new APIFetchEpisodesById();
        getEpisodes.execute(showId);


    }

}
