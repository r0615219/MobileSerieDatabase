package com.example.carolineboeykens.mobileseriedatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by carolineboeykens on 31/12/2017.
 */

class SingleEpisode {
    int id;
    String name;
    int season;
    int number;

    SingleEpisode(int Id, String Name, int Season, int Number){
        this.id = Id;
        this.name = Name;
        this.season = Season;
        this.number = Number;
    }
}

class AnotherAdapter extends BaseAdapter {
    private DatabaseReference databaseReference;
    FirebaseUser user;

    static ArrayList<SingleEpisode> episodes = new ArrayList<SingleEpisode>();
    Context context;

    AnotherAdapter(Context Context){
        context = Context;
        episodes = new ArrayList<SingleEpisode>();
    }

    @Override
    public int getCount() {
        return episodes.size();
    }

    @Override
    public Object getItem(int position) {
        return episodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder")
        View v = layoutInflater.inflate(R.layout.single_episode, null);

        TextView textEpisodeName = (TextView) v.findViewById(R.id.episodeNameTextView);
        final SingleEpisode episode = episodes.get(position);
        textEpisodeName.setText(episode.season + " x " + episode.number + " : " + episode.name);
        final Button layout = (Button) v.findViewById(R.id.buttonEpisode);
        layout.setTag(episode.id + episode.name);
        layout.setText("Seen");
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference ref = databaseReference.child("seen");
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String Id = UUID.randomUUID().toString();
                final String userId = user.getUid();
                int episodeId = episode.id;
                //final String episodeIdString = Integer.toString(episodeId).trim();

                Map<String, SaveEpisodes> episodes_seen = new HashMap<>();
                episodes_seen.put(episode.season + " x " + episode.number + " : " + episode.name, new SaveEpisodes(Id, episode.id, userId));

                ref.push().setValue(episodes_seen);
                layout.setText("Unseen");


                /*ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            if (data.child(episodeIdString).exists()) {
                                layout.setText(episodeIdString);
                                layout.refreshDrawableState();
                            } else {

                                Map<String, SaveEpisodes> episodes_seen = new HashMap<>();
                                episodes_seen.put(episode.season + " x " + episode.number + " : " + episode.name, new SaveEpisodes(Id, episode.id, userId));

                                ref.push().setValue(episodes_seen);
                                layout.setText("Unseen");
                                layout.refreshDrawableState();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

            }
        });

        return v;

    }

}
