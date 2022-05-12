package com.example.myapplication.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.ui.Activity.MainActivity;
import com.example.myapplication.ui.Adapter.LocationListAdapter;
import com.example.myapplication.ui.Model.User.LocationListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Frag1 extends Fragment {


    RecyclerView route;
    public List<LocationListModel> locationList;
    String Username;
    public LocationListAdapter locationAdapter;
    public DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag1_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        Username = mainActivity.getUsername();
        Log.d("TAG", "onViewCreated Frag 1: "+Username);

        route = view.findViewById(R.id.route);
        locationList = new ArrayList<>();

        getLocationList();

    }



    public void getLocationList(){
        databaseReference = FirebaseDatabase.getInstance("https://skripsi-96465.firebaseio.com/").getReference();
        databaseReference.child("LocationList").child(Username+"001").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationList.clear();
                for (DataSnapshot dsp:snapshot.getChildren()) {
                    locationList.add(dsp.getValue(LocationListModel.class));
                    Log.d("TAG", "onDataChange: ");
                }

                locationAdapter = new LocationListAdapter(getContext(),locationList,Username);

                route.setAdapter(locationAdapter);
                route.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: "+error.getMessage());
            }
        });
    }


}