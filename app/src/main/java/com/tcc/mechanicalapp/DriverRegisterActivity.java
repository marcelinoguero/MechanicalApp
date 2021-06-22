package com.tcc.mechanicalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class DriverRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_resgister);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        userType = getIntent().getStringExtra("userType");

        Button btn = findViewById(R.id.complete_btn);
        btn.setOnClickListener(this);

        ImageButton btn1 = findViewById(R.id.back_btn);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.back_btn) {
            finish();
        }

        if (v.getId() == R.id.complete_btn) {
            EditText nameField = findViewById(R.id.name);
            EditText emailField = findViewById(R.id.email);
            EditText passwordField = findViewById(R.id.password);
            EditText passwordConfirmationField = findViewById(R.id.password_confirmation);

            String nameText = nameField.getText().toString();
            String emailText = emailField.getText().toString();
            String passwordText = passwordField.getText().toString();
            String passwordConfirmationText = passwordConfirmationField.getText().toString();

            if (!nameText.equals("") && !emailText.equals("") && !passwordText.equals("") && !passwordConfirmationText.equals("")) {

                AsyncHttpClient client = new AsyncHttpClient();

                JSONObject jsonParams = new JSONObject();
                try {
                    jsonParams.put("name", nameText);
                    jsonParams.put("email", emailText);
                    jsonParams.put("password", passwordText);
                    jsonParams.put("confirmpassword", passwordConfirmationText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringEntity entity = null;
                entity = new StringEntity(jsonParams.toString(), "UTF-8");

                Context context = this.getApplicationContext();
                String server = Constants.getInstance().getServerAddress();
                client.post(context, server + "/register/newprofile/" + userType, entity, "application/json", new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                        Toast toast = Toast.makeText(context, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT);
                        toast.show();

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",1);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        
                    }

                    @Override
                    public void onRetry(int retryNo) {

                    }
                });
            } else {
                Toast toast = Toast.makeText(this, "Preencha todos os campos antes de continuar", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}