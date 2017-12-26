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

    String singleParsed = "";
    String dataParsed = "";

    //ArrayList<singleShow> shows;

    @Override
    protected String doInBackground(String... searchSerieString) {

        try {
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
            while(line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            //DIT WERKT !!
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);
                singleParsed = "Show : " + jsonObject.get("show") + "\n";
                dataParsed = dataParsed + singleParsed;
            }

            //shows
            //shows = new ArrayList<singleShow>();

            //read data line by line
            /*JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                JSONObject show = jsonObject.getJSONObject("show");

                String name = (String) show.getString("name");
                String summary = (String) show.getString("summary");
                String premieredDate = (String) show.getString("premiered");

                //customAdapter.shows.add(new singleShow("Name", "PremieredDate", "Summary"));


                singleParsed = "Name : " + name + "\n" +
                               "Premiered : " + premieredDate + "\n" +
                               "Summary : " + summary + "\n";

                dataParsed = dataParsed + singleParsed;


            }*/

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        MainActivity.outputText.setText(dataParsed);
    }
}
