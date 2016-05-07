package com.example.alexander.outstandingplacesofukraine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alexander.outstandingplacesofukraine.R;
import com.example.alexander.outstandingplacesofukraine.intents.OverviewActivity;
import com.example.alexander.outstandingplacesofukraine.pojo.Places;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Places> places;

    private LayoutInflater inflater;


    public ImageAdapter(Context context, List<Places> places) {
        this.context = context;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.row, null);


        final ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
        View view = (View) convertView.findViewById(R.id.footer);
        TextView textView = (TextView) convertView.findViewById(R.id.name);
        final Places place = places.get(position);
        Glide.with(context)
                .load(place.getOverview().getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        String text = place.getName();

        if (text.length() >= 22) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            params.setMargins(16, 6, 16, 0);
            textView.setLayoutParams(params);
        }

        textView.setText(place.getName());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OverviewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", place.getOverview().getPhoto());
                intent.putExtra("name", place.getName());
                intent.putExtra("info", place.getOverview().getDescrption());
                intent.putExtra("latitude", place.getOverview().getCoordinates().getLatitude());
                intent.putExtra("longitude", place.getOverview().getCoordinates().getLongitude());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
