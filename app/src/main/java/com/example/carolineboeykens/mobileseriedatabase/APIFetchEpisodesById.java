package com.example.carolineboeykens.mobileseriedatabase;

import android.os.AsyncTask;

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

/**
 * Created by carolineboeykens on 31/12/2017.
 */

public class APIFetchEpisodesById extends AsyncTask<Integer, Void, String> {
    String data = "";
    String result = "";

    @Override
    protected String doInBackground(Integer... showId) {

        try {
            //episodesAdapter.episodes.clear();

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


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            //read data line by line
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                //get the data inside the object
                int episodeId = (int) jsonObject.getInt("id");
                String singleEpisodeName = (String) jsonObject.getString("name");

                //add the data to the listview
                //episodesAdapter.episodes.add(new singleEpisode(episodeId, singleEpisodeName));
                //episodesAdapter.episodes.add(new singleEpisode(101, "Error Name"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
