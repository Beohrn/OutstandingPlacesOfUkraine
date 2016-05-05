package com.example.alexander.outstandingplacesofukraine;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import com.example.alexander.outstandingplacesofukraine.fragment.ListFragment;
import com.firebase.client.Firebase;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout coordinatorLayout;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getSupportActionBar().setTitle("KievPlaces");
        Firebase.setAndroidContext(getApplicationContext());

        PrimaryDrawerItem museums = new PrimaryDrawerItem().withName("Museums").withTextColor(Color.WHITE);
        PrimaryDrawerItem castles = new PrimaryDrawerItem().withName("Castles").withTextColor(Color.WHITE);
        PrimaryDrawerItem theaters = new PrimaryDrawerItem().withName("Theaters").withTextColor(Color.WHITE);
        PrimaryDrawerItem places = new PrimaryDrawerItem().withName("Places")
                .withTextColor(Color.WHITE)
                .withOnDrawerItemClickListener(new  Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        return false;
                    }
                });
        PrimaryDrawerItem parks = new PrimaryDrawerItem().withName("Parks").withTextColor(Color.WHITE);
        PrimaryDrawerItem chapels = new PrimaryDrawerItem().withName("Chapels").withTextColor(Color.WHITE);


        Drawer builder = new DrawerBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .withDrawerLayout(R.layout.material_drawer_fits_not)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(places, museums, castles, theaters, parks,chapels)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        if (isNetworkConnected()) {

            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.frgmCont, new ListFragment(getApplicationContext(), "outstpl"));
            transaction.commit();

        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Network connection is lost", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return (manager.getActiveNetworkInfo() != null);
    }


}
