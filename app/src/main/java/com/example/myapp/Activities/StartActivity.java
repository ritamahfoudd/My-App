package com.example.myapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapp.R;
import com.example.myapp.Utils.SessionManagement;

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
        SessionManagement sessionManagement = new SessionManagement(StartActivity.this);
        if(sessionManagement.getSession()){
            return new Intent(StartActivity.this, MainActivity.class);
        } else {
            return new Intent(StartActivity.this, LoginActivity.class);
        }
    }

}