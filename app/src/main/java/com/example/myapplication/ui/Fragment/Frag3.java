package com.example.myapplication.ui.Fragment;

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
import android.webkit.WebStorage;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Activity.MainActivity;
import com.example.myapplication.ui.Adapter.DistanceListAdapter;
import com.example.myapplication.ui.Adapter.LocationListAdapter;
import com.example.myapplication.ui.Model.Distance.DistanceResult;
import com.example.myapplication.ui.Model.Distance.DistanceSumary;
import com.example.myapplication.ui.Model.PlaceDetail.LocationDetails;
import com.example.myapplication.ui.Model.PlaceDetail.OpeningHours;
import com.example.myapplication.ui.Model.User.LocationListModel;
import com.example.myapplication.ui.Repository.DistanceList.DistanceAPI;
import com.example.myapplication.ui.Repository.LocationDetail.LocDetailAPI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag3 extends Fragment {


    RecyclerView distanceList;
    public List<LocationListModel> locationList;
    String Username;
    public LocationListAdapter locationAdapter;
    public DatabaseReference databaseReference;
    Retrofit retrofit;
    List<DistanceResult> distanceResults;
    DistanceListAdapter distanceListAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public static Frag3 newInstance(String param1, String param2) {
        Frag3 fragment = new Frag3();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag3_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();
        Username = mainActivity.getUsername();

        distanceList = view.findViewById(R.id.DistaceList);


        locationList = new ArrayList<>();
        distanceResults = new ArrayList<>();

        getLocationList();


    }

    public void getLocationList(){
        databaseReference = FirebaseDatabase.getInstance("https://skripsi-96465.firebaseio.com/").getReference();
        databaseReference.child("LocationList").child(Username+"001").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp:snapshot.getChildren()) {
                    locationList.add(dsp.getValue(LocationListModel.class));
                }

                if (locationList.isEmpty()){
                    Log.d("TAG", "onDataChange: Failed to get Data");
                    return;
                }

                getAllDistacne(locationList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: "+error.getMessage());
            }

        });
    }


    public void getAllDistacne(List<LocationListModel> locationList){

        int id = 0;
        retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        distanceListAdapter = new DistanceListAdapter(getContext(),distanceResults);
        distanceList.setAdapter(distanceListAdapter);
        distanceList.setLayoutManager(new LinearLayoutManager(getContext()));

        for (int a = 0;a<locationList.size();a++){

            for (int b = 0; b<locationList.size();b++){

                if (b==a){
                    continue;
                }

                if (a == 0){
                    id = 1;
                }
                else{
                    id = 0;
                }

                Double lat1 = locationList.get(a).getLocation().getLat();
                Double lat2 = locationList.get(b).getLocation().getLat();
                Double lng1 = locationList.get(a).getLocation().getLng();
                Double lng2 = locationList.get(b).getLocation().getLng();
                String Name1 = locationList.get(a).getName().toString();
                String Name2 = locationList.get(b).getName().toString();
                OpeningHours openingHours = locationList.get(b).getOpeningHours();
                distanceResults = getDistance(lat1,lat2,lng1,lng2,Name1,Name2,openingHours, id);
                ;
            }
        }
    }

    public List<DistanceResult> getDistance(Double lat1, Double lat2, Double lng1, Double lng2, String Name1, String Name2, OpeningHours openingHours, int id){

        String Url = "https://maps.googleapis.com/maps/api/distancematrix/json?destinations="+lat1+","+lng1+
                "&origins="+lat2+","+lng2+"&key="+getResources().getString(R.string.API_KEY);


        DistanceAPI distanceAPI = retrofit.create(DistanceAPI.class);
        Call<DistanceResult> call = distanceAPI.getDistance(Url);
        call.enqueue(new Callback<DistanceResult>() {
            @Override
            public void onResponse(Call<DistanceResult> call, Response<DistanceResult> response) {
                if (!response.isSuccessful()){
                    Log.d("TAG", "Code: "+response.code());
                    return;
                }
                distanceResults.add(response.body());

                String origin = response.body().getOriginAddresses().get(0).toString();
                String Destination = response.body().getDestinationAddresses().get(0).toString();
                String DistanceText = response.body().getRows().get(0).getElements().get(0).getDistance().getText();
                int DistanceValue = response.body().getRows().get(0).getElements().get(0).getDistance().getValue();
                String DurationText = response.body().getRows().get(0).getElements().get(0).getDuration().getText();
                int DurationValue = response.body().getRows().get(0).getElements().get(0).getDuration().getValue();

                distanceListAdapter.notifyDataSetChanged();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://skripsi-96466.firebaseio.com/").getReference();

                DistanceSumary distanceSumary = new DistanceSumary(Name2,Destination, origin, DistanceText,DistanceValue, DurationText, DurationValue,openingHours);

                if (id == 1){
                    Log.d("TAG", "Data baru = Success");
                    databaseReference.child(Username).child("StartPlace").child(Name1).child(Name2).setValue(distanceSumary);
                }
                else {
                    Log.d("TAG", "Data lama");
                    databaseReference.child(Username).child("VisitLocation").child(Name1).child(Name2).setValue(distanceSumary);
                }

            }

            @Override
            public void onFailure(Call<DistanceResult> call, Throwable t) {

            }
        });
        return distanceResults;
    }


}