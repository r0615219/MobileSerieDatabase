package com.example.carolineboeykens.mobileseriedatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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


public class MainActivity extends AppCompatActivity {

    ListView listviewShows;
    CustomAdapter customAdapter;

    @SuppressLint("StaticFieldLeak")
    public class APIFetchData extends AsyncTask<String, String, String> {

        String data = "";
        String result = "";

        @Override
        protected String doInBackground(String... searchSerieString) {

            try {
                CustomAdapter.shows.clear();

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

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            try {
                //read data line by line
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                    //get the show object
                    JSONObject show = jsonObject.getJSONObject("show");

                    //get the data inside the show object
                    int id = (int) show.getInt("id");
                    String name = (String) show.getString("name");
                    String premieredDate = (String) show.getString("premiered");
                    String description = (String) show.getString("summary");

                    //add the data to the listview
                    CustomAdapter.shows.add(new SingleShow(id, name, premieredDate, description));
                }
                customAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        //Intent Login = new Intent(this, LoginActivity.class);
        //startActivity(Login);

        listviewShows = (ListView) findViewById(R.id.listViewShows);
        customAdapter = new CustomAdapter(this);
        listviewShows.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();


        listviewShows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("mobile.mobileseriedatabase.ID", i);
                startActivity(showDetail);
            }
        });

        //button clicked
        Button searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get text en set to string
                EditText searchSerieEditText = findViewById(R.id.searchSerieEditText);
                String searchSerieString = searchSerieEditText.getText().toString();

                //AsyncTask DoInBackground
                APIFetchData getData = new APIFetchData();
                getData.execute(searchSerieString);
            }
        });

    }
    // ...




}
