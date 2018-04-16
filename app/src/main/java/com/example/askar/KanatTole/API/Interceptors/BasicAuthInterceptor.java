package com.example.askar.KanatTole.API.Interceptors;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nurgaliyev on 31.03.2018.
 */

public class BasicAuthInterceptor implements Interceptor {

    private String credentials;

    public BasicAuthInterceptor(String user, String password){
        this.credentials = Credentials.basic(user,password);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}
