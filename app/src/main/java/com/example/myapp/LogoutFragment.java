package com.example.myapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class LogoutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        Button btLogout = view.findViewById(R.id.btLogout);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }

    private void logout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.logoutAlert);
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
                TimerFragment.resetChronometer();
                SharedPreferences sp = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(getActivity(), "Logged out!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}