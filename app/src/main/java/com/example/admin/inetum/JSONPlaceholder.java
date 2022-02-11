package com.example.admin.inetum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceholder {

//    @GET("Title")
//    Call<List<Channel>> getChannel();

    @GET("Channels?")
    Call<ArrayList<Channel>>
    getChannel(@Query("UserAgent")
                       String userAgent);

    @GET("NowAndNextLiveChannelPrograms?")
    Call<ArrayList<CurrentProgram>>
    getNowAndNextRetrofit(@Query("UserAgent")
                                  String userAgent);

    //
    //
    //
}
