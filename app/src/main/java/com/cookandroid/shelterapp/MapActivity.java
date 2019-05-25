package com.cookandroid.shelterapp;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapView;

public class MapActivity extends AppCompatActivity {
    Toolbar toolbar;
    TMapView tmapview;
    public final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private final String TMAP_API_KEY = "dd858e01-6eb8-4cf7-aa33-68f2f15907d3";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey(TMAP_API_KEY);
        tmapview.setIconVisibility(true);
        linearLayoutTmap.addView(tmapview);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);   //위치권한 탐색 허용 관련 내용
            }
            return;
        }
        tmapview.setTrackingMode(true);
        tmapview.setCompassMode(true);
        tmapview.setCenterPoint(37.651411, 127.016117);
        setGps();

    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            //현재위치의 좌표를 알수있는 부분
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tmapview.setLocationPoint(longitude, latitude);
                tmapview.setCenterPoint(longitude, latitude);
                Log.d("TmapTest", "" + longitude + "," + latitude);
            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void setGps() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setGps();

                } else {
                    Log.d("locationTest", "동의거부함");
                }
            }


        }
    }
}