package com.example.myapplication.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Activity.MainActivity;
import com.example.myapplication.ui.Adapter.LocationAdapter;
import com.example.myapplication.ui.Model.LocationList.GooglePlaceModel;
import com.example.myapplication.ui.Model.LocationList.Location;
import com.example.myapplication.ui.Repository.LocationList.RetrofitAPI;
import com.example.myapplication.ui.Repository.LocationList.RetrofitClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag2 extends Fragment {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private RetrofitAPI retrofitAPI;
    private List<GooglePlaceModel> googlePlaceModelList;
    private String Username;


    public static Frag2 newInstance(String param1, String param2) {
        Frag2 fragment = new Frag2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag2_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();
        Username = mainActivity.getUsername();
        Log.d(TAG, "onViewCreated: "+Username);

        googlePlaceModelList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.Location);
        retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);

        getPlaces();


    }

    private void getPlaces(){
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=Jakarta&type=tourist_attraction&key="+ getResources().getString(R.string.API_KEY);

        //Pengambilan data lokasi tempat wisata dalam radius 50000 meter

        retrofitAPI.getLocation(url).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(@NonNull Call<Location> call, @NonNull Response<Location> response) {

                if (response.errorBody() == null){

                    if(response.body() != null){
                        Log.d(TAG, "onResponse: "+response.message());
                        if(response.body().getGooglePlaceModelsList() != null && response.body().getGooglePlaceModelsList().size() > 0){
                           googlePlaceModelList.clear();
                           for (int i = 0; i < response.body().getGooglePlaceModelsList().size(); i++){
                               googlePlaceModelList.add(response.body().getGooglePlaceModelsList().get(i));
                           }
                           SetRecylerView();
                        }
                        else{
                            Log.d(TAG, "onResponse: "+response.code());
                        }

                    }

                }
                else{
                    Log.d("TAG", "onResponse: "+ response.errorBody());
                    Toast.makeText(requireContext(), "Error : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
                
                
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Log.d("TAG", "onFailure: "+ t);

            }
        });
    }


    //Set recycler view
    private void SetRecylerView(){
        LocationAdapter locAdapter = new LocationAdapter(getContext(),googlePlaceModelList,Username);
        recyclerView.setAdapter(locAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}