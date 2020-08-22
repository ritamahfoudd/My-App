package com.example.myapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.Models.Item;
import com.example.myapp.Adapters.ItemAdapter;
import com.example.myapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements ItemAdapter.OnItemClickListener {

    private ItemAdapter.OnItemClickListener onItemClickListener = this;
    private RecyclerView rvItems;
    private ItemAdapter adapter;
    private List<Item> listItems;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final static String URL_DATA = "https://jsonplaceholder.typicode.com/todos";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        listItems = new ArrayList<>();

        populateList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void populateList() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Item item = new Item((int) jsonObject.get("userId"), (int) jsonObject.get("id"),
                                (String) jsonObject.get("title"), (boolean) jsonObject.get("completed"));
                        listItems.add(item);
                    }

                    adapter = new ItemAdapter(listItems, onItemClickListener);
                    rvItems.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        Item item = listItems.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.item, bundle);
    }

}