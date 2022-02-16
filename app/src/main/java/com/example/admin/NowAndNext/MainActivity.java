package com.example.admin.NowAndNext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity
        extends AppCompatActivity
        implements PaginationCallBack {

    RecyclerView recyclerView;
    List<Channel> channels;
    private static String JSON_URL = "http://ott.online.meo.pt/catalog/v9/Channels?$format=json&UserAgent=AND&$filter=substringof%28%27MEO_Mobile%27%2CAvailableOnChannels%29%20and%20IsAdult%20eq%20false&$orderby=ChannelPosition%20asc&$skip=";
    Adapter adapter;

    static Integer skip = 0;
    private PaginationCallBack callback = new PaginationCallBack() {
        @Override
        public void loadNextPage() {

            skip += 10;
            extractChannels(skip);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channels = new ArrayList<>();

        recyclerView = findViewById(R.id.channelsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new Adapter(getApplicationContext(), channels, callback);
        recyclerView.setAdapter(adapter);

        extractChannels(skip);

    }


    private void setRecyclerView() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
/*
                if (linearLayoutManager.findLastVisibleItemPosition() >
                        countToShowLoadButton - 3 &&
                        currentPageNumber < totalPageNumber) {

                    loadMoreButton.setVisibility(View.VISIBLE);
                    loadMoreButton.setEnabled(true);
                }
*/
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


    private void extractChannels(Integer skip) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET,
                        JSON_URL + skip.toString(),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray channelArray = response.getJSONArray("value");
                                    System.out.println("channelArray.length:" + channelArray.length());
                                    for (int i = 0; i < channelArray.length(); i++) {
                                        JSONObject channelObject = channelArray.getJSONObject(i);
                                        String channelTitle = channelObject.getString("Title");
                                        //System.out.println(channelTitle);

                                        Channel channel = new Channel();
                                        channel.setTitle(channelTitle);

                                        CurrentProgram nowAndNext = getNowAndNext(channelTitle);
                                        //System.out.println(nowAndNext.toString());
                                        channel.setCurrentProgram(nowAndNext);

                                        channels.add(channel);
//                                        adapter.updateDataSet(channels);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                    System.out.println("Linha 48");
                                }
                                setRecyclerView();
                                adapter.updateDataSet(channels);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tag", "onErrorResponse: " + error.getMessage());
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println("Linha 78");
                    }
                });
        queue.add(jsonObjectRequest);
    }


    private CurrentProgram getNowAndNext(String channelTitle) {
        CurrentProgram currentProgram = new CurrentProgram();
        final String JSON_URL_CURRENT =
                "http://ott.online.meo.pt/Program/v9/Programs/NowAndNextLiveChannelPrograms?UserAgent=AND&$filter=CallLetter eq %27" + channelTitle + "%27&$orderby=StartDate asc";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, JSON_URL_CURRENT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray programArray = response.getJSONArray("value");
                    //System.out.println(programArray);

                    if (programArray.length() > 0) {
                        JSONObject programObject = programArray.getJSONObject(0);
                        //System.out.println(programObject);
                        String programTitle = programObject.getString("Title");
                        //System.out.println(programTitle);

                        JSONObject nextProgramObject = programArray.getJSONObject(1);
                        String nextProgramTitle = nextProgramObject.getString("Title");

                        currentProgram.setProgramTitle(programTitle);
                        currentProgram.setNextProgramTitle(nextProgramTitle);
                        currentProgram.
                                setCoverImageURL(
                                        "http://cdn-images.online.meo.pt/eemstb/ImageHandler.ashx?evTitle=" + programTitle + "&chCallLetter=" + channelTitle + "&profile=16_9&width=320");
                        //System.out.println(currentProgram);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    System.out.println("Linha 102");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("Linha 111");
            }
        });
        queue.add(jsonObjectRequest);
        return currentProgram;
    }


    @Override
    public void loadNextPage() {

        skip += 10;
        extractChannels(skip);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}