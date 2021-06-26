package com.tcc.mechanicalapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.card.MaterialCardView;

public class NavTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_test);

        Constants.getInstance().setScale(getResources().getDisplayMetrics().density);
        MaterialCardView card = findViewById(R.id.card);
        card.setShapeAppearanceModel(card.getShapeAppearanceModel().toBuilder().setTopEdge(new ConcaveEdge()).setAllCornerSizes(0).setTopLeftCornerSize(50).setTopRightCornerSize(50).build());
    }
}