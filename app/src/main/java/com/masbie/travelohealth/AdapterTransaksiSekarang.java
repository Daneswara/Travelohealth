package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterTransaksiSekarang extends ArrayAdapter {
    String[] text_no_antrian, text_antrian_saat_ini, text_estimasi_pelayanan, text_estimasi_perjalanan;
    Integer[] imagesId;
    Context context;
    int[] idtransaksi;

    public AdapterTransaksiSekarang(Activity context, Integer[] imagesId, String[] text_no_antrian, String[] text_antrian_saat_ini, String[] text_estimasi_pelayanan, String[] text_estimasi_perjalanan, int[] idtransaksi) {
        super(context, R.layout.layout_transaksi_listview, text_no_antrian);
        this.text_no_antrian = text_no_antrian;
        this.text_antrian_saat_ini = text_antrian_saat_ini;
        this.text_estimasi_pelayanan = text_estimasi_pelayanan;
        this.text_estimasi_perjalanan = text_estimasi_perjalanan;
        this.imagesId = imagesId;
        this.idtransaksi = idtransaksi;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_transaksi_listview, null,
                true);
        TextView mtextView = (TextView) viewRow.findViewById(R.id.urutan);
        ImageView mimageView = (ImageView) viewRow.findViewById(R.id.image_view);
        TextView antriansaatini = (TextView) viewRow.findViewById(R.id.antrianke);
        TextView pelayanan = (TextView) viewRow.findViewById(R.id.estimasipelayanan);
        TextView perjalanan = (TextView) viewRow.findViewById(R.id.estimasiperjalanan);
        Button detail = (Button) viewRow.findViewById(R.id.detail);
        mtextView.setText("No. "+text_no_antrian[i]);
        antriansaatini.setText("Saat ini antrian ke-"+text_antrian_saat_ini[i]);
        pelayanan.setText(text_estimasi_pelayanan[i]);
        perjalanan.setText(text_estimasi_perjalanan[i]);
        mimageView.setImageResource(imagesId[i]);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTransaksi.class);
                context.startActivity(intent);
            }
        });
        return viewRow;
    }
}
