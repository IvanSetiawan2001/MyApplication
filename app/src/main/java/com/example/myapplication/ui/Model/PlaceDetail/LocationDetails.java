package com.example.myapplication.ui.Model.PlaceDetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationDetails {

    @SerializedName("result")
    private GooglePlaceDetailModel googlePlaceDetailModelList;

    public GooglePlaceDetailModel getGooglePlaceDetailModelList() {
        return googlePlaceDetailModelList;
    }
}
