package com.example.hw3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LocationFragment extends Fragment {
    private View view;
    private ArrayList<Location> locations;
    private RecyclerView recyclerView;
    private JSONArray locationsArray;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_locations);
        locations = new ArrayList<>();
        client.addHeader("Accept", "*/*"); // HTTP Header for request
        client.get(getArguments().getString("location link"), new AsyncHttpResponseHandler() { // HTTP get request to retrieve info via API call
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody)); // Show what API call looks like

                try {
                    JSONObject json = new JSONObject(new String(responseBody)); // Convert string from API call into JSON
                    JSONArray locationsArray = json.getJSONArray("results");
                    for (int i = 0; i < locationsArray.length(); i++) {
                        JSONObject locationObject = locationsArray.getJSONObject(i);
                        Location location = new Location(locationObject.getString("name"), locationObject.getString("type"), locationObject.getString("dimension"));
                        Log.d("Location Name", location.getName());
                        locations.add(location);
                    }
                    LocationAdapter adapter = new LocationAdapter(locations);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Log.d("Recycler View not", "showing");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody)); // Just in case no api call or similar happens
            }
        });

        return view;
    }


}
