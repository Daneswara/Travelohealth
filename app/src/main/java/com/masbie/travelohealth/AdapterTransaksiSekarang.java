package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterTransaksiSekarang extends ArrayAdapter {
    String[] androidListViewStrings;
    Integer[] imagesId;
    Context context;

    public AdapterTransaksiSekarang(Activity context, Integer[] imagesId, String[] textListView) {
        super(context, R.layout.layout_transaksi_listview, textListView);
        this.androidListViewStrings = textListView;
        this.imagesId = imagesId;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_transaksi_listview, null,
                true);
        TextView mtextView = (TextView) viewRow.findViewById(R.id.text_view);
        ImageView mimageView = (ImageView) viewRow.findViewById(R.id.image_view);
        mtextView.setText(androidListViewStrings[i]);
        mimageView.setImageResource(imagesId[i]);
        return viewRow;
    }
}
