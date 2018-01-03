package com.example.carolineboeykens.mobileseriedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    ListView listviewShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewShows = (ListView) findViewById(R.id.listViewShows);
        final customAdapter customAdapter = new customAdapter(this);
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

}
