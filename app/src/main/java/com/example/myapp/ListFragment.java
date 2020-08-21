package com.example.myapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private TextView tvLoading;
    private ListView lvItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateList();
    }

    private void populateList() {
        FetchItems fetchItems = new FetchItems(this);
        fetchItems.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvLoading.setVisibility(View.VISIBLE);
                lvItems.setVisibility(View.INVISIBLE);
                populateList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    public void updateAdapter(ArrayList<Item> items) {
        tvLoading = getView().findViewById(R.id.tvLoading);
        tvLoading.setVisibility(View.INVISIBLE);

        lvItems = getView().findViewById(R.id.lvItems);
        lvItems.setVisibility(View.VISIBLE);
        final ItemAdapter adapter = new ItemAdapter(getActivity(), items);
        lvItems.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.item, bundle);
            }
        });
    }

}