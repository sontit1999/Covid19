package com.example.covid19.service;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {

        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        return retrofit;
    }
}
