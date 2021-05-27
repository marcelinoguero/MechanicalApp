package com.tcc.mechanicalapp;

import androidx.annotation.NonNull;

import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.ShapePath;

public class ConcaveRoundedCorner extends CornerTreatment {
    @Override
    public void getCornerPath(@NonNull ShapePath shapePath, float angle, float interpolation, float radius) {
        float interpolatedRadious = radius*interpolation;
        shapePath.reset(0f, interpolatedRadious, 180f, 180f - angle);
        shapePath.addArc(-interpolatedRadious, -interpolatedRadious, interpolatedRadious, interpolatedRadious, 90f, -angle);
    }
}
