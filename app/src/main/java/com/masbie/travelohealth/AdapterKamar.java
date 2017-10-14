package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.masbie.travelohealth.object.Kamar;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterKamar extends ArrayAdapter {
    List<Kamar> daftar_kamar;
    Context context;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public AdapterKamar(Activity context, List<Kamar> daftar_kamar) {
        super(context, R.layout.layout_kamar_listview, daftar_kamar);
        this.daftar_kamar = daftar_kamar;
        this.context = context;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_kamar_listview, null,
                true);
        TextView mtextView = viewRow.findViewById(R.id.kelaskamar);
        TextView harga = viewRow.findViewById(R.id.harga);
        TextView fasilitas = viewRow.findViewById(R.id.fasilitas);
        TextView pesan = viewRow.findViewById(R.id.pesanDokter);
        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        harga.setText(currencyFormatter.format(daftar_kamar.get(i).harga) + "/hari");
        fasilitas.setText(daftar_kamar.get(i).fasilitas);
        mtextView.setText(daftar_kamar.get(i).kelas);

        StorageReference storageRef = storage.getReference().child("images/"+daftar_kamar.get(i).gambar);
        ImageView mimageView = viewRow.findViewById(R.id.image_view);
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(mimageView);

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PesanKamar.class);
                context.startActivity(intent);
            }
        });


        return viewRow;
    }
}
