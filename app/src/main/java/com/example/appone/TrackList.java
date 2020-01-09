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

public class TrackList extends ArrayAdapter<Track> {
    private Activity activity;
    private List<Track> tracksList;

    public TrackList(Activity activity, List<Track> tracksList){
        super(activity,R.layout.track_layout,tracksList);
        this.activity =activity;
        this.tracksList = tracksList;

    }


    @NonNull

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.track_layout,null,true);

        TextView textViewName = listItemView.findViewById(R.id.textViewName);
        TextView textViewRating = listItemView.findViewById(R.id.textViewRating);

        Track track = tracksList.get(position);

        textViewName.setText(track.getTrackname());
        textViewRating.setText(String.valueOf(track.getRating()));

        return listItemView;

    }
}
