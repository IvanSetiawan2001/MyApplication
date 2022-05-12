package com.example.myapplication.ui.Adapter;

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
import com.example.myapplication.ui.Activity.LocDetail;
import com.example.myapplication.R;
import com.example.myapplication.ui.Model.LocationList.GooglePlaceModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{

    List<GooglePlaceModel>loc;
    Context context;
    String imgBaseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxheight=150&maxwidth=150";
    String APIKey;
    String username;

    public LocationAdapter(Context ctx, List<GooglePlaceModel> locations, String Username){
        context = ctx;
        loc=locations;
        APIKey = context.getResources().getString(R.string.API_KEY);
        username = Username;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.location_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        holder.Name.setText(loc.get(position).getName());
        holder.Address.setText(loc.get(position).getFormattedAddress());

        try {
            Glide.with(context)
                    .load(imgBaseUrl + "&photo_reference=" + loc.get(position).getPhotos().get(0).getPhotoReference() +
                            " &key=" + APIKey)
                    .into(holder.Image);
        }catch (Exception e){

        }

        holder.LocList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LocDetail.class);
                intent.putExtra("PlaceId",loc.get(holder.getAdapterPosition()).getPlaceId());
                intent.putExtra("Username",username);
                context.startActivity(intent);
            }
        });


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
