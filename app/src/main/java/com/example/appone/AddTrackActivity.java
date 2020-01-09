package com.example.appone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    Button buttonAddTrack;

    ListView listViewTracks;

    List<Track> tracks;

    DatabaseReference databaseReferenceTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        textViewArtistName = findViewById(R.id.textViewArtist);
        editTextTrackName = findViewById(R.id.editTextNameTrack);
        seekBarRating = findViewById(R.id.seekBarRating);
        buttonAddTrack = findViewById(R.id.buttonAddTrack);

        listViewTracks = findViewById(R.id.listViewTracks);

        Intent intent = getIntent();

        tracks = new ArrayList<>();

        String artistid = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistName.setText(name);

        databaseReferenceTrack = FirebaseDatabase.getInstance().getReference("tracks").child(artistid);
        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceTrack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot trackSnapshot:dataSnapshot.getChildren()){
                    Track track = trackSnapshot.getValue(Track.class);
                    tracks.add(track);
                }
                TrackList trackListAdapter = new TrackList(AddTrackActivity.this,tracks);
                listViewTracks.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack() {

        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if (!TextUtils.isEmpty(trackName)){
            String databaseTrackid = databaseReferenceTrack.push().getKey();

            Track track = new Track(databaseTrackid,trackName,rating);

            databaseReferenceTrack.child(databaseTrackid).setValue(track);
            Toast.makeText(this,"successfull",Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this,"empty",Toast.LENGTH_SHORT).show();
        }
    }
}
