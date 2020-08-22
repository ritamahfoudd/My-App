package com.example.myapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.widget.Chronometer;

import com.example.myapp.Fragments.TimerFragment;
import com.example.myapp.R;
import com.example.myapp.Utils.SessionManagement;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static Chronometer chronometer;
    private static boolean running;
    private static long pauseOffset;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        chronometer = findViewById(R.id.chronometer);

        sharedPreferences = getSharedPreferences("PrefsFile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        init();

        startChronometer();
    }

    private void init() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else {
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId() == R.id.timer) {
            leave();
        } else if (Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId() == R.id.item) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.list);
        } else {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.timer);
        }
    }

    private void leave() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exitAlert);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                if(!sessionManagement.getSession())
                    resetChronometer();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.timer:
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.main, true).build();
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.timer, null, navOptions);
                break;
            case R.id.list:
                if (isValidDestination(R.id.list)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.list);
                }
                break;
            case R.id.logout:
                if (isValidDestination(R.id.logout)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.logout);
                }
                break;
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isValidDestination(int destination) {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    @Override
    public void onStart() {
        startChronometer();
        super.onStart();
    }

    @Override
    public void onResume() {
        startChronometer();
        super.onResume();
    }

    @Override
    public void onPause() {
        pauseChronometer();
        super.onPause();
    }

    @Override
    public void onStop() {
        pauseChronometer();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        pauseChronometer();
        super.onDestroy();
    }

    public static void resetChronometer(){
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    private void startChronometer() {
        if(!running) {
            pauseOffset = sharedPreferences.getLong("pauseOffset", 0);
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    private void pauseChronometer() {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            editor.putLong("pauseOffset", pauseOffset).apply();
        }
    }


}