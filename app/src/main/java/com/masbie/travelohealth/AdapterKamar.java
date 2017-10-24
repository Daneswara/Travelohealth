package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.masbie.travelohealth.pojo.service.DetailedRoomClassPojo;
import com.masbie.travelohealth.pojo.service.RoomSectorPojo;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

public class AdapterKamar extends ArrayAdapter<RoomSectorPojo<DetailedRoomClassPojo>>
{
    private List<RoomSectorPojo<DetailedRoomClassPojo>> daftar_kamar;
    private Context                                     context;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public AdapterKamar(Activity context, List<RoomSectorPojo<DetailedRoomClassPojo>> daftar_kamar)
    {
        super(context, R.layout.layout_kamar_listview, daftar_kamar);
        this.daftar_kamar = daftar_kamar;
        this.context = context;
    }

    @SuppressWarnings("ConstantConditions") @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        /*
        * Layout Initialization====================================================================
        * */
        final LayoutInflater                        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View                                  viewRow        = layoutInflater.inflate(R.layout.layout_kamar_listview, null, true);
        final TextView                              mtextView      = viewRow.findViewById(R.id.kelaskamar);
        final TextView                              harga          = viewRow.findViewById(R.id.harga);
        final TextView                              fasilitas      = viewRow.findViewById(R.id.fasilitas);
        final TextView                              pesan          = viewRow.findViewById(R.id.pesanDokter);
        final ImageView                             mimageView     = viewRow.findViewById(R.id.image_view);
        final RoomSectorPojo<DetailedRoomClassPojo> kamar          = daftar_kamar.get(i);

        /*
        * Content Initialization====================================================================
        * */
        if(kamar.getClasses().size() > 0)
        {
            final DetailedRoomClassPojo kamarClass = kamar.getClasses().get(0);
            harga.setText(currencyFormatter.format(kamarClass.getCost()) + "/hari");
            fasilitas.setText(kamarClass.getFeature());
            mtextView.setText(kamar.getName() + " - " + kamarClass.getName());
            mimageView.setImageResource(R.drawable.vip1);

/*            StorageReference storageRef = storage.getReference().child("images/" + kamarClass.gambar);
            Glide.with(context)
                 .using(new FirebaseImageLoader())
                 .load(storageRef)
                 .into(mimageView);*/

            if(kamarClass.getReady() > 0)
            {
                pesan.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(context, PesanKamar.class);
                        intent.putExtra("gambar", kamarClass.getPreview());
                        intent.putExtra("id_kamar", kamarClass.getId());
                        intent.putExtra("kelas", kamarClass.getName());
                        intent.putExtra("harga", kamarClass.getCost());
                        context.startActivity(intent);
                    }
                });
            }
            else
            {
                pesan.setText("PENUH");
                pesan.setBackgroundColor(Color.GRAY);
                pesan.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Mohon maaf!")
                                .setContentText("Kamar " + kamar.getName() + " - " + kamarClass.getName() + " sudah penuh.")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog)
                                    {
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                });
            }
        }


        return viewRow;
    }
}
