package com.tcc.mechanicalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SelectUserTypeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_user_type);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button btn = findViewById(R.id.next_btn);
        btn.setOnClickListener(this);

        ImageButton btn1 = findViewById(R.id.back_btn);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_btn) {
            Intent intent = new Intent(SelectUserTypeActivity.this, DriverRegisterActivity.class);

            RadioGroup typeGroup = findViewById(R.id.type_group);
            int selectedOption = typeGroup.getCheckedRadioButtonId();
            String userType = "";

            if (selectedOption == R.id.driver_radio) {
                userType = "driver";
            } else if (selectedOption == R.id.mechanic_radio) {
                userType = "freelancer_mechanic";
            } else if (selectedOption == R.id.workshop_radio) {
                userType = "mechanic_workshop";
            }

            if (userType.equals("")) {
                Toast toast = Toast.makeText(this, "Selecione um tipo de usuário", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                intent.putExtra("userType", userType);
                startActivityForResult(intent, 0);
            }
        }

        if (v.getId() == R.id.back_btn) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                finish();
            }
        }
    }
}