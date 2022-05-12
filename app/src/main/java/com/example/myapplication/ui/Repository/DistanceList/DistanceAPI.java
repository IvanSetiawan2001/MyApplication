package com.example.myapplication.ui.Repository.DistanceList;

import com.example.myapplication.ui.Model.Distance.DistanceResult;
import com.example.myapplication.ui.Model.PlaceDetail.LocationDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DistanceAPI {

    @GET
    Call<DistanceResult> getDistance(@Url String url);

}
