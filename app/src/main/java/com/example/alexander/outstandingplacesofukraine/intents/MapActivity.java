package com.example.alexander.outstandingplacesofukraine.intents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.alexander.outstandingplacesofukraine.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {

    private GoogleMap map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        Intent intent = getIntent();
        final double latitude = intent.getDoubleExtra("latitude", 0);
        final double longitude = intent.getDoubleExtra("longitude", 0);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapActivity)).getMap();
        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
    }
}
