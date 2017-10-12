package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.masbie.travelohealth.object.Dokter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterDokter extends ArrayAdapter {
    List<Dokter> daftar_dokter;
    Context context;
    private LinearLayout fl, f2;

    public AdapterDokter(Activity context, List<Dokter> daftar_dokter) {
        super(context, R.layout.layout_dokter_listview, daftar_dokter);
        this.context = context;
        this.daftar_dokter = daftar_dokter;
        fl = (LinearLayout) context.findViewById(R.id.transaksi);
        f2 = (LinearLayout) context.findViewById(R.id.dokter);

    }

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public boolean cek = true;
    private int pilihan = 0;
    private String dokter = "";

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_dokter_listview, null,
                true);
        TextView mtextView = (TextView) viewRow.findViewById(R.id.namadokter);
        TextView jam = (TextView) viewRow.findViewById(R.id.jampraktek);
        TextView pesan = (TextView) viewRow.findViewById(R.id.pesanDokter);
        mtextView.setText(daftar_dokter.get(i).nama);
        jam.setText("Praktek \n " + daftar_dokter.get(i).jampraktek);

        StorageReference storageRef = storage.getReference().child("images/"+daftar_dokter.get(i).gambar);
        ImageView mimageView = viewRow.findViewById(R.id.image_view);
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(mimageView);

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apa anda yakin?")
                        .setContentText("Anda akan memesan " + daftar_dokter.get(i).nama + "!")
                        .setConfirmText("Ya, saya yakin!")
                        .setCancelText("Batal")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                fl.setVisibility(View.VISIBLE);
                                f2.setVisibility(View.GONE);
                                sDialog
                                        .setTitleText("Berhasil!")
                                        .setContentText("Anda telah masuk dalam antrian!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        });

        return viewRow;
    }
}
