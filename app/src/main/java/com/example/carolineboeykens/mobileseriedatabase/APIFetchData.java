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
 * Created by carolineboeykens on 25/12/2017.
 */

public class APIFetchData extends AsyncTask<String, String, String> {

    String data = "";
    String result = "";

    //ArrayList<singleShow> shows;

    @Override
    protected String doInBackground(String... searchSerieString) {

        try {
            customAdapter.shows.clear();

            //api url
            result = "http://api.tvmaze.com/search/shows?q=" + searchSerieString[0];
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

                //get the show object
                JSONObject show = jsonObject.getJSONObject("show");

                //get the data inside the show object
                String name = (String) show.getString("name");
                String premieredDate = (String) show.getString("premiered");
                
                //add the data to the listview
                customAdapter.shows.add(new singleShow(name, premieredDate));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}