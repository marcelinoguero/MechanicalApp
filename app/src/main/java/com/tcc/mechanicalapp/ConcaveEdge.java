package com.tcc.mechanicalapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.LogWriter;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

import java.util.logging.Logger;

public class ConcaveEdge extends EdgeTreatment {

    private float scale = NavActivity.scale;
    @Override
    public void getEdgePath(float length, float center, float interpolation, @NonNull ShapePath shapePath) {

        float left = center - 100f;
        float top = -100f;
        float right = center + 100f;
        float bottom = 100f;

        Log.d("DebugCenter", "Center: "+center);
        shapePath.addArc(left, top, right, bottom, -180f, -180f);
    }
}
