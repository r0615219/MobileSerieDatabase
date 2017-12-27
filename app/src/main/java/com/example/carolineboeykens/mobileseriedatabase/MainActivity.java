package com.example.carolineboeykens.mobileseriedatabase;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static TextView outputText;
    ListView listviewShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewShows = (ListView) findViewById(R.id.listviewShows);
        final customAdapter customAdapter = new customAdapter(this);
        listviewShows.setAdapter(customAdapter);

        //button clicked
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get text en set to string
                EditText searchSerieEditText = (EditText) findViewById(R.id.searchSerieEditText);
                String searchSerieString = (String) searchSerieEditText.getText().toString();

                //AsyncTask DoInBackground
                APIFetchData getData = new APIFetchData();
                getData.execute(searchSerieString);

            }
        });

    }
}

class singleShow {
    String name;
    String premieredDate;

    singleShow(String Name, String PremieredDate) {
        this.name = Name;
        this.premieredDate = PremieredDate;
    }
}

class customAdapter extends BaseAdapter {

    public static ArrayList<singleShow> shows = new ArrayList<singleShow>();
    Context c;

    customAdapter(Context context){
        c = context;
        shows = new ArrayList<singleShow>();
    }

    @Override
    public int getCount() {
        //return names.length;
        return shows.size();
    }

    @Override
    public Object getItem(int i) {
        //return names[i];
        return shows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = layoutInflater.inflate(R.layout.single_show, null);

        TextView textShowName = (TextView) v.findViewById(R.id.textShowName);
        TextView textPremieredDate = (TextView) v.findViewById(R.id.textPremieredDate);

        singleShow tmp = shows.get(i);

        textShowName.setText(tmp.name);
        textPremieredDate.setText(tmp.premieredDate);

        return v;
    }
}
