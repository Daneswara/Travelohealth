package com.masbie.travelohealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.masbie.travelohealth.dao.TokenDao;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.dao.external.get.InformationDao;
import com.masbie.travelohealth.dao.external.personal.AccountDao;
import com.masbie.travelohealth.object.Antrian;
import com.masbie.travelohealth.pojo.personal.AccountPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.DetailedRoomClassPojo;
import com.masbie.travelohealth.pojo.service.DoctorsServicesPojo;
import com.masbie.travelohealth.pojo.service.RoomSectorPojo;
import com.masbie.travelohealth.pojo.service.ServicesDoctorsPojo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
{
    SharedPreferences                           sharedpreferences;
    ListView                                    androidListViewLayanan;
    ListView                                    androidListViewDokter;
    ListView                                    androidListViewKamar;
    ListView                                    androidListView;
    List<ServicesDoctorsPojo>                   daftar_poli;
    List<DoctorsServicesPojo>                   daftar_dokter;
    List<RoomSectorPojo<DetailedRoomClassPojo>> daftar_kamar;
    List<Antrian>                               daftar_antrian;
    DirectionsResult                            result;
    double                                      longitude, latitude;
    private final LocationListener locationListener = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle)
        {

        }

        @Override
        public void onProviderEnabled(String s)
        {

        }

        @Override
        public void onProviderDisabled(String s)
        {

        }
    };
    BottomNavigationView navigation;
    //    static {
    //        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    //    }
    private LinearLayout fl, f2, f3, f4, f5;
    private FirebaseAuth      mAuth;
    private DatabaseReference mDatabase;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            switch(item.getItemId())
            {
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
    private AccountPojo account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.account = AccountDao.retrieveAccount(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getSharedPreferences("menu", Context.MODE_PRIVATE);

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

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(Home.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
        else
        {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        androidListView = findViewById(R.id.custom_listview_transaksi_sekarang);

        // ambil data poli
        daftar_antrian = new LinkedList<>();
        new getLokasi().execute();

        /*
        * Layout Service ===========================================================================
        * */
        androidListViewLayanan = findViewById(R.id.custom_listview_layanan);
        daftar_poli = new LinkedList<>();
        InformationDao.getService(this, new Callback<ResponsePojo<List<ServicesDoctorsPojo>>>()
        {
            @SuppressWarnings("ConstantConditions") @Override public void onResponse(@NonNull Call<ResponsePojo<List<ServicesDoctorsPojo>>> call, @NonNull Response<ResponsePojo<List<ServicesDoctorsPojo>>> response)
            {
                daftar_poli.addAll(response.body().getData().getResult());
                AdapterLayanan androidListLayanan = new AdapterLayanan(Home.this, daftar_poli);
                androidListViewLayanan.setAdapter(androidListLayanan);
            }

            @Override public void onFailure(@NonNull Call<ResponsePojo<List<ServicesDoctorsPojo>>> call, @NonNull Throwable throwable)
            {
                Dao.defaultFailureTask(Home.this, call, throwable);
            }
        });
        /*mDatabase.child("poli").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                daftar_poli.add(dataSnapshot.getValue(Poli.class));
                AdapterLayanan androidListLayanan = new AdapterLayanan(Home.this, daftar_poli);
                androidListViewLayanan.setAdapter(androidListLayanan);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });*/

        /*
        * Layout Doctor ============================================================================
        * */
        androidListViewDokter = findViewById(R.id.custom_listview_dokter);
        daftar_dokter = new LinkedList<>();
        InformationDao.getDoctor(this, new Callback<ResponsePojo<List<DoctorsServicesPojo>>>()
        {
            @SuppressWarnings("ConstantConditions") @Override public void onResponse(@NonNull Call<ResponsePojo<List<DoctorsServicesPojo>>> call, @NonNull Response<ResponsePojo<List<DoctorsServicesPojo>>> response)
            {
                daftar_dokter.addAll(response.body().getData().getResult());
                AdapterDokter androidListDokter = new AdapterDokter(Home.this, daftar_dokter);
                androidListViewDokter.setAdapter(androidListDokter);
            }

            @Override public void onFailure(@NonNull Call<ResponsePojo<List<DoctorsServicesPojo>>> call, @NonNull Throwable throwable)
            {
                Dao.defaultFailureTask(Home.this, call, throwable);
            }
        });
        /*mDatabase.child("dokter").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                daftar_dokter.add(dataSnapshot.getValue(Dokter.class));
                AdapterDokter androidListDokter = new AdapterDokter(Home.this, daftar_dokter);
                androidListViewDokter.setAdapter(androidListDokter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });*/

        /*
        * Layout Room ==============================================================================
        * */
        androidListViewKamar = findViewById(R.id.custom_listview_kamar);
        daftar_kamar = new LinkedList<>();
        InformationDao.getRoom(this, new Callback<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>>()
        {
            @SuppressWarnings("ConstantConditions") @Override public void onResponse(@NonNull Call<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> call, @NonNull Response<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> response)
            {
                daftar_kamar.addAll(response.body().getData().getResult());
                AdapterKamar androidListKamar = new AdapterKamar(Home.this, daftar_kamar);
                androidListViewKamar.setAdapter(androidListKamar);
            }

            @Override public void onFailure(@NonNull Call<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> call, @NonNull Throwable throwable)
            {
                Dao.defaultFailureTask(Home.this, call, throwable);
            }
        });
        /*mDatabase.child("kamar").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                daftar_kamar.add(dataSnapshot.getValue(Kamar.class));
                AdapterKamar androidListKamar = new AdapterKamar(Home.this, daftar_kamar);
                androidListViewKamar.setAdapter(androidListKamar);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });*/


        /*
        * Layout Logout ============================================================================
        * */
        TextView nama  = findViewById(R.id.nama_pengguna);
        TextView email = findViewById(R.id.email_pengguna);
        nama.setText(this.account.getUsername());
        email.setText(this.account.getIdentity());
        View logout = findViewById(R.id.keluar);
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TokenDao.clear(Home.this);
                Intent intent = new Intent(Home.this, SplashScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void cek_menu()
    {
        int                      menunya = sharedpreferences.getInt("menu", 0);
        SharedPreferences.Editor editor  = sharedpreferences.edit();
        if(menunya == 0)
        {
            editor.putInt("menu", 1);
            editor.commit();
            fl.setVisibility(View.VISIBLE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_home);
        }
        else if(menunya == 1)
        {
            fl.setVisibility(View.VISIBLE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_home);
        }
        else if(menunya == 2)
        {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.VISIBLE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_layanan);
        }
        else if(menunya == 3)
        {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.VISIBLE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_dokter);
        }
        else if(menunya == 4)
        {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.VISIBLE);
            f5.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.navigation_kamar);
        }
        else if(menunya == 5)
        {
            fl.setVisibility(View.GONE);
            f2.setVisibility(View.GONE);
            f3.setVisibility(View.GONE);
            f4.setVisibility(View.GONE);
            f5.setVisibility(View.VISIBLE);
            navigation.setSelectedItemId(R.id.navigation_akun);
        }
    }

    private DirectionsResult getDirectionsDetails()
    {
        DateTime now = new DateTime();
        try
        {
            return DirectionsApi.newRequest(getGeoContext())
                                .mode(TravelMode.DRIVING)
                                .origin(new LatLng(latitude, longitude))
                                .destination("RSAB Muhammadiyah Malang")
                                .departureTime(now)
                                .await();
        }
        catch(ApiException e)
        {
            e.printStackTrace();
            return null;
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private GeoApiContext getGeoContext()
    {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3).setApiKey(getString(R.string.google_maps_dir_key)).setConnectTimeout(1, TimeUnit.SECONDS).setReadTimeout(1, TimeUnit.SECONDS).setWriteTimeout(1, TimeUnit.SECONDS);
    }

    class getLokasi extends AsyncTask<String, String, DirectionsResult>
    {
        /**
         * Before starting background thread Show Progress Dialog
         */
        public getLokasi()
        {
        }


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected DirectionsResult doInBackground(String... args)
        {
            // TODO Auto-generated method stub
            // Check for success tag
            DateTime now = new DateTime();
            try
            {
                return DirectionsApi.newRequest(getGeoContext())
                                    .mode(TravelMode.DRIVING)
                                    .origin(new LatLng(latitude, longitude))
                                    .destination("RSAB Muhammadiyah Malang")
                                    .departureTime(now)
                                    .await();
            }
            catch(ApiException e)
            {
                e.printStackTrace();
                return null;
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
                return null;
            }
            catch(IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final DirectionsResult result)
        {
            super.onPostExecute(result);
            Calendar     calendar = Calendar.getInstance();
            final String tanggal  = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
            if(result != null)
            {
                mDatabase.child("antrian").child(tanggal).addChildEventListener(new ChildEventListener()
                {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s)
                    {
                        final String key = dataSnapshot.getKey();
                        mDatabase.child("antrian").child(tanggal).child(dataSnapshot.getKey()).addChildEventListener(new ChildEventListener()
                        {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s)
                            {
                                if(!dataSnapshot.getKey().equals("antrian") && !dataSnapshot.getKey().equals("proses"))
                                {
                                    final Antrian antrian = dataSnapshot.getValue(Antrian.class);
                                    if(antrian.uid_pasien.equals(mAuth.getCurrentUser().getUid()))
                                    {
                                        mDatabase.child("antrian").child(tanggal).child(key).child("proses").addValueEventListener(new ValueEventListener()
                                        {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot)
                                            {
                                                if(dataSnapshot.getValue() != null)
                                                {
                                                    daftar_antrian.add(antrian);
                                                    AdapterTransaksiSekarang androidListAdapter = new AdapterTransaksiSekarang(Home.this, daftar_antrian, result, dataSnapshot.getValue(long.class));
                                                    androidListView.setAdapter(androidListAdapter);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError)
                                            {

                                            }
                                        });

                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s)
                            {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot)
                            {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s)
                            {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {

                            }
                        });
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s)
                    {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot)
                    {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s)
                    {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });
            }
        }

    }

}
