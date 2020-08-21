package com.example.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class TimerFragment extends Fragment {

    private static Chronometer chronometer;
    private TextView tvDate;
    private boolean running;
    private static long pauseOffset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
        super.onStart(); 
    }

    @Override
    public void onResume() {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
        super.onStop();
    }

    public static void resetChronometer(){
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        chronometer = view.findViewById(R.id.chronometer);

        tvDate = view.findViewById(R.id.tvDate);
        SharedPreferences sp = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        tvDate.setText(sp.getString("date", "not found"));

        return view;
    }
}