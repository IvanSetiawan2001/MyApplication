package com.example.myapplication.ui.Model.User;



import java.util.List;

public class UserModel {

    String Username;
    String Password;
    String Email;
    List<LocationListModel> locationListModels;
    public UserModel(){};


    public UserModel(String username, String password, String email) {
        Username = username;
        Password = password;
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public List<LocationListModel> getLocationModels() {
        return locationListModels;
    }

    public void setLocationModels(List<LocationListModel> locationListModels) {
        this.locationListModels = locationListModels;
    }
}
