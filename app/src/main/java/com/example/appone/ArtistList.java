package com.example.appone;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ArtistList extends ArrayAdapter<Artist> {

    private Activity activity;
    private List<Artist> artistList;

    public ArtistList(Activity activity, List<Artist> artistList){
        super(activity, R.layout.list_layout,artistList);
        this.activity = activity;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName = listItemView.findViewById(R.id.textViewName);
        TextView textViewGenere = listItemView.findViewById(R.id.textViewGenre);

        Artist artist = artistList.get(position);

        textViewName.setText(artist.getArtistName());
        textViewGenere.setText(artist.getArtistGenere());

        return listItemView;

    }
}
