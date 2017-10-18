package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.masbie.travelohealth.object.Dokter;
import com.masbie.travelohealth.object.Pesan;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
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
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    BottomNavigationView navigation;

    public AdapterDokter(Activity context, List<Dokter> daftar_dokter) {
        super(context, R.layout.layout_dokter_listview, daftar_dokter);
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.daftar_dokter = daftar_dokter;
        fl = (LinearLayout) context.findViewById(R.id.transaksi);
        f2 = (LinearLayout) context.findViewById(R.id.dokter);
        navigation = context.findViewById(R.id.navigation);
    }

    FirebaseStorage storage = FirebaseStorage.getInstance();


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
        jam.setText(daftar_dokter.get(i).hari.toUpperCase() + "\n" + daftar_dokter.get(i).jampraktek);

        StorageReference storageRef = storage.getReference().child("images/" + daftar_dokter.get(i).gambar);
        ImageView mimageView = viewRow.findViewById(R.id.image_view);
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(mimageView);
        if (!cekKetersediaan(daftar_dokter.get(i).hari, daftar_dokter.get(i).jampraktek)) {
            pesan.setText("TUTUP");
            pesan.setBackgroundColor(Color.GRAY);
            pesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Layanan TUTUP!")
                            .setContentText("Anda dapat mencoba lagi ketika layanan telah dibuka.")
                            .show();
                }
            });
        } else {
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
                                public void onClick(final SweetAlertDialog sDialog) {
                                    Calendar calendar = Calendar.getInstance();
                                    String tanggal = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
                                    mDatabase.child("antrian").child(tanggal).child(daftar_dokter.get(i).poli).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Calendar calendar = Calendar.getInstance();
                                            String tanggal = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
                                            calendar.add(Calendar.HOUR, 2);
                                            if(!dataSnapshot.hasChild("antrian")){
                                                mDatabase.child("antrian").child(tanggal).child(daftar_dokter.get(i).poli).child("antrian").setValue(1);
                                                mDatabase.child("antrian").child(tanggal).child(daftar_dokter.get(i).poli).child("proses").setValue(1);
                                                Pesan pesanan = new Pesan(daftar_dokter.get(i).id, 1, calendar.getTimeInMillis(), "proses", mAuth.getCurrentUser().getUid(), daftar_dokter.get(i).gambar);
                                                mDatabase.child("antrian").child(tanggal).child(daftar_dokter.get(i).poli).child(String.valueOf(Calendar.getInstance().getTimeInMillis())).setValue(pesanan);
                                                sDialog
                                                        .setTitleText("Berhasil!")
                                                        .setContentText("Anda telah masuk dalam antrian!")
                                                        .setConfirmText("OK")
                                                        .showCancelButton(false)
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            } else {
                                                mDatabase.child("antrian").child(tanggal).child(daftar_dokter.get(i).poli).child("antrian").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Calendar calendar = Calendar.getInstance();
                                                        String tanggal = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
                                                        calendar.add(Calendar.HOUR, 2);
                                                        mDatabase.child("antrian").child(tanggal).child(daftar_dokter.get(i).poli).child("antrian").setValue((long)dataSnapshot.getValue()+1);
                                                        Pesan pesanan = new Pesan(daftar_dokter.get(i).id, (long)dataSnapshot.getValue()+1, calendar.getTimeInMillis(), "antri", mAuth.getCurrentUser().getUid(), daftar_dokter.get(i).gambar);
                                                        mDatabase.child("antrian").child(tanggal).child(daftar_dokter.get(i).poli).child(String.valueOf(Calendar.getInstance().getTimeInMillis())).setValue(pesanan);
                                                        fl.setVisibility(View.VISIBLE);
                                                        f2.setVisibility(View.GONE);
                                                        navigation.setSelectedItemId(R.id.navigation_home);
                                                        sDialog
                                                                .setTitleText("Berhasil!")
                                                                .setContentText("Anda telah masuk dalam antrian!")
                                                                .setConfirmText("OK")
                                                                .showCancelButton(false)
                                                                .setConfirmClickListener(null)
                                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });

                                            }
                                            //subscripe
                                            FirebaseMessaging.getInstance().subscribeToTopic(daftar_dokter.get(i).poli);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                                }
                            })
                            .show();
                }
            });
        }

        return viewRow;
    }

    public boolean cekKetersediaan(String hari, String waktu) {
        waktu = waktu.replace(".", "titik");
        hari = hari.replace(" ", "");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String semuahari[] = hari.split(",");
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
        boolean cek_hari = false;
        for (int i = 0; i < semuahari.length; i++) {
            if (semuahari[i].equalsIgnoreCase(hari_now)) {
                cek_hari = true;
            }
        }
        String[] waktu_tersedia = waktu.split("-");
        System.out.println(waktu_tersedia[0]);
        System.out.println(waktu_tersedia[1]);
        String[] waktuawal = waktu_tersedia[0].split("titik");
        System.out.println(waktuawal[0]);
        System.out.println(waktuawal[1]);
        String[] waktuakhir = waktu_tersedia[1].split("titik");
        int jam = calendar.get(Calendar.HOUR_OF_DAY);
        int menit = calendar.get(Calendar.MINUTE);
        int konv_waktu = jam * 60 + menit;
        int konv_waktuawal = Integer.parseInt(waktuawal[0]) * 60 + Integer.parseInt(waktuawal[1]);
        int konv_waktuakhir = Integer.parseInt(waktuakhir[0]) * 60 + Integer.parseInt(waktuakhir[1]);
        boolean cek_waktu = konv_waktu >= konv_waktuawal && konv_waktu <= konv_waktuakhir;
        System.out.println("cek hari" + cek_hari);
        System.out.println("cek waktu" + cek_waktu);
        return cek_hari && cek_waktu;
    }
}
