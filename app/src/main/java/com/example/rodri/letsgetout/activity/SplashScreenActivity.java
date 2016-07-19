package com.example.rodri.letsgetout.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.util.Util;

/**
 * Created by rodri on 7/6/2016.
 */
public class SplashScreenActivity extends Activity {

    private long duration = 2500; // duration in milliseconds
    private boolean active = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(getApplicationContext() ,MainActivity.class);
        startActivity(intent);
        finish();

        /**final Thread splashThread = new Thread() {

            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < duration)) {
                        sleep(100);
                        if (active) waited += 100;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        splashThread.start();

         */

    }
}
