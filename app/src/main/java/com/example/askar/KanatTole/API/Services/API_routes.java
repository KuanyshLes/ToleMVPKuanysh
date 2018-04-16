package com.example.askar.KanatTole.API.Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface API_routes {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("companies/1/items")
    Call<JsonObject> addItemToCompany(@Body JsonObject itemInfo);

    @GET("companies/{companyId}/items")
    Call<JsonArray> getItemFromCompany(@Path("companyId") String companyId);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("owners/registration")
    Call<JsonObject> ownerRegistration(@Body JsonObject ownerInfo);

    @POST("companies/{companyId}/items/{itemId}")
    Call<JsonObject> editItem(
            @Body JsonObject itemInfo,
            @Path("companyId") String companyId,
            @Path("itemId") String itemId
    );
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @DELETE("companies/{companyId}/items/{itemId}")
    Call<JsonObject> deleteItem(
            @Path("companyId") String companyId,
            @Path("itemId") String itemId
    );



}
