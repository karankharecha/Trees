package com.datastructure.trees;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation slideRight = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_right);
        Animation slideDown = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_down);
        Animation slideLeft = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_left);
        Animation slideUp = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_up);

        findViewById(R.id.upper).startAnimation(slideRight);
        findViewById(R.id.right).startAnimation(slideDown);
        findViewById(R.id.lower).startAnimation(slideLeft);
        findViewById(R.id.left).startAnimation(slideUp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, TreeActivity.class));
                finish();
            }
        }, 2000);
    }
}
