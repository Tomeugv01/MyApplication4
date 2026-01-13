package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private TextView topTitle, bottomTitle;
    private TableLayout logoTable;
    private ImageView img1, img2, img3, img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        topTitle = findViewById(R.id.topTitle);
        bottomTitle = findViewById(R.id.bottomTitle);
        logoTable = findViewById(R.id.logoTable);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        // Reduce title size a bit to ensure the image grid is visible on smaller screens
        topTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f);
        bottomTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f);

        // Ensure images are explicitly set and visible (prevents cases where resources aren't loaded into ImageView)
        try {
            img1.setImageResource(R.drawable.splash1);
            img2.setImageResource(R.drawable.splash2);
            img3.setImageResource(R.drawable.splash3);
            img4.setImageResource(R.drawable.splash4);
        } catch (Exception e) {
            // If resource lookup fails, fall back to leaving src set in XML — don't crash
            e.printStackTrace();
        }
        // Debug: log whether ImageView drawable was successfully set
        Log.d("SplashDebug", "img1 drawable=" + (img1.getDrawable() != null));
        Log.d("SplashDebug", "img2 drawable=" + (img2.getDrawable() != null));
        Log.d("SplashDebug", "img3 drawable=" + (img3.getDrawable() != null));
        Log.d("SplashDebug", "img4 drawable=" + (img4.getDrawable() != null));

        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);
        img3.setVisibility(View.VISIBLE);
        img4.setVisibility(View.VISIBLE);
        img1.setAlpha(1f);
        img2.setAlpha(1f);
        img3.setAlpha(1f);
        img4.setAlpha(1f);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);

        // Start animations for titles
        if (fadeIn != null) topTitle.startAnimation(fadeIn);
        if (fadeIn2 != null) bottomTitle.startAnimation(fadeIn2);

        // Intentionally no transition when the second fade finishes — user requested no redirect.
        // If you want to later trigger navigation, add a click handler or a delayed Runnable here.

        // Apply layout animation to the table (also set in XML)
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        if (controller != null && logoTable != null) {
            logoTable.setLayoutAnimation(controller);
            logoTable.startLayoutAnimation();
        }

        // Additionally, explicitly animate individual image views with staggered offsets
        // Load separate animation instances so we can set different startOffset values
        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        Animation a2 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        Animation a3 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        Animation a4 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);

        if (a1 != null) {
            a1.setStartOffset(0);
            img1.startAnimation(a1);
        }
        if (a2 != null) {
            a2.setStartOffset(150);
            img2.startAnimation(a2);
        }
        if (a3 != null) {
            a3.setStartOffset(300);
            img3.startAnimation(a3);
        }
        if (a4 != null) {
            a4.setStartOffset(450);
            img4.startAnimation(a4);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Clear animations to avoid leaks
        if (topTitle != null) topTitle.clearAnimation();
        if (bottomTitle != null) bottomTitle.clearAnimation();
        if (logoTable != null) {
            logoTable.clearAnimation();
            for (int i = 0; i < logoTable.getChildCount(); i++) {
                View row = logoTable.getChildAt(i);
                if (row != null) row.clearAnimation();
            }
        }
        if (img1 != null) img1.clearAnimation();
        if (img2 != null) img2.clearAnimation();
        if (img3 != null) img3.clearAnimation();
        if (img4 != null) img4.clearAnimation();
    }
}
