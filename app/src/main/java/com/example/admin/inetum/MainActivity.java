package com.example.admin.inetum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    JSONPlaceholder jsonPlaceholder;
    //    List<Channel> channels;
    private static final String JSON_URL =
            "http://ott.online.meo.pt/catalog/v9/Channels?UserAgent=AND&$filter=substringof(%27MEO_Mobile%27,AvailableOnChannels) and IsAdult eq false&$orderby=ChannelPosition asc&$inlinecount=allpages";
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.channelsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        channels = new ArrayList<>();
//        extractChannels();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ott.online.meo.pt/Catalog/v9/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);

        getChannel();

    }


    private void getChannel() {
        Call<List<Channel>> call = jsonPlaceholder.getChannel();
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call, retrofit2.Response<List<Channel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Channel> channelList = response.body();
                ChannelAdapter channelAdapter = new ChannelAdapter(MainActivity.this, channelList);
                recyclerView.setAdapter(channelAdapter);
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

/*
    private void extractChannels() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray channelArray = response.getJSONArray("value");
                    for (int i = 0; i < channelArray.length(); i++) {
                        JSONObject channelObject = channelArray.getJSONObject(i);
                        String channelTitle = channelObject.getString("Title");
                        Channel channel = new Channel();
                        channel.setTitle(channelTitle);
                        CurrentProgram currentProgram = channel.getCurrentProgram();
                        CurrentProgram nowAndNext = getNowAndNext(channelTitle);
                        currentProgram.setProgramTitle(nowAndNext.getProgramTitle());
                        currentProgram.setSynopsis(nowAndNext.getSynopsis());
                        currentProgram.setCoverImageURL(nowAndNext.getCoverImageURL());
                        channels.add(channel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(), channels);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }
*/

    private CurrentProgram getNowAndNext(String channelTitle) {
        CurrentProgram currentProgram = new CurrentProgram();
        final String JSON_URL_CURRENT =
                "http:ott.online.meo.pt/Program/v9/Programs/NowAndNextLiveChannelPrograms?UserAgent=AND&$filter=CallLetter eq %27" + channelTitle + "%27&$orderby=StartDate asc";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL_CURRENT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray programArray = response.getJSONArray("value");
                    JSONObject programObject = programArray.getJSONObject(0);
                    String programTitle = programObject.getString("Title");
                    String synopsis = programObject.getString("Synopsis");
                    currentProgram.setProgramTitle(programTitle);
                    currentProgram.setSynopsis(synopsis);
                    currentProgram.setCoverImageURL("http://cdn-images.online.meo.pt/eemstb/ImageHandler.ashx?evTitle=" + programTitle + "&chCallLetter=" + channelTitle + "&profile=16_9&width=320");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        return currentProgram;
    }

}