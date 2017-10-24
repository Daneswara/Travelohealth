package com.masbie.travelohealth;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.dao.external.request.RegisterDao;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.ServiceQueuePojo;
import com.masbie.travelohealth.pojo.service.ServiceRequestPojo;
import com.masbie.travelohealth.pojo.service.ServicesDoctorsPojo;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daneswara Jauhari on 06/08/2017.
 */

@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions", "unused"}) public class AdapterLayanan extends ArrayAdapter<ServicesDoctorsPojo>
{
    private Context                   context;
    private List<ServicesDoctorsPojo> daftar_poli;
    private BottomNavigationView      navigation;
    private LinearLayout              fl, f2;
    private FirebaseAuth      mAuth;
    private DatabaseReference mDatabase;
    private FirebaseStorage   storage = FirebaseStorage.getInstance();
    private DateTimeFormatter hms     = DateTimeFormat.forPattern("HH:mm:ss");
    private DateTimeFormatter ymd     = DateTimeFormat.forPattern("YYYY-MM-dd");
    private Random            random  = new Random();
    private DateTimeZone      zone    = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

    public AdapterLayanan(Activity context, List<ServicesDoctorsPojo> daftar_poli)
    {
        super(context, R.layout.layout_layanan_listview, daftar_poli);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.daftar_poli = daftar_poli;
        this.context = context;
        fl = context.findViewById(R.id.transaksi);
        f2 = context.findViewById(R.id.layanan);
        navigation = context.findViewById(R.id.navigation);
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        /*
        * Layout Initialization====================================================================
        * */
        final LayoutInflater      layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View                viewRow        = layoutInflater.inflate(R.layout.layout_layanan_listview, null, true);
        final TextView            mtextView      = viewRow.findViewById(R.id.namalayanan);
        final TextView            jam            = viewRow.findViewById(R.id.jamkerja);
        final TextView            pesan          = viewRow.findViewById(R.id.pesanDokter);
        final ImageView           mimageView     = viewRow.findViewById(R.id.image_view);
        final ServicesDoctorsPojo poli           = daftar_poli.get(i);

        /*
        * Content Initialization====================================================================
        * */
        mtextView.setText(poli.getName());
        jam.setText(String.format(Locale.getDefault(), "%s\n%s - %s", "Setiap Hari",
                poli.getTimeStart().toString(hms), poli.getTimeEnd().toString(hms)));
        mimageView.setImageResource(R.drawable.klinikgigi);
        //StorageReference storageRef = storage.getReference().child("images/" + poli.gambar);
        /*Glide.with(context)
             .using(new FirebaseImageLoader())
             .load(storageRef)
             .into(mimageView);*/

        /*
        * Dialog Initialization ===================================================================
        * */
        //System.out.println("cek keter" + cekKetersediaan(poli.hari, poli.jamkerja));
        //if(!cekKetersediaan(poli.hari, poli.jamkerja))
        if(false)
        {
            pesan.setText("TUTUP");
            pesan.setBackgroundColor(Color.GRAY);
            pesan.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Layanan TUTUP!")
                            .setContentText("Anda dapat mencoba lagi ketika layanan " + poli.getName() + " telah dibuka.")
                            .show();
                }
            });
        }
        else
        {
            pesan.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Apa anda yakin?")
                            .setContentText("Anda akan memesan layanan " + poli.getName() + "!")
                            .setConfirmText("Ya, saya yakin!")
                            .setCancelText("Batal")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                            {
                                @Override
                                public void onClick(SweetAlertDialog sDialog)
                                {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                            {
                                @Override
                                public void onClick(final SweetAlertDialog sDialog)
                                {
                                    if(poli.getDoctors().size() > 0)
                                    {
                                        Integer            doctor   = poli.getDoctors().get(random.nextInt(poli.getDoctors().size())).getId();
                                        LocalDate          tanggal  = new LocalDate(zone);
                                        ServiceRequestPojo selected = new ServiceRequestPojo(doctor, tanggal);
                                        RegisterDao.registerServiceRequest(selected, context, new Callback<ResponsePojo<ServiceQueuePojo>>()
                                        {
                                            @Override public void onResponse(@NonNull Call<ResponsePojo<ServiceQueuePojo>> call, @NonNull Response<ResponsePojo<ServiceQueuePojo>> response)
                                            {
                                                ServiceQueuePojo queue = response.body().getData().getResult();
                                                //Simpan ke DB atau firebase terserah enaknya gimana buat trigger notif
                                                //FirebaseDao.subscribe(String.format(Locale.getDefault(), "service-%s-%d", queue.getOrder().toString(ymd), queue.getService().getId()));
                                                sDialog
                                                        .setTitleText("Berhasil!")
                                                        .setContentText("Anda telah masuk dalam antrian!")
                                                        .setConfirmText("OK")
                                                        .showCancelButton(false)
                                                        .setConfirmClickListener(null)
                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            }

                                            @Override public void onFailure(@NonNull Call<ResponsePojo<ServiceQueuePojo>> call, @NonNull Throwable throwable)
                                            {
                                                Dao.defaultFailureTask(context, call, throwable);
                                            }
                                        });

                                        /*Calendar calendar = Calendar.getInstance();
                                        String   tanggal  = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
                                        mDatabase.child("antrian").child(tanggal).child(poli.getId()).addListenerForSingleValueEvent(new ValueEventListener()
                                        {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot)
                                            {
                                                Calendar calendar = Calendar.getInstance();
                                                String tanggal = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
                                                calendar.add(Calendar.HOUR, 2);
                                                if(!dataSnapshot.hasChild("antrian"))
                                                {
                                                    mDatabase.child("antrian").child(tanggal).child(poli.id).child("antrian").setValue(1);
                                                    mDatabase.child("antrian").child(tanggal).child(poli.id).child("proses").setValue(1);
                                                    Pesan pesanan = new Pesan(poli.id, 1, calendar.getTimeInMillis(), "proses", mAuth.getCurrentUser().getUid(), poli.gambar);
                                                    mDatabase.child("antrian").child(tanggal).child(poli.id).child(String.valueOf(Calendar.getInstance().getTimeInMillis())).setValue(pesanan);
                                                    sDialog
                                                            .setTitleText("Berhasil!")
                                                            .setContentText("Anda telah masuk dalam antrian!")
                                                            .setConfirmText("OK")
                                                            .showCancelButton(false)
                                                            .setConfirmClickListener(null)
                                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                }
                                                else
                                                {
                                                    mDatabase.child("antrian").child(tanggal).child(poli.id).child("antrian").addListenerForSingleValueEvent(new ValueEventListener()
                                                    {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot)
                                                        {
                                                            Calendar calendar = Calendar.getInstance();
                                                            String tanggal = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
                                                            calendar.add(Calendar.HOUR, 2);
                                                            mDatabase.child("antrian").child(tanggal).child(poli.id).child("antrian").setValue((long) dataSnapshot.getValue() + 1);
                                                            Pesan pesanan = new Pesan(poli.id, (long) dataSnapshot.getValue() + 1, calendar.getTimeInMillis(), "antri", mAuth.getCurrentUser().getUid(), poli.gambar);
                                                            mDatabase.child("antrian").child(tanggal).child(poli.id).child(String.valueOf(Calendar.getInstance().getTimeInMillis())).setValue(pesanan);
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
                                                        public void onCancelled(DatabaseError databaseError)
                                                        {

                                                        }
                                                    });

                                                }
                                                //FirebaseMessaging.getInstance().subscribeToTopic(poli.id);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError)
                                            {

                                            }
                                        });*/
                                    }
                                    else
                                    {
                                        Toast.makeText(context, "Tidak Ada dokter yang melayani", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .show();
                }
            });
        }


        return viewRow;
    }

    public boolean cekKetersediaan(String hari, String waktu)
    {
        waktu = waktu.replace(".", "titik");
        hari = hari.replace(" ", "");
        Calendar calendar    = Calendar.getInstance();
        int      day         = calendar.get(Calendar.DAY_OF_WEEK);
        String   semuahari[] = hari.split(",");
        String   hari_now    = "";
        switch(day)
        {
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
        for(int i = 0; i < semuahari.length; i++)
        {
            if(semuahari[i].equalsIgnoreCase(hari_now))
            {
                cek_hari = true;
            }
        }
        String[] waktu_tersedia = waktu.split("-");
        System.out.println(waktu_tersedia[0]);
        System.out.println(waktu_tersedia[1]);
        String[] waktuawal = waktu_tersedia[0].split("titik");
        System.out.println(waktuawal[0]);
        System.out.println(waktuawal[1]);
        String[] waktuakhir      = waktu_tersedia[1].split("titik");
        int      jam             = calendar.get(Calendar.HOUR_OF_DAY);
        int      menit           = calendar.get(Calendar.MINUTE);
        int      konv_waktu      = jam * 60 + menit;
        int      konv_waktuawal  = Integer.parseInt(waktuawal[0]) * 60 + Integer.parseInt(waktuawal[1]);
        int      konv_waktuakhir = Integer.parseInt(waktuakhir[0]) * 60 + Integer.parseInt(waktuakhir[1]);
        boolean  cek_waktu       = konv_waktu >= konv_waktuawal && konv_waktu <= konv_waktuakhir;
        System.out.println("cek hari" + cek_hari);
        System.out.println("cek waktu" + cek_waktu);
        return cek_hari && cek_waktu;
    }
}
