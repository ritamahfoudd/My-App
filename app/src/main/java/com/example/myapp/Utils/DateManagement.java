package com.example.myapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DateManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String PREFS_NAME = "PrefsFile";
    String LOGIN_DATE = "loginDate";

    public DateManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveDate(String date) {
        editor.putString(LOGIN_DATE, date).commit();
    }

    public String getDate() {
        return sharedPreferences.getString(LOGIN_DATE, "not found");
    }

    public void clearDate() {
        editor.remove(LOGIN_DATE).apply();
    }
}
