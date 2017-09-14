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

import com.masbie.travelohealth.ObjectRoom.Room;
import com.masbie.travelohealth.api.ApiTravelohealth;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        initializeRetrofit();
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("login", 0);
        String token = pref.getString("token", "");

        Map<String, String> map = new HashMap<>();
        map.put("x-access-token", token);

        ApiTravelohealth apiService = retrofit.create(ApiTravelohealth.class);
        Call<Room> result = apiService.showRoom(map);
        result.enqueue(new Callback<Room>() {

            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                try {
                    if (response.body() != null) {
                        int panjang = response.body().getData().getResult().size();
                        for (int i = 0; i < panjang; i++){
                            System.out.println("id ke-"+i+" = "+response.body().getData().getResult().get(i).getId());
                        }
                    }
                } catch (Exception e){
                    System.out.println("ERROR:"+e);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {

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
