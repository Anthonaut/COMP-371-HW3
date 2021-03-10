package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class MainActivity extends AppCompatActivity {

    private String characterLink;
    private Button button_character;
    private String locationlLink;
    private Button button_location;
    private String episodeLink;
    private Button button_episode;
    private static final String api_url = "https://rickandmortyapi.com/api";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_character = findViewById(R.id.button_character);
        button_location = findViewById(R.id.button_location);
        button_episode = findViewById(R.id.button_episode);

        client.addHeader("Accept", "*/*"); // HTTP Header for request
        client.get(api_url, new AsyncHttpResponseHandler() { // HTTP get request to retrieve info via API call
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody)); // Show what API call looks like

                try {
                    JSONObject json = new JSONObject(new String(responseBody)); // Convert string from API call into JSON
                    characterLink = json.getString("characters");
                    locationlLink = json.getString("locations");
                    episodeLink = json.getString("episodes");
                    Bundle bundle = new Bundle();
                    bundle.putString("character link", characterLink);
                    bundle.putString("location link", locationlLink);
                    bundle.putString("episode link", episodeLink);
                    CharacterFragment characterFragment = new CharacterFragment();
                    characterFragment.setArguments(bundle);
                    LocationFragment locationFragment = new LocationFragment();
                    locationFragment.setArguments(bundle);
                    EpisodeFragment episodeFragment = new EpisodeFragment();
                    episodeFragment.setArguments(bundle);
                    button_character.setOnClickListener(v-> loadFragment(characterFragment));
                    button_location.setOnClickListener(v-> loadFragment(locationFragment));
                    button_episode.setOnClickListener(v -> loadFragment(episodeFragment));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody)); // Just in case no api call or similar happens
            }
        });

    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }

}