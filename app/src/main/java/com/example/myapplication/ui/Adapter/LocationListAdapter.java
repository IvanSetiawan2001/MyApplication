package com.example.myapplication.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.Activity.LocDetail;
import com.example.myapplication.ui.Model.LocationList.GooglePlaceModel;
import com.example.myapplication.ui.Model.LocationList.LocationModel;
import com.example.myapplication.ui.Model.User.LocationListModel;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    List<LocationListModel> loc;
    Context context;
    String imgBaseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxheight=150&maxwidth=150";
    String APIKey;
    String username;

    @NonNull
    @Override
    public LocationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.location_layout,parent,false);
        return new LocationListAdapter.ViewHolder(view);
    }

    public LocationListAdapter(Context ctx, List<LocationListModel> locations, String Username){
        context = ctx;
        loc=locations;
        APIKey = context.getResources().getString(R.string.API_KEY);
        username = Username;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.ViewHolder holder, int position) {
        holder.Name.setText(loc.get(position).getName());
        holder.Address.setText(loc.get(position).getAddress());

        try {
            Glide.with(context)
                    .load(imgBaseUrl + "&photo_reference=" + loc.get(position).getPhotoReff() +
                            " &key=" + APIKey)
                    .into(holder.Image);
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return loc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name;
        TextView Address;
        ImageView Image;
        ConstraintLayout LocList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.Name);
            Address = itemView.findViewById(R.id.Address);
            Image = itemView.findViewById(R.id.imageView);
            LocList = itemView.findViewById(R.id.LocationList);

        }
    }
}
