package com.example.carolineboeykens.mobileseriedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create intent for activity
                Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                //get text en set to string
                EditText searchSerieEditText = (EditText) findViewById(R.id.searchSerieEditText);
                Editable searchSerieString = searchSerieEditText.getText();
                //pass information to next activity
                searchIntent.putExtra("example.carolineboeykens.mobileseriedatabase.SERIE", "" + searchSerieString);
                //start activity
                startActivity(searchIntent);
            }
        });

        Button testBtn = (Button) findViewById(R.id.clickBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputText = (TextView) findViewById(R.id.outputText);

                APIFetchData getData = new APIFetchData();
                getData.execute();

            }
        });

    }
}
