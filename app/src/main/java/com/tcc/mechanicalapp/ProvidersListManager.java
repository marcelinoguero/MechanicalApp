package com.tcc.mechanicalapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

public class ProvidersListManager {
    private LinearLayout list;
    private Context viewContext;
    private Activity currentActivity;

    public ProvidersListManager (Context context, Activity activity) {
        viewContext = context;
        currentActivity = activity;
    }

    public void addProvider(String providerName, String providerType, int starsNumber) {

        int width = 0;
        int height = 0;
        int topMargin = 0;
        int rightMargin = 0;
        int leftMargin = 0;

        list = currentActivity.findViewById(R.id.providers_list);

        height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, viewContext.getResources().getDisplayMetrics()));
        topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, viewContext.getResources().getDisplayMetrics()));
        rightMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, viewContext.getResources().getDisplayMetrics()));
        leftMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, viewContext.getResources().getDisplayMetrics()));

        RelativeLayout providerCard = new RelativeLayout(viewContext);
        providerCard.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        providerCard.setBackground(viewContext.getDrawable(R.drawable.card_on_list_bg));
        RelativeLayout.LayoutParams providerCardParams = (RelativeLayout.LayoutParams) providerCard.getLayoutParams();
        providerCardParams.topMargin = topMargin;
        providerCardParams.rightMargin = rightMargin;
        providerCardParams.leftMargin = leftMargin;

        height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, viewContext.getResources().getDisplayMetrics()));
        width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, viewContext.getResources().getDisplayMetrics()));
        topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, viewContext.getResources().getDisplayMetrics()));
        rightMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, viewContext.getResources().getDisplayMetrics()));

        ImageButton callServiceButton = new ImageButton(viewContext);
        callServiceButton.setLayoutParams(new RelativeLayout.LayoutParams(height, width));
        callServiceButton.setBackground(viewContext.getDrawable(R.drawable.call_service_icon));
        RelativeLayout.LayoutParams callServiceButtonParams = (RelativeLayout.LayoutParams) callServiceButton.getLayoutParams();
        callServiceButtonParams.rightMargin = rightMargin;
        callServiceButtonParams.topMargin = topMargin;
        callServiceButtonParams.alignWithParent = true;
        callServiceButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        callServiceButtonParams.addRule(RelativeLayout.CENTER_VERTICAL);
        callServiceButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        callServiceButton.setId(R.id.call_id);

        leftMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, viewContext.getResources().getDisplayMetrics()));
        topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, viewContext.getResources().getDisplayMetrics()));

        TextView providerTypeText = new TextView(viewContext);
        providerTypeText.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        providerTypeText.setText(providerType);
        providerTypeText.setTextSize(15);
        RelativeLayout.LayoutParams providerTypeTextParams = (RelativeLayout.LayoutParams) providerTypeText.getLayoutParams();
        providerTypeTextParams.topMargin = topMargin;
        providerTypeTextParams.leftMargin = leftMargin;
        providerTypeTextParams.addRule(RelativeLayout.RIGHT_OF, R.id.photo_id);
        providerTypeTextParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        providerTypeText.setId(R.id.type_id);
        providerTypeText.setTextColor(ContextCompat.getColor(viewContext, R.color.white));

        leftMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, viewContext.getResources().getDisplayMetrics()));
        rightMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, viewContext.getResources().getDisplayMetrics()));

        RatingBar starsRating = new RatingBar(viewContext, null, android.R.attr.ratingBarStyleSmall);
        starsRating.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams starsRatingParams = (RelativeLayout.LayoutParams) starsRating.getLayoutParams();
        starsRatingParams.rightMargin = rightMargin;
        starsRatingParams.leftMargin = leftMargin;
        starsRatingParams.addRule(RelativeLayout.BELOW, R.id.type_id);
        starsRatingParams.addRule(RelativeLayout.RIGHT_OF, R.id.photo_id);
        starsRating.setNumStars(5);
        starsRating.setRating(starsNumber);
        starsRating.setId(R.id.rating_id);

        leftMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, viewContext.getResources().getDisplayMetrics()));
        topMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, viewContext.getResources().getDisplayMetrics()));

        TextView providerNameText = new TextView(viewContext);
        providerNameText.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        providerNameText.setText(providerName);
        providerNameText.setTextSize(20);
        RelativeLayout.LayoutParams providerNameTextParams = (RelativeLayout.LayoutParams) providerNameText.getLayoutParams();
        providerNameTextParams.topMargin = topMargin;
        providerNameTextParams.leftMargin = leftMargin;
        providerNameTextParams.addRule(RelativeLayout.RIGHT_OF, R.id.photo_id);
        providerNameTextParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        providerNameText.setId(R.id.name_id);
        providerNameText.setTextColor(ContextCompat.getColor(viewContext, R.color.white));
        providerNameText.setTypeface(null, Typeface.BOLD);

        height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, viewContext.getResources().getDisplayMetrics()));
        width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, viewContext.getResources().getDisplayMetrics()));
        leftMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, viewContext.getResources().getDisplayMetrics()));

        View providerPhoto = new View(viewContext);
        providerPhoto.setId(R.id.photo_id);
        providerPhoto.setLayoutParams(new RelativeLayout.LayoutParams(height, width));
        RelativeLayout.LayoutParams providerPhotoParams = (RelativeLayout.LayoutParams) providerPhoto.getLayoutParams();
        providerPhotoParams.leftMargin = leftMargin;
        providerPhotoParams.addRule(RelativeLayout.CENTER_VERTICAL);
        providerPhoto.setBackground(viewContext.getDrawable(R.drawable.default_user_profile));

        providerCard.addView(callServiceButton);
        providerCard.addView(providerTypeText);
        providerCard.addView(starsRating);
        providerCard.addView(providerNameText);
        providerCard.addView(providerPhoto);

        list.addView(providerCard);
    }

    public void removeView(int index) {
        list.removeViewAt(index);
        return;
    }

    public void clearList() {
        if (list != null) {
            list.removeAllViews();
        }
    }

}
