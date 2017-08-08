package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterLayanan extends ArrayAdapter {
    String[] androidListViewStrings;
    Integer[] imagesId;
    Context context;

    public AdapterLayanan(Activity context, Integer[] imagesId, String[] textListView) {
        super(context, R.layout.layout_layanan_listview, textListView);
        this.androidListViewStrings = textListView;
        this.imagesId = imagesId;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_layanan_listview, null,
                true);
        ImageView mimageView = (ImageView) viewRow.findViewById(R.id.image_view);
        mimageView.setImageResource(imagesId[i]);
        return viewRow;
    }
}
