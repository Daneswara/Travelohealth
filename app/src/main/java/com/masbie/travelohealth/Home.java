package com.masbie.travelohealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.masbie.travelohealth.object.Dokter;
import com.masbie.travelohealth.object.Kamar;
import com.masbie.travelohealth.object.Poli;
import com.masbie.travelohealth.object.User;

import java.util.LinkedList;
import java.util.List;

public class Home extends AppCompatActivity {
    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    private LinearLayout fl, f2, f3, f4, f5;
    SharedPreferences sharedpreferences;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fl.setVisibility(View.VISIBLE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    setTitle("Transaksi");
                    editor.putInt("menu", 1);
                    editor.commit();
                    return true;
                case R.id.navigation_layanan:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.VISIBLE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    setTitle("Layanan");
                    editor.putInt("menu", 2);
                    editor.commit();
                    return true;
                case R.id.navigation_dokter:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.VISIBLE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    setTitle("Dokter");
                    editor.putInt("menu", 3);
                    editor.commit();
                    return true;
                case R.id.navigation_kamar:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.GONE);
                    f5.setVisibility(View.GONE);
                    f4.setVisibility(View.VISIBLE);
                    setTitle("Kamar");
                    editor.putInt("menu", 4);
                    editor.commit();
                    return true;
                case R.id.navigation_akun:
                    fl.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                    f3.setVisibility(View.GONE);
                    f4.setVisibility(View.GONE);
                    f5.setVisibility(View.VISIBLE);
                    setTitle("Kamar");
                    editor.putInt("menu", 5);
                    editor.commit();
                    return true;
            }
            return false;
        }

    };

    ListView androidListViewLayanan;
    ListView androidListViewDokter;
    ListView androidListViewKamar;

    String text_no_antrian[] = {"8"};
    String text_antrian_saat_ini[] = {"2"};
    String text_estimasi_pelayanan[] = {"1 jam 2 menit"};
    String text_estimasi_perjalanan[] = {"16 menit"};
    int id_transaksi[] = {1};
    Integer image_transaksi[] = {R.drawable.klinikpenyakitdalam};

    List<Poli> daftar_poli;
    List<Dokter> daftar_dokter;
    List<Kamar> daftar_kamar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getSharedPreferences("menu", Context.MODE_PRIVATE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent keluar = new Intent(Home.this, LoginActivity.class);
            startActivity(keluar);
            finish();
        } else {
            fl = findViewById(R.id.transaksi);
            f2 = findViewById(R.id.layanan);
            f3 = findViewById(R.id.dokter);
            f4 = findViewById(R.id.kamar);
            f5 = findViewById(R.id.akun);
            navigation = findViewById(R.id.navigation);
            navigation.setItemBackgroundResource(R.color.colorPrimaryDark);
            navigation.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
            navigation.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            cek_menu();

            final AdapterTransaksiSekarang androidListAdapter = new AdapterTransaksiSekarang(this, image_transaksi, text_no_antrian, text_antrian_saat_ini, text_estimasi_pelayanan, text_estimasi_perjalanan, id_transaksi);
            ListView androidListView = findViewById(R.id.custom_listview_transaksi_sekarang);
            androidListView.setAdapter(androidListAdapter);

            androidListViewDokter = findViewById(R.id.custom_listview_dokter);

            androidListViewLayanan = findViewById(R.id.custom_listview_layanan);

            androidListViewKamar = findViewById(R.id.custom_listview_kamar);

            TextView logout = findViewById(R.id.keluar);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    sharedpreferences.edit().clear();
                    sharedpreferences.edit().commit();
                    Intent intent = new Intent(Home.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            // ambil data poli
            daftar_poli = new LinkedList<>();
            daftar_kamar = new LinkedList<>();
            daftar_dokter = new LinkedList<>();
            mDatabase.child("poli").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    daftar_poli.add(dataSnapshot.getValue(Poli.class));
                    AdapterLayanan androidListLayanan = new AdapterLayanan(Home.this, daftar_poli);
                    androidListViewLayanan.setAdapter(androidListLayanan);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("dokter").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    daftar_dokter.add(dataSnapshot.getValue(Dokter.class));
                    AdapterDokter androidListDokter = new AdapterDokter(Home.this, daftar_dokter);
                    androidListViewDokter.setAdapter(androidListDokter);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase.child("kamar").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    daftar_kamar.add(dataSnapshot.getValue(Kamar.class));
                    AdapterKamar androidListKamar = new AdapterKamar(Home.this, daftar_kamar);
                    androidListViewKamar.setAdapter(androidListKamar);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TextView nama = findViewById(R.id.nama_pengguna);
                    TextView email = findViewById(R.id.email_pengguna);
                    User user = dataSnapshot.getValue(User.class);
                    nama.setText(user.name);
                    email.setText(user.email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
    BottomNavigationView navigation;
    public void cek_menu() {
        int menunya = sharedpreferences.getInt("menu", 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (menunya == 0) {
            editor.putInt("menu", 1);
            editor.commit();
            fl.setVisibility(View.VISIBLE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_home);
        } else if (menunya == 1) {
            fl.setVisibility(View.VISIBLE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_home);
        } else if (menunya == 2) {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.VISIBLE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_layanan);
        } else if (menunya == 3) {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.VISIBLE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_dokter);
        } else if (menunya == 4) {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.VISIBLE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_kamar);
        } else if (menunya == 5) {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.VISIBLE);
            navigation.setSelectedItemId(R.id.navigation_akun);
        }
    }

}
