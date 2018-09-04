package com.kokonut.test.raul.kokonuttest.view;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;

import com.kokonut.test.raul.kokonuttest.R;


public class SplashActivity extends BaseActivity {
    private static final int SPLASH_DELAY = 1000;
    private Handler splashTimer;

    @Override
    void createView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    void createController() {
        splashTimer = new Handler();
    }

    @Override
    void setupView() {

    }

    @Override
    void refresh() {
        splashTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoginActivity();
            }
        },SPLASH_DELAY);
    }

    private void startLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
