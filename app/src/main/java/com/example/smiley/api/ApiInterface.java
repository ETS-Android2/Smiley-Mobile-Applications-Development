package com.example.smiley.api;

import com.example.smiley.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<News> getNews(
            //@Query("country")String country,
            @Query("q") String keyword,
            @Query("apiKey")String apiKey
    );
}
