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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterDokter extends ArrayAdapter {
    String[] androidListViewStrings, jampraktek;
    int[] iddokter;
    Integer[] imagesId;
    Context context;
    private LinearLayout fl, f2;

    public AdapterDokter(Activity context, Integer[] imagesId, String[] textListView, String[] jampraktek, int[] iddokter) {
        super(context, R.layout.layout_dokter_listview, textListView);
        this.androidListViewStrings = textListView;
        this.jampraktek = jampraktek;
        this.iddokter = iddokter;
        this.imagesId = imagesId;
        this.context = context;
        fl = (LinearLayout) context.findViewById(R.id.transaksi);
        f2 = (LinearLayout) context.findViewById(R.id.dokter);

    }

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
        mtextView.setText(androidListViewStrings[i]);
        jam.setText("Praktek \n " + jampraktek[i]);
        ImageView mimageView = (ImageView) viewRow.findViewById(R.id.image_view);
        mimageView.setImageResource(imagesId[i]);

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apa anda yakin?")
                        .setContentText("Anda akan memesan " + androidListViewStrings[i] + "!")
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
