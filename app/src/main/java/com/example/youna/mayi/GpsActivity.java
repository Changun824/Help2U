package com.example.youna.mayi;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GpsActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private String lon,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        Intent intent=getIntent();
        lat=intent.getStringExtra("lat");
        lon=intent.getStringExtra("lon");
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng location = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("▼");
        markerOptions.snippet("노약자");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.couple));
        map.addMarker(markerOptions);



        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }


}
