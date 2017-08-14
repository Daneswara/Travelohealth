package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterKamar extends ArrayAdapter {
    String[] androidListViewStrings, harga, fasilitas;
    int[] idkamar;
    Integer[] imagesId;
    Context context;

    public AdapterKamar(Activity context, Integer[] imagesId, String[] textListView, String[] harga, String[] fasilitas, int[] idkamar) {
        super(context, R.layout.layout_kamar_listview, textListView);
        this.androidListViewStrings = textListView;
        this.harga = harga;
        this.fasilitas = fasilitas;
        this.idkamar = idkamar;
        this.imagesId = imagesId;
        this.context = context;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_kamar_listview, null,
                true);
        TextView mtextView = (TextView) viewRow.findViewById(R.id.kelaskamar);
        TextView harga = (TextView) viewRow.findViewById(R.id.harga);
        TextView fasilitas = (TextView) viewRow.findViewById(R.id.fasilitas);
        TextView pesan = (TextView) viewRow.findViewById(R.id.pesanDokter);
        harga.setText(this.harga[i]);
        fasilitas.setText(this.fasilitas[i]);
        mtextView.setText(androidListViewStrings[i]);
        ImageView mimageView = (ImageView) viewRow.findViewById(R.id.image_view);
        mimageView.setImageResource(imagesId[i]);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Kamar ke-"+idkamar[i]);
                Intent intent = new Intent(context, PesanKamar.class);
                context.startActivity(intent);
            }
        });
        return viewRow;
    }
}
