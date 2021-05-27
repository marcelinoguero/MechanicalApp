package com.tcc.mechanicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;

public class NavTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_test);

        MaterialCardView card = findViewById(R.id.card);
        card.setShapeAppearanceModel(card.getShapeAppearanceModel().toBuilder().setTopEdge(new ConcaveEdge()).setAllCornerSizes(0).setTopLeftCornerSize(50).setTopRightCornerSize(50).build());
    }
}