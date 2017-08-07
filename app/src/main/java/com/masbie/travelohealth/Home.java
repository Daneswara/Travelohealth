package com.masbie.travelohealth;

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

    private LinearLayout fl, f2, f3, f4;

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
                    setTitle("Transaksi");
                    return true;
                case R.id.navigation_layanan:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.VISIBLE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.GONE);
                    setTitle("Layanan");
                    return true;
                case R.id.navigation_dokter:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.VISIBLE);
                    f4.setVisibility(View.GONE);
                    setTitle("Dokter");
                    return true;
                case R.id.navigation_kamar:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.VISIBLE);
                    setTitle("Kamar");
                    return true;
            }
            return false;
        }

    };
    String androidListViewStrings[] = {"Android ListView Example", "Android Custom ListView Example", "Custom ListView Example"};

    Integer image_id[] = {R.color.colorAccent, R.color.colorAccent, R.color.colorAccent};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fl = (LinearLayout) findViewById(R.id.transaksi);
        f2 = (LinearLayout) findViewById(R.id.layanan);
        f3 = (LinearLayout) findViewById(R.id.dokter);
        f4 = (LinearLayout) findViewById(R.id.kamar);
        fl.setVisibility(View.VISIBLE);
        f2.setVisibility(View.GONE);
        f3.setVisibility(View.GONE);
        f4.setVisibility(View.GONE);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        AdapterTransaksiSekarang androidListAdapter = new AdapterTransaksiSekarang(this, image_id, androidListViewStrings);
        ListView androidListView = (ListView) findViewById(R.id.custom_listview_transaksi_sekarang);
        androidListView.setAdapter(androidListAdapter);

        AdapterDokter androidListDokter = new AdapterDokter(this, image_id, androidListViewStrings);
        ListView androidListViewDokter = (ListView) findViewById(R.id.custom_listview_dokter);
        androidListViewDokter.setAdapter(androidListDokter);
    }

}
