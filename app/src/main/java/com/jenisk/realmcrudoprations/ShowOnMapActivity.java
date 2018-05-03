package com.jenisk.realmcrudoprations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jenisk.realmcrudoprations.model.DirectoryData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Jenis Kasundra on 03/05/2018.
 */

public class ShowOnMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    double latitude,longitude;
    String userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap();
    }

    private void initMap() {

            Intent intent = getIntent();
            latitude = Double.parseDouble(intent.getStringExtra("latitude"));
            longitude = Double.parseDouble(intent.getStringExtra("longitude"));
            userName = intent.getStringExtra("username");


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(userName));
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


    }
}
