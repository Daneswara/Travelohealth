package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.maps.model.DirectionsResult;
import com.masbie.travelohealth.object.Antrian;
import com.masbie.travelohealth.pojo.service.ServiceQueueProcessedPojo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterTransaksiSekarang extends ArrayAdapter
{
    Context          context;
    DirectionsResult result;
    List<ServiceQueueProcessedPojo>    daftar_antrian;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private DateTimeZone zone    = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

    public AdapterTransaksiSekarang(Activity context, List<ServiceQueueProcessedPojo> daftar_antrian, DirectionsResult result)
    {
        super(context, R.layout.layout_transaksi_listview, daftar_antrian);
        this.context = context;
        this.daftar_antrian = daftar_antrian;
        this.result = result;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.layout_transaksi_listview, null,
                true);
        TextView  mtextView      = viewRow.findViewById(R.id.urutan);
        ImageView mimageView     = viewRow.findViewById(R.id.image_view);
        TextView  antriansaatini = viewRow.findViewById(R.id.antrianke);
        TextView  pelayanan      = viewRow.findViewById(R.id.estimasipelayanan);
        TextView  perjalanan     = viewRow.findViewById(R.id.estimasiperjalanan);
        Button    detail         = viewRow.findViewById(R.id.detail);
        mtextView.setText("No. " + daftar_antrian.get(i).getQueue());
        if(daftar_antrian.get(i).getQueueProcessed()==null){
            antriansaatini.setText("Saat ini belum ada yang diproses");
        } else {
            antriansaatini.setText("Saat ini antrian ke-" + daftar_antrian.get(i).getQueueProcessed());
        }
        DateTime utc = daftar_antrian.get(i).getTimestamp().toDateTime(zone);
        long secondsSinceEpoch = utc.getMillis();
        DateTime sekarang = new DateTime().withZone(zone);
        long datenow = sekarang.getMillis();
        CharSequence estimasi = DateUtils.getRelativeTimeSpanString(secondsSinceEpoch, datenow, 0);
        pelayanan.setText(estimasi);
        mimageView.setImageResource(R.drawable.klinikpenyakitdalam);
//        StorageReference storageRef = storage.getReference().child("images/" + daftar_antrian.get(i).gambar);
//        Glide.with(context)
//             .using(new FirebaseImageLoader())
//             .load(storageRef)
//             .into(mimageView);
        detail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, DetailTransaksi.class);
                context.startActivity(intent);
            }
        });
        if(result == null || result.routes.length == 0)
        {
            perjalanan.setText("estimasi waktu tidak tersedia");
        }
        else
        {
            perjalanan.setText(result.routes[0].legs[0].duration.humanReadable);
        }
        return viewRow;
    }


}
