package com.example.myapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.Models.User;
import com.example.myapp.R;
import com.example.myapp.Utils.DateManagement;
import com.example.myapp.Utils.SessionManagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName, etPassword;
    private Button btLogin;
    private CheckBox cbRememberMe;

    public SharedPreferences mPrefs;
    public static final String PREFS_NAME = "PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();

        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String username = etUserName.getText().toString();
        String pass = etPassword.getText().toString();

        if( (username.equals("user1") && pass.equals("pass1")) || (username.equals("user2") && pass.equals("pass2")) ){
            SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
            if(cbRememberMe.isChecked()){
                sessionManagement.saveSession(new User(username, pass));
            } else {
                sessionManagement.removeSession();
            }

            DateManagement dateManagement = new DateManagement(LoginActivity.this);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            dateManagement.saveDate(dateFormat.format(cal.getTime()));

            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Wrong username or password!", Toast.LENGTH_LONG).show();
        }
    }

    public void findViewsById() {
        etPassword = findViewById(R.id.etPassword);
        etUserName = findViewById(R.id.etUsername);
        btLogin = findViewById(R.id.btLogin);
        cbRememberMe = findViewById(R.id.cbRememberMe);
    }

}