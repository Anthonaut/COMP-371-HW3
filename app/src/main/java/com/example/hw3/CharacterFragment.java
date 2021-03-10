package com.example.hw3;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CharacterFragment extends Fragment {
    private View view;
    private AsyncHttpClient client = new AsyncHttpClient();
    private static final String api_url = "https://rickandmortyapi.com/api/character";
    private ImageView characterPic;
    private TextView charName;
    private TextView charStatus;
    private TextView charSpecies;
    private TextView charGender;
    private TextView charOrigin;
    private TextView charLocation;
    private TextView charEpisodeAppearances;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);
        characterPic = view.findViewById(R.id.imageView_characterPic);
        charName = view.findViewById(R.id.textView_characterName);
        charStatus = view.findViewById(R.id.textView_characterStatus);
        charSpecies = view.findViewById(R.id.textView_characterSpecies);
        charGender = view.findViewById(R.id.textView_characterGender);
        charOrigin = view.findViewById(R.id.textView_characterOrigin);
        charLocation = view.findViewById(R.id.textView_characterLocation);
        charEpisodeAppearances = view.findViewById(R.id.textView_characterEpisodeAppearances);

        client.addHeader("Accept", "*/*"); // HTTP Header for request
        client.get(api_url, new AsyncHttpResponseHandler() { // HTTP get request to retrieve info via API call
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody)); // Show what API call looks like

                try {
                    JSONObject json = new JSONObject(new String(responseBody)); // Convert string from API call into JSON
                    JSONArray characterArray = json.getJSONArray("results");
                    int randomID = randomIDGenerator(characterArray.length());
                    JSONObject character = characterArray.getJSONObject(randomID);
                    Log.d("Character", character.toString());
                    Picasso.get().load(character.getString("image")).into(characterPic);
                    charName.setText(character.getString("name"));
                    charStatus.setText(character.getString("status"));
                    charSpecies.setText(character.getString("species"));
                    charGender.setText(character.getString("gender"));
                    charOrigin.setText(character.getJSONObject("origin").getString("name"));
                    charLocation.setText(character.getJSONObject("location").getString("name"));
                    JSONArray episodesArray = character.getJSONArray("episode");
                    charEpisodeAppearances.setText(retrieveEpisodeNum(episodesArray.getString(0)) + ", ");
                    for (int i = 1; i < episodesArray.length(); i++) {
                        if (i == episodesArray.length() - 1) {
                            Log.d("Did I stop", "?");
                            charEpisodeAppearances.append(retrieveEpisodeNum(episodesArray.getString(i)));
                        }
                        else {
                            Log.d("Did i loop", "?");
                            charEpisodeAppearances.append(retrieveEpisodeNum(episodesArray.getString(i)) + ", ");
                        }
                    }
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

    public String retrieveEpisodeNum(String episodeUrl) {
        int slashPosition = episodeUrl.lastIndexOf("/") + 1; // it's inclusive so must add 1
        String num = episodeUrl.substring(slashPosition);
        return num;
    }

}
