package com.example.myapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        TextView tvUserId = view.findViewById(R.id.tvUserId);
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvCompleted = view.findViewById(R.id.tvCompleted);

        assert getArguments() != null;
        Item item = (Item) getArguments().getSerializable("item");

        assert item != null;
        tvUserId.setText(String.valueOf(item.getUserId()));
        tvId.setText(String.valueOf(item.getId()));
        tvTitle.setText(item.getTitle());
        tvCompleted.setText(String.valueOf(item.isCompleted()));

        return view;
    }
}