package com.tcc.mechanicalapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LocationManager {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Context viewContext;
    private Activity currentActivity;
    private CancellationToken cancellationToken;
    private Location currentLocation;

    public LocationManager(Context context, Activity activity){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        viewContext = context;
        currentActivity = activity;

        cancellationToken = new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @NotNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull @NotNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        };
    }

    public void plotUserLocation(MapManager mapManager) {
        if (ActivityCompat.checkSelfPermission(viewContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(currentActivity, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION}, 101
            );
        }

        Task<Location> task = fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationToken);
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mapManager.createUserMarker(viewContext, location.getLatitude(), location.getLongitude(),"Minha posição" ,R.drawable.m_user_icon);
                    if (currentLocation == null) {
                        mapManager.setCameraPosition(location.getLatitude(), location.getLongitude());
                        mapManager.focusCamera();
                    }
                    currentLocation = location;
                }
            }
        });
    }

    public void getLocation () {
        if (ActivityCompat.checkSelfPermission(viewContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(currentActivity, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION}, 101
            );
        }

        Task<Location> task = fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationToken);
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                }
            }
        });

        return;
    }


    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void requestNearbyProviders(MapManager mapManager, ProvidersListManager providersListManager) {
        AsyncHttpClient client = new AsyncHttpClient();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("radius",1000);
            jsonParams.put("latitude",currentLocation.getLatitude());
            jsonParams.put("longitude",currentLocation.getLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity entity = null;
        entity = new StringEntity(jsonParams.toString(), "UTF-8");

        String server = Constants.getInstance().getServerAddress();
        client.post(viewContext, server+"/location/getnearby/freelancer_mechanic", entity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                mapManager.clearMarkers();
                providersListManager.clearList();
                try {
                    JSONArray testV = new JSONArray(new String(response));
                    JSONObject providerObject;
                    String name;
                    String utype;
                    double longitude;
                    double latitude;
                    int starRating;

                    for (int i = 0; i < testV.length(); i++) {
                        providerObject = testV.getJSONObject(i);
                        name = providerObject.getJSONArray("provider").getJSONObject(0).getString("name");
                        latitude = providerObject.getJSONObject("location").getJSONArray("coordinates").getDouble(0);
                        longitude = providerObject.getJSONObject("location").getJSONArray("coordinates").getDouble(1);
                        utype = providerObject.getString("utype");
                        starRating = providerObject.getInt("rating");

                        if (utype.equals("freelancer_mechanic")) {
                            providersListManager.addProvider(name, "Mecânico", starRating);
                        } else {
                            mapManager.createProviderMarker(viewContext, latitude, longitude, name, R.drawable.m_workshop_icon);
                            providersListManager.addProvider(name, "Oficina Mecânica", starRating);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }

            @Override
            public void onRetry(int retryNo) {

            }
        });
    }
}
