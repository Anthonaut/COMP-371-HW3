package com.example.hw3;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EpisodeFragment extends Fragment {
    private View view;
    private static final String api_url = "https://rickandmortyapi.com/api/episode";
    private AsyncHttpClient client = new AsyncHttpClient();
    private TextView textView_episodeName;
    private TextView textView_airDate;
    private ImageView imageViewOne;
    private ImageView imageViewTwo;
    private ImageView imageViewThree;
    private String charPicUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_episode, container, false);
        textView_airDate = view.findViewById(R.id.textView_episodeAirDate);
        textView_episodeName = view.findViewById(R.id.textView_episodeTitle);

        client.addHeader("Accept", "*/*"); // HTTP Header for request
        client.get(api_url, new AsyncHttpResponseHandler() { // HTTP get request to retrieve info via API call
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody)); // Show what API call looks like

                try {
                    JSONObject json = new JSONObject(new String(responseBody)); // Convert string from API call into JSON
                    JSONArray episodesArray = json.getJSONArray("results");
                    int randomID = randomIDGenerator(episodesArray.length());
                    JSONObject episode = episodesArray.getJSONObject(randomID);
                    Log.d("Episode", episode.toString());
                    textView_episodeName.setText(episode.getString("name"));
                    textView_airDate.setText(episode.getString("air_date"));
                    JSONArray charactersList = episode.getJSONArray("characters");
                    Log.d("Char Url", charactersList.getString(0));
                    /*if (charactersList.length() == 1) {
                        Picasso.get().load(getCharPicUrl(charactersList.getString(0))).into(imageViewTwo);
                    }
                    else if (charactersList.length() == 2) {
                        Picasso.get().load(getCharPicUrl(charactersList.getString(0))).into(imageViewOne);
                        Picasso.get().load(getCharPicUrl(charactersList.getString(1))).into(imageViewThree);
                    }
                    else {
                        Picasso.get().load(getCharPicUrl(charactersList.getString(0))).into(imageViewOne);
                        Picasso.get().load(getCharPicUrl(charactersList.getString(1))).into(imageViewTwo);
                        Picasso.get().load(getCharicUrl(charactersList.getString(2))).into(imageViewThree);
                    }*/
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

    public int randomIDGenerator(int totalCharCount) {
        int min = 0;
        int max = totalCharCount - 1; // max is exclusive
        int random_int = (int)(Math.random() * (max - min + 1) + min);
        return random_int;
    }

    public String getCharPicUrl(String charUrl) {

        client.addHeader("Accept", "*/*"); // HTTP Header for request
        client.get(charUrl, new AsyncHttpResponseHandler() { // HTTP get request to retrieve info via API call
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("An api call", "is being made");
                Log.d("api response", new String(responseBody)); // Show what API call looks like

                try {
                    JSONObject json = new JSONObject(new String(responseBody)); // Convert string from API call into JSON
                    charPicUrl = json.getString("image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody)); // Just in case no api call or similar happens
            }
        });
        //Log.d("CharPic Url", charPicUrl);
        return charPicUrl;
    }

}
