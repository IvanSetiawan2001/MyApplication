package com.example.myapplication.ui.Repository.LocationDetail;

import com.example.myapplication.ui.Model.PlaceDetail.GooglePlaceDetailModel;
import com.example.myapplication.ui.Model.PlaceDetail.Location;
import com.example.myapplication.ui.Model.PlaceDetail.LocationDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface LocDetailAPI {

    @GET
    Call<LocationDetails> getLocationDetail(@Url String url);

}
