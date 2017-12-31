package com.example.carolineboeykens.mobileseriedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent in = new Intent();
        int index = in.getIntExtra("mobile.mobileseriedatabase.ID", -1);

        TextView showNameTextView = findViewById(R.id.showNameTextView);
        showNameTextView.setText("Id : " + index);

    }
}
