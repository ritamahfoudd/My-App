package com.example.myapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.Utils.DateManagement;

public class TimerFragment extends Fragment {

    private TextView tvDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        tvDate = view.findViewById(R.id.tvDate);

        DateManagement dateManagement = new DateManagement(getContext());
        tvDate.setText(dateManagement.getDate());

        return view;
    }
}