/***************************************************************************************
 *    References:
 *
 *    Title: Easy Splash Screen - Android Studio Tutorial
 *    Author: Coding in Flow
 *    Date: 2017
 *    Code version: 1.0
 *    Availability: https://www.youtube.com/watch?v=gt1WYT0Qpfk
 *
 ***************************************************************************************/

package com.example.taskappv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskappv2.login.MainActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run () {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, 3000);
    }
}
