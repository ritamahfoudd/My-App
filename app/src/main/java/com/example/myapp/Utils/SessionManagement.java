package com.example.myapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapp.Models.User;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String PREFS_NAME = "PrefsFile";
    String USERNAME = "username";
    String PASSWORD = "password";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user) {
        editor.putString(USERNAME, user.getUsername()).commit();
        editor.putString(PASSWORD, user.getPassword()).commit();
    }

    public boolean getSession() {
        return sharedPreferences.contains(USERNAME);
    }

    public void removeSession() {
        editor.remove(USERNAME).apply();
    }
}
