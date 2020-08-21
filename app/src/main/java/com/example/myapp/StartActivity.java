package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getPreferencesData();
                startActivity(intent);
                finish();
            }
        },2000);
    }

    private Intent getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        if(sp.contains("username") && sp.contains("password") && sp.contains("rememberMe")){
            return new Intent(StartActivity.this, MainActivity.class);
        } else {
            return new Intent(StartActivity.this, LoginActivity.class);
        }
    }

}