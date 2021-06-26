package com.tcc.mechanicalapp;

import com.loopj.android.http.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button btn = findViewById(R.id.login_btn);
        TextView btnRegister = findViewById(R.id.register_button);
        btn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.register_button) {
            startActivityForResult(new Intent(LoginActivity.this, SelectUserTypeActivity.class),0);
        }

        if (v.getId() == R.id.login_btn) {

            EditText emailField = findViewById(R.id.login);
            EditText passwordField = findViewById(R.id.password);

            String emailText = emailField.getText().toString();
            String passwordText = passwordField.getText().toString();

            RadioGroup typeGroup = findViewById(R.id.user_type_options);
            int selectedOption = typeGroup.getCheckedRadioButtonId();
            String userType = "";

            if (selectedOption == R.id.radio_driver_option) {
                userType = "driver";
            } else if (selectedOption == R.id.radio_mechanic_option) {
                userType = "mechanic";
            } else if (selectedOption == R.id.workshop_radio) {
                userType = "mechanic_workshop";
            }

            AsyncHttpClient client = new AsyncHttpClient();

            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("login",emailText);
                jsonParams.put("password",passwordText);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringEntity entity = null;
            entity = new StringEntity(jsonParams.toString(), "UTF-8");

            Context context = this.getApplicationContext();
            String server = Constants.getInstance().getServerAddress();
            client.post(context, server+"/authentication/login/"+userType, entity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    try {
                        JSONObject jsonObjectResponse = new JSONObject(new String(response));

                        EditText text = findViewById(R.id.login);
                        EditText password = findViewById(R.id.password);

                        Constants.getInstance().setUserLogin(text.getText().toString());
                        Constants.getInstance().setPassword(password.getText().toString());

                        if (jsonObjectResponse.getString("type").equals("driver")) {
                            Constants.getInstance().setUserName(jsonObjectResponse.getString("name"));
                            Constants.getInstance().setUserId(jsonObjectResponse.getString("id"));
                            Constants.getInstance().setUserType("driver");
                            startActivityForResult(new Intent(LoginActivity.this, DriverProfileActivity.class),0);
                        } else if (jsonObjectResponse.getString("type").equals("associated_mechanic")) {
                            Constants.getInstance().setUserName(jsonObjectResponse.getString("name"));
                            Constants.getInstance().setUserId(jsonObjectResponse.getString("id"));
                            Constants.getInstance().setUserType("associated_mechanic");
                            Constants.getInstance().setWorkshopName(jsonObjectResponse.getString("workshop_id"));
                            startActivityForResult(new Intent(LoginActivity.this, AssociatedMechanicProfileActivity.class),0);
                        } else if (jsonObjectResponse.getString("type").equals("freelancer_mechanic")) {
                            Constants.getInstance().setUserName(jsonObjectResponse.getString("name"));
                            Constants.getInstance().setUserId(jsonObjectResponse.getString("id"));
                            Constants.getInstance().setUserType("freelancer_mechanic");
                            startActivityForResult(new Intent(LoginActivity.this, FreelanceMechanicProfileActivity.class),0);
                        } else if (jsonObjectResponse.getString("type").equals("mechanic_workshop")) {
                            Constants.getInstance().setUserName(jsonObjectResponse.getString("name"));
                            Constants.getInstance().setUserId(jsonObjectResponse.getString("id"));
                            Constants.getInstance().setUserType("mechanic_workshop");
                            startActivityForResult(new Intent(LoginActivity.this, MechanicWorkshopProfileActivity.class),0);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Toast toast = Toast.makeText(context, "Usuário ou senha invalidos", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onRetry(int retryNo) {
                }
            });

        }
    }
}