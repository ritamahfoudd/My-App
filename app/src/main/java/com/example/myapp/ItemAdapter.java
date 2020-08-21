package com.example.myapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Activity context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        Item item = getItem(position);
        TextView tvTitle = listItemView.findViewById(R.id.tvTitle);

        assert item != null;
        tvTitle.setText(item.getTitle());

        if (item.isCompleted()) {
            listItemView.setBackgroundColor(Color.LTGRAY);
        } else {
            listItemView.setBackgroundColor(Color.WHITE);
        }

        return listItemView;
    }
}
