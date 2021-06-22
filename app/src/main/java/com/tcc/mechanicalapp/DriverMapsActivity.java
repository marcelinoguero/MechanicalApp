package com.tcc.mechanicalapp;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static java.lang.Thread.currentThread;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private MapManager mapManager;
    private ProvidersListManager providersListManager;
    private LocationManager locationManager;

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

        ImageButton btn1 = findViewById(R.id.btnTeste);
        ImageButton btn2 = findViewById(R.id.btnUpBottomSheet);
        FloatingActionButton btn3 = findViewById(R.id.btnSearchProviders);
        ImageButton btn5 = findViewById(R.id.btnFocusUser);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn5.setOnClickListener(this);

        providersListManager = new ProvidersListManager(this, this);
        locationManager = new LocationManager(this,this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapManager = new MapManager(googleMap);

        new Thread(new Runnable() {
            public void run(){
                while (true) {
                    locationManager.plotUserLocation(mapManager);
                    try {
                        currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnSearchProviders) {
            locationManager.requestNearbyProviders(mapManager, providersListManager);
        }

        if (v.getId() == R.id.btnTeste) {
            finish();
        }

        if (v.getId() == R.id.btnUpBottomSheet) {
        }

        if (v.getId() == R.id.call_button) {
            startActivityForResult(new Intent(DriverMapsActivity.this, ChatActivity.class), 0);
        }

        if (v.getId() == R.id.btnFocusUser) {
            mapManager.focusCamera();
        }
    }
}