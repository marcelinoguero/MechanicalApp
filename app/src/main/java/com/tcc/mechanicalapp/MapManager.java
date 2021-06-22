package com.tcc.mechanicalapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private List<Marker> markerList;
    private Marker userLocation;

    public MapManager(GoogleMap map) {

        mMap = map;
        markerList = new ArrayList<Marker>();

        String dark_pattern = Constants.getInstance().getMapStyle();
        MapStyleOptions dark_style = new MapStyleOptions(dark_pattern);
        mMap.setMapStyle(dark_style);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setMinZoomPreference(15.0f);
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public void createProviderMarker (Context context, double latitude, double longitude, String title, int icon) {

        LatLng location = new LatLng(latitude, longitude);
        Marker newMarker = mMap.addMarker(new MarkerOptions().position(location).title(title).icon(bitmapDescriptorFromVector(context,icon)));

        markerList.add(newMarker);

        ValueAnimator markerAnimation = ValueAnimator.ofFloat(0, 1);
        markerAnimation.setDuration(500);
        markerAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                newMarker.setAlpha((float) animation.getAnimatedValue());
            }
        });
        markerAnimation.start();

        return;
    }

    public void createUserMarker (Context context, double latitude, double longitude, String title, int icon) {
        LatLng location = new LatLng(latitude, longitude);
        Marker newMarker = mMap.addMarker(new MarkerOptions().position(location).title(title).icon(bitmapDescriptorFromVector(context,icon)));
        setCameraPosition(latitude, longitude);

        if (userLocation != null) {
            userLocation.remove();
        }
        userLocation = newMarker;

        return;
    }

    public void setCameraPosition(double latitude, double longitude, float zoom) {
        LatLng location = new LatLng(latitude, longitude);
        cameraPosition = new CameraPosition.Builder()
                .target(location)
                .tilt(0)
                .zoom(zoom)
                .build();
        return;
    }

    public void setCameraPosition(double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);
        cameraPosition = new CameraPosition.Builder()
                .target(location)
                .tilt(0)
                .zoom(mMap.getCameraPosition().zoom)
                .build();
        return;
    }

    public void animateMarker() {
        final Handler handler = new Handler();

        final long startTime = SystemClock.uptimeMillis();
        final long duration = 300;

        Marker marker = markerList.get(0);

        Projection proj = mMap.getProjection();
        final LatLng markerLatLng = marker.getPosition();
        Point startPoint = proj.toScreenLocation(markerLatLng);
        startPoint.offset(0, -10);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    public void focusCamera() {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return;
    }

    public void clearMarkers() {

        for (Marker marker : markerList) {
            marker.remove();
        }

        markerList.removeAll(markerList);

        return;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, false));
    }
}
