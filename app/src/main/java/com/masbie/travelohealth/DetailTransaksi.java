package com.masbie.travelohealth;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;

public class DetailTransaksi extends FragmentActivity implements OnMapReadyCallback
{

    private static final int overview = 0;
    double longitude, latitude;
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
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(DetailTransaksi.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DetailTransaksi.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
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

        // Add a marker in Sydney and move the camera
        //        LatLng sydney = new LatLng(-7.9838373, 112.6265787);
        //        mMap.addMarker(new MarkerOptions().position(sydney).title("RSAB Muhammadiyah Malang").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        setupGoogleMapScreenSettings(googleMap);
        new getLokasi("RSAB Muhammadiyah Malang", TravelMode.DRIVING, googleMap).execute();
    }

    private void setupGoogleMapScreenSettings(GoogleMap mMap)
    {
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap)
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].startLocation.lat, results.routes[overview].legs[overview].startLocation.lng)).title("Lokasi Anda").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].endLocation.lat, results.routes[overview].legs[overview].endLocation.lng)).title("RSAB Muhammadiyah Malang").snippet(getEndLocationTitle(results)));
    }

    private void positionCamera(DirectionsRoute route, GoogleMap mMap)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap)
    {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }

    private String getEndLocationTitle(DirectionsResult results)
    {
        return "Time :" + results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private GeoApiContext getGeoContext()
    {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_maps_dir_key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    class getLokasi extends AsyncTask<String, String, DirectionsResult>
    {
        /**
         * Before starting background thread Show Progress Dialog
         */
        String     destination;
        TravelMode mode;
        GoogleMap  googleMap;

        public getLokasi(String destination, TravelMode mode, GoogleMap googleMap)
        {
            this.destination = destination;
            this.mode = mode;
            this.googleMap = googleMap;
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
                                    .mode(mode)
                                    .origin(new com.google.maps.model.LatLng(latitude, longitude))
                                    .destination(destination)
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
            if(result != null)
            {
                addPolyline(result, googleMap);
                positionCamera(result.routes[overview], googleMap);
                addMarkersToMap(result, googleMap);
            }
        }

    }
}
