package com.tcc.mechanicalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView userName = findViewById(R.id.driver_name);
        userName.setText(Constants.getInstance().getUserName());

        ImageButton btn1 = findViewById(R.id.driverMapsBtn);
        ImageButton btn2 = findViewById(R.id.driverExitBtn);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn1.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.driverMapsBtn) {
            startActivityForResult(new Intent(DriverProfileActivity.this, DriverMapsActivity.class), 0);
        }

        if (v.getId() == R.id.driverExitBtn) {
            Toast toast = Toast.makeText(this, "Logoff efetuado com sucesso", Toast.LENGTH_SHORT);
            toast.show();
            finish();
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