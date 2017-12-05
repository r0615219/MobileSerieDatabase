package com.example.carolineboeykens.mobileseriedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getIntent().hasExtra("example.carolineboeykens.mobileseriedatabase.SERIE")){
            //get textview
            TextView serieNameTextView = (TextView) findViewById(R.id.serieNametextView);
            //get extras from previous activity
            String serieName = getIntent().getExtras().getString("example.carolineboeykens.mobileseriedatabase.SERIE");
            //set text
            serieNameTextView.setText(serieName);
        }

    }
}
