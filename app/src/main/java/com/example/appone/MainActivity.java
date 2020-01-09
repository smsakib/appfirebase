package com.example.appone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME="artistname";
    public static final String ARTIST_ID="artistid";

    EditText editTextArtistName;
    Button buttonAdd;
    Spinner spinnerGenere;

    ListView listViewMain;

    List<Artist> artistList;

    DatabaseReference databaseReferenceArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReferenceArtist = FirebaseDatabase.getInstance().getReference("artist");

        editTextArtistName = findViewById(R.id.editTextName);
        buttonAdd = findViewById(R.id.buttonAddArtist);
        spinnerGenere = findViewById(R.id.spinnerGenres);
        listViewMain = findViewById(R.id.listViewArtists);

        artistList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtish();
            }
        });

        listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.get(position);
                Intent intent = new Intent(getApplicationContext(),AddTrackActivity.class);
                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artistList.clear();
                for (DataSnapshot artistSnapshot: dataSnapshot.getChildren()){

                    Artist artist = artistSnapshot.getValue(Artist.class);

                    artistList.add(artist);

                }

                ArtistList artistListAdapter = new ArtistList(MainActivity.this,artistList);
                listViewMain.setAdapter(artistListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ShowUpdateDialog(String artistId, String artistName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);
        final TextView textViewUpdateName = dialogView.findViewById(R.id.textViewUpdateName);
        final EditText editTextUpdateText = dialogView.findViewById(R.id.editTextUpdateName);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
        


    }

    private void addArtish(){
        String name = editTextArtistName.getText().toString().trim();
        String genere = spinnerGenere.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)){

            String id = databaseReferenceArtist.push().getKey();

            Artist artist = new Artist(id,name,genere);

            databaseReferenceArtist.child(id).setValue(artist);

            Toast.makeText(this,"Artist added",Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this,"you should enter name",Toast.LENGTH_SHORT).show();
        }
    }
}
