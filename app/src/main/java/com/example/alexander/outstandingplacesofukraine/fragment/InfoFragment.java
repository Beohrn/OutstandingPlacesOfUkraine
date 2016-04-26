package com.example.alexander.outstandingplacesofukraine.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexander.outstandingplacesofukraine.R;

public class InfoFragment extends Fragment {

    private String info;
    private TextView infoText;
    public InfoFragment() {
    }

    public InfoFragment(String info) {
        this.info = info;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info_fragment, container, false);

        infoText = (TextView) view.findViewById(R.id.infoText);

        infoText.setText(info);

        return view;
    }
}
