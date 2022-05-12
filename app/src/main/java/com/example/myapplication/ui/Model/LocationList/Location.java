package com.example.myapplication.ui.Model.LocationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Location {

    @SerializedName("results")
    @Expose
    private List<GooglePlaceModel> googlePlaceModelsList;
    public List<GooglePlaceModel> getGooglePlaceModelsList() {
        return googlePlaceModelsList;
    }

    public void setGooglePlaceModels(ArrayList<GooglePlaceModel> googlePlaceModels) {
        this.googlePlaceModelsList = googlePlaceModels;
    }

}
