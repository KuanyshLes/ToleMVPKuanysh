package com.example.askar.KanatTole.API.Services;

import com.example.askar.KanatTole.API.Interceptors.BasicAuthInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nurgaliyev on 28.03.2018.
 */

public class API_client {

    private static Retrofit retrofit;

    public static Retrofit getClient(){

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new BasicAuthInterceptor("teamspirit@gmail.com","1234"))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://teamorigins.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;

    }
}
