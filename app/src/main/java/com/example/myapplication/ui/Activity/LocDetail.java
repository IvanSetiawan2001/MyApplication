package com.example.myapplication.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.Adapter.LocationListAdapter;
import com.example.myapplication.ui.Fragment.Frag1;
import com.example.myapplication.ui.Model.PlaceDetail.GooglePlaceDetailModel;
import com.example.myapplication.ui.Model.PlaceDetail.Location;
import com.example.myapplication.ui.Model.PlaceDetail.LocationDetails;
import com.example.myapplication.ui.Model.PlaceDetail.OpeningHours;
import com.example.myapplication.ui.Model.User.LocationListModel;
import com.example.myapplication.ui.Repository.LocationDetail.LocDetailAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LocDetail extends AppCompatActivity implements OnMapReadyCallback {


    private List<GooglePlaceDetailModel> googlePlaceModelList;
    private ImageView LocPhoto;
    private TextView LocName, LocAddress, LocReview, LocOPHour;
    private AppCompatButton SaveButton;
    String TAG = "TAG";
    private Context context;
    Retrofit retrofit;
    String PlaceId;
    private LocDetailAPI locDetailAPI;
    private String Url;
    private String imgBaseUrl;
    String Username;
    Double Rating;
    Frag1 frag1;
    GoogleMap map;
    Location location = new Location();
    Double Lat;
    Double Long;

    LocationDetails locDetail;
    LocationListModel locModel;
    List<LocationListModel> locationListModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_detail);

        context = getBaseContext();
        LocPhoto = findViewById(R.id.LocPhoto);
        LocOPHour = findViewById(R.id.LocOpHour);
        LocName = findViewById(R.id.LocName);
        LocAddress = findViewById(R.id.LocAddress);
        SaveButton = findViewById(R.id.SaveButton);
        locationListModelList = new ArrayList<>();
        frag1 = new Frag1();




        imgBaseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=";

//        LocReview.setEnabled(false);

        getData();
        Log.d(TAG, "onCreate: "+ PlaceId);
        getDetail();



        DatabaseReference databaseReference = FirebaseDatabase.getInstance(getResources().getString(R.string.LOCATION_DATABASE_URL)).getReference();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("LocationList").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() <= 0){
                            setLocModel();
                            Log.d(TAG, "zero data: sampai sini masuk");
                            databaseReference.child("LocationList").child(Username+"001").setValue(locationListModelList);
                            finish();
                        }
                        else if (snapshot.getChildrenCount()>0){

                           databaseReference.child("LocationList").child(Username+"001").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {

                                   for (DataSnapshot dsp:snapshot.getChildren()) {
                                       locationListModelList.add(dsp.getValue(LocationListModel.class));
                                   }

                                   setLocModel();
                                   databaseReference.child("LocationList").child(Username+"001").setValue(locationListModelList);

                                   finish();
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {
                                   Log.d(TAG, "onCancelled: "+error.getMessage());
                               }
                           });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: "+error.getMessage());
                    }
                });

            }
        });

    }




    //Get Place ID and username
    private void getData(){
        if(getIntent().hasExtra("PlaceId")){
            PlaceId = getIntent().getStringExtra("PlaceId");
            Username = getIntent().getStringExtra("Username");
        }
        else{
            Toast.makeText(this, "Place not found", Toast.LENGTH_SHORT).show();
        }
    }

    //Get Place Detail
    private void getDetail(){
      Url = "https://maps.googleapis.com/maps/api/place/details/json?place_id="+PlaceId+"&key="+getResources().getString(R.string.API_KEY);

      retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/")
              .addConverterFactory(GsonConverterFactory.create())
              .addConverterFactory(ScalarsConverterFactory.create())
                      .build();

      LocDetailAPI locDetailAPI = retrofit.create(LocDetailAPI.class);

     Call<LocationDetails> call = locDetailAPI.getLocationDetail(Url);

     call.enqueue(new Callback<LocationDetails>() {
         @Override
         public void onResponse(Call<LocationDetails> call, Response<LocationDetails> response) {
             if (!response.isSuccessful()){
                 Log.d(TAG, "Code: "+response.code());
                 return;
             }

             locDetail = response.body();

             //set photo
             Glide.with(context).load(imgBaseUrl
                     + response.body().getGooglePlaceDetailModelList().getPhotos().get(0).getPhotoReference()
                     + "&key="+getResources().getString(R.string.API_KEY)).into(LocPhoto);

             LocName.setText(locDetail.getGooglePlaceDetailModelList().getName());
             LocAddress.setText(locDetail.getGooglePlaceDetailModelList().getFormattedAddress());
             location.setLat(locDetail.getGooglePlaceDetailModelList().getGeometry().getLocation().getLat());
             location.setLng(locDetail.getGooglePlaceDetailModelList().getGeometry().getLocation().getLng());
             Lat = locDetail.getGooglePlaceDetailModelList().getGeometry().getLocation().getLat();
             Long = locDetail.getGooglePlaceDetailModelList().getGeometry().getLocation().getLng();


             //set map fragment
             SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                     .findFragmentById(R.id.map);

             mapFragment.getMapAsync(LocDetail.this);
             //sampai sini set map fragment nya


             if(locDetail.getGooglePlaceDetailModelList().getOpeningHours() != null){

                 String hour = response.body().getGooglePlaceDetailModelList().getOpeningHours().getPeriods().get(0).getOpen().getTime();
                 hour = AddChar(hour,':',2);
                 LocOPHour.setText("Open At: "+hour);
             }
             else {
                 LocOPHour.setText("Open At: No Data");
             }

             if(response.body().getGooglePlaceDetailModelList().getReviews()!=null){
//                 LocReview.setText(response.body().getGooglePlaceDetailModelList().getReviews().get(0).getText());
             }
             else
             {
                 LocReview.setText("No Review");
             }
             }


         @Override
         public void onFailure(Call<LocationDetails> call, Throwable t) {
             Log.d(TAG, "onFailure: "+t.getMessage());
         }
     });





    }

    //add a char in a string
    private String AddChar(String Hour,char c, int pos){
        StringBuffer stringBuffer = new StringBuffer(Hour);
        stringBuffer.insert(pos,c);
        return stringBuffer.toString();
    }

    private void setLocModel(){

        Log.d(TAG, "setLocModel: "+locDetail.getGooglePlaceDetailModelList().getName());

        String PhotoReff = locDetail.getGooglePlaceDetailModelList().getPhotos().get(0).getPhotoReference();
        Location location = locDetail.getGooglePlaceDetailModelList().getGeometry().getLocation();
        String name = locDetail.getGooglePlaceDetailModelList().getName();
        String Address = locDetail.getGooglePlaceDetailModelList().getFormattedAddress();
        OpeningHours openingHours = locDetail.getGooglePlaceDetailModelList().getOpeningHours();
        if (locDetail.getGooglePlaceDetailModelList().getRating() != null){
            Rating = locDetail.getGooglePlaceDetailModelList().getRating();
        }
        else{
            Rating = 0.0;
        }

        locModel = new LocationListModel();
        locModel.setLocation(location);
        locModel.setAddress(Address);
        locModel.setName(name);
        locModel.setPlaceId(PlaceId);
        locModel.setOpeningHours(openingHours);
        locModel.setPhotoReff(PhotoReff);

        locationListModelList.add(locModel);
    }


    //apply map nya pakai function yang ini
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;
        LatLng position = new LatLng(Lat,Long);
        Log.d(TAG, "Lat: "+Lat);
        map.addMarker(new MarkerOptions().position(position));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,16.0f));

    }
}



