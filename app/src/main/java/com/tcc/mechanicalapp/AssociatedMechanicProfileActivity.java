package com.tcc.mechanicalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class AssociatedMechanicProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_mechanic_profile);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        TextView userName = findViewById(R.id.associated_name);
        TextView workshopName = findViewById(R.id.associated_name);
        userName.setText(Constants.getInstance().getUserName());
        userName.setText(Constants.getInstance().getWorkshopName());

        ImageButton btn1 = findViewById(R.id.associatedExitBtn);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}