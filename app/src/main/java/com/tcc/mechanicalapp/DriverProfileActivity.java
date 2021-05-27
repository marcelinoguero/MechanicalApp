package com.tcc.mechanicalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class DriverProfileActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageButton btn1 = findViewById(R.id.mapsBtn);
        ImageButton btn2 = findViewById(R.id.exitBtn);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn1.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mapsBtn) {
            startActivityForResult(new Intent(DriverProfileActivity.this, DriverMapsActivity.class), 0);
        }

        if (v.getId() == R.id.exitBtn) {
            finishActivity(1);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                v.getBackground().setColorFilter(0xe04eb5a9, PorterDuff.Mode.SRC_ATOP);
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                v.getBackground().clearColorFilter();
                v.invalidate();
                break;
            }
        }
        return false;
    }
}