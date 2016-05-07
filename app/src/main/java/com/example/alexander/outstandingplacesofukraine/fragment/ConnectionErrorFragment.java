package com.example.alexander.outstandingplacesofukraine.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexander.outstandingplacesofukraine.R;


public class ConnectionErrorFragment extends Fragment {

    private TextView tryItNow;
    private Context context;
    private FragmentTransaction transaction;
    private String category;

    public ConnectionErrorFragment() {
    }

    public ConnectionErrorFragment(Context context, String category) {
        this.context = context;
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.field_internet_connection_fragment, container, false);

        tryItNow = (TextView) view.findViewById(R.id.tryItNow);
        tryItNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.listContainer, new ListFragment(context, category));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (manager.getActiveNetworkInfo() != null);
    }


}
