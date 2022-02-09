package com.example.admin.inetum;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceholder {

    @GET("Title")
    Call<List<Channel>> getChannel();

    //
    //
    //
}
