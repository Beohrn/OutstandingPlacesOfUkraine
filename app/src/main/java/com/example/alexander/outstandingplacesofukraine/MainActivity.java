package com.example.alexander.outstandingplacesofukraine;

import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.example.alexander.outstandingplacesofukraine.fragment.ListFragment;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "UsingFirebase";
    private final String URL = "https://kievplaces.firebaseio.com/outstpl";
    // private ImageAdapter adapter;
    private LinearLayout coordinatorLayout;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main);

        coordinatorLayout = (LinearLayout) findViewById(R.id.coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(getApplicationContext());

        if (isNetworkConnected()) {

            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.frgmCont, new ListFragment(getApplicationContext(), null));
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
