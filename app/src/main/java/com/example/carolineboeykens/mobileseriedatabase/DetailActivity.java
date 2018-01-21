package com.example.carolineboeykens.mobileseriedatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    FirebaseUser user;
    ListView listviewEpisodes;
    AnotherAdapter anotherAdapter;
    Button addSerie;
    private String serieName;
    private String serieId;

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

        //getshowId
        int showId = temporary.id;

        //output
        TextView showNameTextView = findViewById(R.id.showNameTextView);
        showNameTextView.setText(showName);

        //save showName and showId in variables to use in handleSaveData
        serieName = showName;
        serieId = Integer.toString(showId);

        //get episodesById - AsyncTask DoInBackground
        APIFetchEpisodesById getEpisodes = new APIFetchEpisodesById();
        getEpisodes.execute(showId);

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
    public void handleSaveData(View view) {

        DatabaseReference favoritesRef = databaseReference.child("favorites");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String Id = UUID.randomUUID().toString();
        String userId = user.getUid();

        Map<String, SaveData> favorites = new HashMap<>();
        favorites.put(serieName, new SaveData(Id, serieId, userId));

        favoritesRef.push().setValue(favorites);

        addSerie = (Button) findViewById(R.id.addSerie);
        addSerie.setText("Remove from favorites");
        addSerie.setOnClickListener(null);

    }


}
