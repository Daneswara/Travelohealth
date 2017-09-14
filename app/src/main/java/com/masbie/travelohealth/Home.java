package com.masbie.travelohealth;

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

import com.masbie.travelohealth.ObjectDoctor.Doctor;
import com.masbie.travelohealth.ObjectPelayanan.Poli;
import com.masbie.travelohealth.ObjectRoom.Room;
import com.masbie.travelohealth.api.ApiTravelohealth;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


    String text_kamar[] = {"Kelas III", "Kelas II", "Kelas I", "Kelas VIP", "Kelas VVIP"};
    String harga[] = {"Rp. 110.000", "Rp. 440.000", "Rp. 550.000", "Rp. 880.000", "Rp. 1.870.000"};
    String fasilitas[] = {"1 Kamar 5 Tempat tidur \n Kursi", "1 Kamar 2 Tempat tidur \n Kursi", "1 Kamar 1 Tempat tidur \n Kursi", "1 Kamar 1 Tempat tidur \n Lemari \n Kursi \n TV", "1 Kamar 1 Tempat tidur \n Tempat makan \n Lemari \n Kursi \n TV"};
    int id_kamar[] = {1, 2, 3, 4, 5};
    Integer image_kamar[] = {R.drawable.kelas31, R.drawable.kelas22, R.drawable.kelas11, R.drawable.vip1, R.drawable.vvip};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("login", 0);
        String token = pref.getString("token", null);
        if (token == null) {
            Intent intent = new Intent(Home.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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


        TextView logout = (TextView) findViewById(R.id.keluar);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initializeRetrofit();


        Map<String, String> map = new HashMap<>();
        map.put("x-access-token", token);

        ApiTravelohealth apiService = retrofit.create(ApiTravelohealth.class);
        Call<Doctor> result = apiService.showDoctor(map);
        result.enqueue(new Callback<Doctor>() {

            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                try {
                    if (response.body() != null) {
                        int jumlahdokter = response.body().getData().getResult().size();
                        // cek service ada atau tidak
                        for (int i = 0; i < response.body().getData().getResult().size(); i++) {
                            if (response.body().getData().getResult().get(i).getService().size() == 0) {
                                jumlahdokter--;
                            }
                        }

                        String text_dokter[] = new String[jumlahdokter];
                        String text_jampraktek[] = new String[jumlahdokter];
                        int id_dokter[] = new int[jumlahdokter];
                        Integer image_dokter[] = new Integer[jumlahdokter];
                        int index = 0;
                        for (int i = 0; i < response.body().getData().getResult().size(); i++) {
                            if (response.body().getData().getResult().get(i).getService().size() != 0) {
                                System.out.println("id ke-" + index + " = " + response.body().getData().getResult().get(index).getId());
                                text_dokter[index] = response.body().getData().getResult().get(index).getUsername();
                                id_dokter[index] = Integer.parseInt(response.body().getData().getResult().get(index).getId());
                                text_jampraktek[index] = response.body().getData().getResult().get(index).getService().get(0).getStart() + " - " +
                                        response.body().getData().getResult().get(index).getService().get(0).getEnd();
                                image_dokter[index] = R.drawable.dokterl;
                                index++;
                            }
                        }

                        AdapterDokter androidListDokter = new AdapterDokter(Home.this, image_dokter, text_dokter, text_jampraktek, id_dokter);
                        ListView androidListViewDokter = (ListView) findViewById(R.id.custom_listview_dokter);
                        androidListViewDokter.setAdapter(androidListDokter);
                    }
                } catch (Exception e) {
                    System.out.println("ERROR:" + e);
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                System.out.println("ERROR:" + t);
            }
        });

        Call<Poli> resultPoli = apiService.showPoli(map);
        resultPoli.enqueue(new Callback<Poli>() {

            @Override
            public void onResponse(Call<Poli> call, Response<Poli> response) {
                try {
                    if (response.body() != null) {
                        int jumlahpoli = response.body().getData().getResult().size();
                        // cek service ada atau tidak
                        for (int i = 0; i < response.body().getData().getResult().size(); i++) {
                            if (response.body().getData().getResult().get(i).getDoctors().size() == 0) {
                                jumlahpoli--;
                            }
                        }

                        String text_poli[] = new String[jumlahpoli];
                        String text_jampraktek[] = new String[jumlahpoli];
                        int id_poli[] = new int[jumlahpoli];
                        Integer image_poli[] = new Integer[jumlahpoli];
                        int index = 0;
                        for (int i = 0; i < response.body().getData().getResult().size(); i++) {
                            if (response.body().getData().getResult().get(i).getDoctors().size() != 0) {
                                System.out.println("id ke-" + index + " = " + response.body().getData().getResult().get(index).getId());
                                text_poli[index] = response.body().getData().getResult().get(index).getName();
                                id_poli[index] = Integer.parseInt(response.body().getData().getResult().get(index).getId());
                                text_jampraktek[index] = response.body().getData().getResult().get(index).getStart() + " - " +
                                        response.body().getData().getResult().get(index).getEnd();
                                image_poli[index] = R.drawable.klinikgigi;
                                index++;
                            }
                        }

                        AdapterLayanan androidListLayanan = new AdapterLayanan(Home.this, image_poli, text_poli, text_jampraktek, id_poli);
                        ListView androidListViewLayanan = (ListView) findViewById(R.id.custom_listview_layanan);
                        androidListViewLayanan.setAdapter(androidListLayanan);
                    }
                } catch (Exception e) {
                    System.out.println("ERROR:" + e);
                }
            }

            @Override
            public void onFailure(Call<Poli> call, Throwable t) {
                System.out.println("ERROR:" + t);
            }
        });

        Call<Room> resultRoom = apiService.showRoom(map);
        resultRoom.enqueue(new Callback<Room>() {

            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                try {
                    if (response.body() != null) {
                        int jumlahkamar = 0;
                        // cek service ada atau tidak
                        for (int i = 0; i < response.body().getData().getResult().size(); i++) {
                            jumlahkamar += response.body().getData().getResult().get(i).getClasses().size();
                        }

                        String text_kamar[] = new String[jumlahkamar];
                        String fasilitas[] = new String[jumlahkamar];
                        String harga[] = new String[jumlahkamar];
                        int id_kamar[] = new int[jumlahkamar];
                        Integer image_kamar[] = new Integer[jumlahkamar];
                        int index = 0;
                        for (int i = 0; i < response.body().getData().getResult().size(); i++) {
                            for (int y = 0; y < response.body().getData().getResult().get(i).getClasses().size(); y++) {
                                text_kamar[index] = response.body().getData().getResult().get(i).getName()+" - ";
                                text_kamar[index] = response.body().getData().getResult().get(i).getClasses().get(y).getName();
                                harga[index] = response.body().getData().getResult().get(i).getClasses().get(y).getCost();
                                fasilitas[index] = response.body().getData().getResult().get(i).getClasses().get(y).getFeature();
                                image_kamar[index] = R.drawable.kelas22;
                            }

                        }

                        AdapterKamar androidListKamar = new AdapterKamar(Home.this, image_kamar, text_kamar, harga, fasilitas, id_kamar);
                        ListView androidListViewKamar = (ListView) findViewById(R.id.custom_listview_kamar);
                        androidListViewKamar.setAdapter(androidListKamar);
                    }
                } catch (Exception e) {
                    System.out.println("ERROR:" + e);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                System.out.println("ERROR:" + t);
            }
        });
    }

    public static final String BASE_API_URL = "https://travelohealth.000webhostapp.com/m/api/";
    private Retrofit retrofit;

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
