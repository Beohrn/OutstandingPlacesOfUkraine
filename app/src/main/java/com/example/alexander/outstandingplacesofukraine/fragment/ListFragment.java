package com.example.alexander.outstandingplacesofukraine.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.alexander.outstandingplacesofukraine.R;
import com.example.alexander.outstandingplacesofukraine.adapter.ImageAdapter;
import com.example.alexander.outstandingplacesofukraine.pojo.Places;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.view.ViewProcessor;
import com.github.rahatarmanahmed.cpv.CircularProgressView;


import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private List<Places> places;
    private GridView gridView;
    private Context context;
    private ImageAdapter adapter;
    private Firebase firebase;

    public ListFragment() {
    }

    public ListFragment(Context context, List<Places> places) {
        this.context = context;
        this.places = new ArrayList<>();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        firebase = new Firebase("https://kievplaces.firebaseio.com/outstpl");
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        final CircularProgressView circularProgressView = (CircularProgressView) view.findViewById(R.id.progress_view);
        circularProgressView.setColor(Color.RED);
        circularProgressView.startAnimation();
        gridView = (GridView) view.findViewById(R.id.gridview);

        final List<Places> list = new ArrayList<>();

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Places place = snapshot.getValue(Places.class);
                    list.add(place);
                }

                adapter = new ImageAdapter(context, list);
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                circularProgressView.stopAnimation();
                circularProgressView.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return view;
    }


}
