package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.masbie.travelohealth.object.Poli;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterLayanan extends ArrayAdapter {
    Context context;
    private LinearLayout fl, f2;
    List<Poli> daftar_poli;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public AdapterLayanan(Activity context, List<Poli> daftar_poli) {
        super(context, R.layout.layout_layanan_listview, daftar_poli);
        this.daftar_poli = daftar_poli;
        this.context = context;
        fl = context.findViewById(R.id.transaksi);
        f2 = context.findViewById(R.id.layanan);
    }

    public boolean cek = true;
    private int pilihan = 0;
    private String layanan = "";

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_layanan_listview, null,
                true);
        TextView mtextView = viewRow.findViewById(R.id.namalayanan);
        TextView jam = viewRow.findViewById(R.id.jamkerja);
        TextView pesan = viewRow.findViewById(R.id.pesanDokter);


        mtextView.setText(daftar_poli.get(i).pelayanan);
        jam.setText(daftar_poli.get(i).hari.toUpperCase() + "\n" + daftar_poli.get(i).jamkerja);


        StorageReference storageRef = storage.getReference().child("images/" + daftar_poli.get(i).gambar);
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
                        .setContentText("Anda akan memesan layanan " + daftar_poli.get(i).pelayanan + "!")
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
                                sDialog.setTitleText("Berhasil!")
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

    public boolean cekKetersediaan(String hari, String waktu){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String hari_now = "";
        switch (day) {
            case Calendar.SUNDAY:
                hari_now = "minggu";
                break;
            case Calendar.MONDAY:
                hari_now = "senin";
                break;
            case Calendar.TUESDAY:
                hari_now = "selasa";
                break;
            case Calendar.WEDNESDAY:
                hari_now = "rabu";
                break;
            case Calendar.THURSDAY:
                hari_now = "kamis";
                break;
            case Calendar.FRIDAY:
                hari_now = "jum'at";
                break;
            case Calendar.SATURDAY:
                hari_now = "sabtu";
                break;
            default:
                hari_now = "tidak ada";
        }
        boolean cek_hari = hari.equalsIgnoreCase(hari_now);
        String[] waktu_tersedia = waktu.split("-");
        return true;
    }
}
