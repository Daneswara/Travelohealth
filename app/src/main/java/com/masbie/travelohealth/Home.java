package com.masbie.travelohealth;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private LinearLayout fl, f2, f3, f4, f5;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fl.setVisibility(View.VISIBLE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    setTitle("Transaksi");
                    return true;
                case R.id.navigation_layanan:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.VISIBLE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    setTitle("Layanan");
                    return true;
                case R.id.navigation_dokter:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.VISIBLE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    setTitle("Dokter");
                    return true;
                case R.id.navigation_kamar:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    f4.setVisibility(View.VISIBLE);
                    setTitle("Kamar");
                    return true;
                case R.id.navigation_akun:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.VISIBLE);
                    setTitle("Kamar");
                    return true;
            }
            return false;
        }

    };
    String text_no_antrian[] = {"8"};
    String text_antrian_saat_ini[] = {"2"};
    String text_estimasi_pelayanan[] = {"1 jam 2 menit"};
    String text_estimasi_perjalanan[] = {"16 menit"};
    int id_transaksi[] = {1};
    Integer image_transaksi[] = {R.drawable.klinikpenyakitdalam};

    String text_pelayanan[] = {"Poli Umum", "Poli Gigi"};
    String text_jamkerja[] = {"07.00-12.00", "12.00-16.00"};
    int id_layanan[] = {1, 2};
    Integer image_layanan[] = {R.drawable.klinikpenyakitdalam, R.drawable.klinikgigi};

    String text_dokter[] = {"Dokter Spesialis Organ Dalam", "Dokter Spesialis Gigi"};
    String text_jampraktek[] = {"07.00-09.00", "19.00-21.00"};
    int id_dokter[] = {1, 2};
    Integer image_dokter[] = {R.drawable.dokterl, R.drawable.dokterp};

    String text_kamar[] = {"Kelas III", "Kelas II", "Kelas I", "Kelas VIP", "Kelas VVIP"};
    String harga[] = {"Rp. 110.000", "Rp. 440.000", "Rp. 550.000", "Rp. 880.000", "Rp. 1.870.000"};
    String fasilitas[] = {"1 Kamar 5 Tempat tidur \n Kursi", "1 Kamar 2 Tempat tidur \n Kursi", "1 Kamar 1 Tempat tidur \n Kursi", "1 Kamar 1 Tempat tidur \n Lemari \n Kursi \n TV", "1 Kamar 1 Tempat tidur \n Tempat makan \n Lemari \n Kursi \n TV"};
    int id_kamar[] = {1, 2, 3, 4, 5};
    Integer image_kamar[] = {R.drawable.kelas31, R.drawable.kelas22, R.drawable.kelas11, R.drawable.vip1, R.drawable.vvip};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fl = (LinearLayout) findViewById(R.id.transaksi);
        f2 = (LinearLayout) findViewById(R.id.layanan);
        f3 = (LinearLayout) findViewById(R.id.dokter);
        f4 = (LinearLayout) findViewById(R.id.kamar);
        f5 = (LinearLayout) findViewById(R.id.akun);
        fl.setVisibility(View.VISIBLE);
        f2.setVisibility(View.GONE);
        f3.setVisibility(View.GONE);
        f4.setVisibility(View.GONE);
        f5.setVisibility(View.GONE);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemBackgroundResource(R.color.colorPrimaryDark);
        navigation.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        navigation.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        AdapterTransaksiSekarang androidListAdapter = new AdapterTransaksiSekarang(this, image_transaksi, text_no_antrian, text_antrian_saat_ini, text_estimasi_pelayanan, text_estimasi_perjalanan, id_transaksi);
        ListView androidListView = (ListView) findViewById(R.id.custom_listview_transaksi_sekarang);
        androidListView.setAdapter(androidListAdapter);

        AdapterDokter androidListDokter = new AdapterDokter(this, image_dokter, text_dokter, text_jampraktek, id_dokter);
        ListView androidListViewDokter = (ListView) findViewById(R.id.custom_listview_dokter);
        androidListViewDokter.setAdapter(androidListDokter);

        AdapterLayanan androidListLayanan = new AdapterLayanan(this, image_layanan, text_pelayanan, text_jamkerja, id_layanan);
        ListView androidListViewLayanan = (ListView) findViewById(R.id.custom_listview_layanan);
        androidListViewLayanan.setAdapter(androidListLayanan);

        AdapterKamar androidListKamar = new AdapterKamar(this, image_kamar, text_kamar, harga, fasilitas, id_kamar);
        ListView androidListViewKamar = (ListView) findViewById(R.id.custom_listview_kamar);
        androidListViewKamar.setAdapter(androidListKamar);

        TextView logout = (TextView) findViewById(R.id.keluar);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
