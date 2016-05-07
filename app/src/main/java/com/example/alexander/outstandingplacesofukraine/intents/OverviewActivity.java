package com.example.alexander.outstandingplacesofukraine.intents;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v7.graphics.Palette;


import com.bumptech.glide.Glide;
import com.example.alexander.outstandingplacesofukraine.R;
import com.example.alexander.outstandingplacesofukraine.fragment.InfoFragment;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class OverviewActivity extends AppCompatActivity {

    private final String TAG = "UsingFirebase";

    private int PLACE_PICKER_REQUEST = 188;
    private GoogleMap map;

    private LayoutInflater inflater;
    private GoogleApiClient client;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_overview);

        client = new GoogleApiClient
                .Builder(this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .addApi(AppIndex.API).build();

        //-------------------
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String info = intent.getStringExtra("info");
        final double latitude = intent.getDoubleExtra("latitude", 0);
        final double longitude = intent.getDoubleExtra("longitude", 0);
        //--------------------

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(name);

        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.background);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int mutedColor = palette.getVibrantSwatch().getRgb();
                collapsingToolbarLayout.setContentScrimColor(mutedColor);
                collapsingToolbarLayout.setStatusBarScrimColor(mutedColor);

            }
        });

        Glide.with(getApplicationContext()).load(intent.getStringExtra("url").toString()).into(imageView);

        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.infoFragment, new InfoFragment(info));
        transaction.commit();


        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        client.connect();

    }

    @Override
    protected void onStop() {
        client.disconnect();
        super.onStop();
    }
}
