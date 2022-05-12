package com.example.myapplication.ui.Model.Distance;

import com.example.myapplication.ui.Model.PlaceDetail.OpeningHours;

public class DistanceSumary {
    String Name;
    String DestinationAddresses;
    String OriginalAddresses;
    String DistanceText;
    int DistanceValue;
    String DurationText;
    int DurationValue;
    OpeningHours openingHours;

    public DistanceSumary(String name, String destinationAddresses, String originalAddresses, String distanceText, int distanceValue, String durationText, int durationValue, OpeningHours openingHours) {
        Name = name;
        DestinationAddresses = destinationAddresses;
        OriginalAddresses = originalAddresses;
        DistanceText = distanceText;
        DistanceValue = distanceValue;
        DurationText = durationText;
        DurationValue = durationValue;
        this.openingHours = openingHours;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDestinationAddresses() {
        return DestinationAddresses;
    }

    public void setDestinationAddresses(String destinationAddresses) {
        DestinationAddresses = destinationAddresses;
    }

    public String getOriginalAddresses() {
        return OriginalAddresses;
    }

    public void setOriginalAddresses(String originalAddresses) {
        OriginalAddresses = originalAddresses;
    }

    public String getDistanceText() {
        return DistanceText;
    }

    public void setDistanceText(String distanceText) {
        DistanceText = distanceText;
    }

    public int getDistanceValue() {
        return DistanceValue;
    }

    public void setDistanceValue(int distanceValue) {
        DistanceValue = distanceValue;
    }

    public String getDurationText() {
        return DurationText;
    }

    public void setDurationText(String durationText) {
        DurationText = durationText;
    }

    public int getDurationValue() {
        return DurationValue;
    }

    public void setDurationValue(int durationValue) {
        DurationValue = durationValue;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }
}
