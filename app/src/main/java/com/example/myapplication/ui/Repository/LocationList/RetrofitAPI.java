package com.example.myapplication.ui.Repository.LocationList;

import com.example.myapplication.ui.Model.LocationList.Location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @GET
    Call<Location> getLocation (@Url String url);
}
