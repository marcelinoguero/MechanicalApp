package com.tcc.mechanicalapp;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.card.MaterialCardView;

import com.google.android.gms.common.api.GoogleApiClient;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MaterialCardView card = findViewById(R.id.card);
        MaterialCardView card1 = findViewById(R.id.top_header);
        card.setShapeAppearanceModel(card.getShapeAppearanceModel().toBuilder().setTopEdge(new ConcaveEdge()).setAllCornerSizes(0).setTopLeftCornerSize(50).setTopRightCornerSize(50).build());
        card1.setShapeAppearanceModel(card1.getShapeAppearanceModel().toBuilder().setAllCornerSizes(0).setBottomLeftCornerSize(50).setBottomRightCornerSize(50).build());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // LatLng sydney = new LatLng(-34, 151);
        LatLng casa = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(casa).title("Home test"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(casa));

        String dark_pattern = Constants.getInstance().getMapStyle();
        MapStyleOptions dark_style = new MapStyleOptions(dark_pattern);
        mMap.setMapStyle(dark_style);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTeste) {
            finishActivity(1);
        }
    }
}