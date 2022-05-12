package com.example.myapplication.ui.Model.User;

import com.example.myapplication.ui.Model.PlaceDetail.Location;
import com.example.myapplication.ui.Model.PlaceDetail.OpeningHours;

public class LocationListModel {

    String PlaceId;
    String PhotoReff;
    Location location;
    String Name;
    String Address;
    OpeningHours openingHours;
    Double Rating;

//    public LocationModel(String placeId, String photoReff, Location location, String name, String address, OpeningHours openingHours, Double rating) {
//        PlaceId = placeId;
//        PhotoReff = photoReff;
//        this.location = location;
//        Name = name;
//        Address = address;
//        this.openingHours = openingHours;
//        Rating = rating;
//    }

    public String getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(String placeId) {
        PlaceId = placeId;
    }

    public String getPhotoReff() {
        return PhotoReff;
    }

    public void setPhotoReff(String photoReff) {
        PhotoReff = photoReff;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }
}
