package com.example.myapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchItems extends AsyncTask<Void, Void, Void> {

    ListFragment listFragment;
    ArrayList<Item> items = new ArrayList<>();

    public FetchItems(ListFragment listFragment){
        this.listFragment = listFragment;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        StringBuilder data = new StringBuilder();

        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/todos");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data.append(line);
            }

            JSONArray jsonArray = new JSONArray(data.toString());
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Item item = new Item((int) jsonObject.get("userId"), (int) jsonObject.get("id"),
                        (String) jsonObject.get("title"), (boolean) jsonObject.get("completed"));
                items.add(item);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listFragment.updateAdapter(items);
    }
}
