package com.cookandroid.shelterapp;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
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
import android.widget.Toast;

import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MapActivity extends AppCompatActivity {
    //Toolbar toolbar;
    TMapView tmapview;
    TMapData tmapdata=null;
    double la=37.651411;
    double lo=127.016117;
    TMapPoint point;
    TMapMarkerItem marker;
    String location[];
    public final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private final String TMAP_API_KEY = "dd858e01-6eb8-4cf7-aa33-68f2f15907d3";
    int count=0;
    Context context=null;
    ArrayList alTMapPoint = new ArrayList();
    ArrayList alTMapPoint2 = new ArrayList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map1);
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





        Log.d("백지연","끝");
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            //현재위치의 좌표를 알수있는 부분
            if (location != null) {
                double latitude = location.getLatitude();
                la=latitude;
                double longitude = location.getLongitude();
                lo=longitude;
                tmapview.setLocationPoint(longitude, latitude);
                tmapview.setCenterPoint(longitude, latitude);
                Log.d("TmapTest", "" + longitude + "," + latitude);
                // 동그라미 : 나중에 지울것
                TMapPoint tMapPoint=tmapview.getLocationPoint();
                TMapCircle tMapCircle = new TMapCircle();
                tMapCircle.setCenterPoint( tMapPoint );
                tMapCircle.setRadius(300);
                tMapCircle.setCircleWidth(2);
                tMapCircle.setLineColor(Color.BLUE);
                tMapCircle.setAreaColor(Color.GRAY);
                tMapCircle.setAreaAlpha(100);
                tmapview.addTMapCircle("circle1", tMapCircle);
                // 통합 검색 : 대피소
                String strData="지진옥외대피소 효문";

                tmapdata=new TMapData(); // 객체 생성
                tmapdata.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(ArrayList poiItem) {
                        TMapPoint x;

                        for(int i = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                            Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                                    "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                                    "Point: " + item.getPOIPoint().toString());

                            x=new TMapPoint(item.getPOIPoint().getLatitude(), item.getPOIPoint().getLongitude());
                            alTMapPoint.add(x);//위치
                            alTMapPoint2.add(item.getPOIName().toString());// 이름

                            //sList.add(new Sitem(item.getPOIName().toString(),item.getPOIAddress().replace("null", ""),item.getPOIAddress().replace("null", "")));

                        }



                        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.news);

                        for(int i=0; i<alTMapPoint.size(); i++){
                            TMapMarkerItem markerItem1 = new TMapMarkerItem();

                            // 마커 아이콘 지정
                            markerItem1.setIcon(bitmap);
                            // 마커의 좌표 지정
                            markerItem1.setTMapPoint((TMapPoint) alTMapPoint.get(i));

                            //지도에 마커 추가
                            tmapview.addMarkerItem("markerItem"+i, markerItem1);
                            // 거리가 1km이하인 것 검색


                        }

                    }
                });




            }
            if(alTMapPoint.size()!=0){
                HashSet<String> distinctData = new HashSet<String>(alTMapPoint);
                ArrayList result1 = new ArrayList<String>(distinctData);
                HashSet<String> distinctData2 = new HashSet<String>(alTMapPoint2);
                ArrayList result2 = new ArrayList<String>(distinctData2);

                // 현재 위치와 지점의 거리 배열
                Double []distance=new Double[alTMapPoint.size()];
                TMapPoint[] ret = new TMapPoint[alTMapPoint.size()];
                String[] ret2 = new String[alTMapPoint2.size()];
                for (int i=0; i < ret.length; i++)
                {
                    ret[i] = (TMapPoint) alTMapPoint.get(i);
                    ret2[i] = alTMapPoint2.get(i).toString(); // 장소이름이 담긴 어레이리스트를 배열에 저장하는 과정
                }

                if(location!=null){
                    // 현재 위치는?
                    double a=location.getLatitude();
                    double b=location.getLongitude();

                    float af=Float.parseFloat(Double.toString(a));
                    float bf=Float.parseFloat(Double.toString(b));

                    Log.d("현재위치",a+" "+b);
                    Location locationB=new Location("pointB");
                    locationB.setLatitude(a);
                    locationB.setLongitude(b);
                    // ALTMAPPOINT에서 경도 위도 더블값만 받아옴
                    for(int i=0;i<ret.length;i++){

                        String date[] = ret[i].toString().split(" ");
                        //lat
                        double lat=Double.parseDouble(date[1]);
                        //lon
                        double lon=Double.parseDouble(date[3]);

                        float latf=Float.parseFloat(Double.toString(lat));
                        float lonf=Float.parseFloat(Double.toString(lon));
                        Location locationA=new Location("pointA");
                        locationA.setLatitude(lat);
                        locationA.setLongitude(lon);
                        Log.d("포인트지점",ret2[i]+"");
                        distance[i]= Double.valueOf(locationA.distanceTo(locationB));
                        Log.d("포인트값거리",distance[i]+"");
                    }
                    // 디스턴스 배열 소팅하여 i값 구하기
                    double min=distance[0];
                    int index=0;
                    for(int k=0;k<ret.length;k++){
                        if(distance[k] < min) {
                            min = distance[k];
                            index=k;

                        }
                        Log.d("포인트max",min+"");
                    }
                    // 최소값 출력
                    Log.d("포인트최단",distance[index]+"");
                    Log.d("포인트최단장소",ret2[index]+"");

                    // 다이얼로그
                    AlertDialog.Builder dlg=new AlertDialog.Builder(MapActivity.this);
                    dlg.setTitle("가장 가까운 대피소");
                    dlg.setMessage(ret2[index]);
                    dlg.setPositiveButton("확인",null);
                    dlg.show();








                }


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