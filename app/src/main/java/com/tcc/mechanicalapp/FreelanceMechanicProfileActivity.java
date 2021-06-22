package com.tcc.mechanicalapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static java.lang.Thread.currentThread;

public class FreelanceMechanicProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_mechanic_profile);

        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        TextView userName = findViewById(R.id.freelancer_name);
        userName.setText(Constants.getInstance().getUserName());

        ImageButton btn1 = findViewById(R.id.freelancerExitBtn);
        btn1.setOnClickListener(this);

        Context context = getApplicationContext();

        new Thread(new Runnable() {
            public void run(){
                SyncHttpClient client = new SyncHttpClient();
                JSONObject jsonParams;
                LocationManager locationManager = new LocationManager(context, FreelanceMechanicProfileActivity.this);
                double latitude = 0.0f;
                double longitude = 0.0f;
                Location currentLocation;

                while (true) {
                    locationManager.getLocation();
                    currentLocation = locationManager.getCurrentLocation();

                    if (currentLocation != null && (currentLocation.getLatitude() != latitude || currentLocation.getLongitude() != longitude)) {
                        latitude = currentLocation.getLatitude();
                        longitude = currentLocation.getLongitude();

                        jsonParams = new JSONObject();
                        try {
                            jsonParams.put("latitude", latitude);
                            jsonParams.put("longitude", longitude);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        StringEntity entity;
                        entity = new StringEntity(jsonParams.toString(), "UTF-8");

                        String server = Constants.getInstance().getServerAddress();
                        client.post(context, server + "/location/pinglocation/" + Constants.getInstance().getUserId(), entity, "application/json", new AsyncHttpResponseHandler() {

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            }

                            @Override
                            public void onRetry(int retryNo) {
                            }
                        });
                    }

                    try {
                        currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        Toast toast = Toast.makeText(this, "Logoff efetuado com sucesso", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}