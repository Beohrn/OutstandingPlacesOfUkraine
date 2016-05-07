package com.example.alexander.outstandingplacesofukraine;

import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.alexander.outstandingplacesofukraine.fragment.ConnectionErrorFragment;
import com.example.alexander.outstandingplacesofukraine.fragment.ListFragment;
import com.firebase.client.Firebase;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    private LinearLayout coordinatorLayout;
    private FragmentTransaction transaction;
    private Drawer builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_main);
        getSupportActionBar().setTitle("KievPlaces");
        Firebase.setAndroidContext(getApplicationContext());

        if (isNetworkConnected()) {
            PrimaryDrawerItem museums = new PrimaryDrawerItem().withName("Museums").withTextColor(Color.WHITE)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            choseCategory("museums");
                            return false;
                        }
                    });
            PrimaryDrawerItem castles = new PrimaryDrawerItem().withName("Castles").withTextColor(Color.WHITE)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            choseCategory("castles");
                            return false;
                        }
                    });
            PrimaryDrawerItem theaters = new PrimaryDrawerItem().withName("Theaters").withTextColor(Color.WHITE)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            choseCategory("theaters");
                            return false;
                        }
                    });
            PrimaryDrawerItem places = new PrimaryDrawerItem().withName("Places")
                    .withTextColor(Color.WHITE)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            choseCategory("outstpl");
                            return false;
                        }
                    });

            builder = new DrawerBuilder()
                    .withActivity(this)
                    .withSavedInstance(savedInstanceState)
                    .withActionBarDrawerToggle(true)
                    .withActionBarDrawerToggleAnimated(true)
                    .withDisplayBelowStatusBar(false)
                    .withTranslucentStatusBar(false)
                    .withDrawerLayout(R.layout.material_drawer_fits_not)
                    .withActionBarDrawerToggle(true)
                    .withActionBarDrawerToggleAnimated(true)
                    .addDrawerItems(places, museums, castles, theaters)
                    .build();


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setHomeButtonEnabled(false);
            //builder.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
//            new DrawerBuilder()
//                    .withActivity(this)
//                    .withSavedInstance(savedInstanceState)
//                    .withActionBarDrawerToggleAnimated(true)
//                    .withDisplayBelowStatusBar(false)
//                    .withTranslucentStatusBar(false)
//                    .withDrawerLayout(R.layout.material_drawer_fits_not)
//                    .withActionBarDrawerToggle(true)
//                    .withActionBarDrawerToggleAnimated(true)
//                    .addDrawerItems(places, museums, castles, theaters)
//                    .append(builder);

            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.listContainer, new ListFragment(getApplicationContext(), "outstpl"));
            transaction.commit();

        } else {
            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.listContainer, new ConnectionErrorFragment(getApplicationContext(), "outstpl"));
            transaction.commit();

        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return (manager.getActiveNetworkInfo() != null);
    }

    private void choseCategory(String category) {
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.listContainer, new ListFragment(getApplicationContext(), category));
        transaction.commit();
    }


}