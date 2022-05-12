package com.example.myapplication.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.Model.Distance.DistanceResult;

import org.w3c.dom.Text;

import java.util.List;

public class DistanceListAdapter extends RecyclerView.Adapter<DistanceListAdapter.ViewHolder>{

    List<DistanceResult> distanceResultList;
    Context context;

    public DistanceListAdapter(Context ctx, List<DistanceResult> distanceResults){
        context = ctx;
        distanceResultList = distanceResults;
    }


    @NonNull
    @Override
    public DistanceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.distance_layout,parent,false);
        return new DistanceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceListAdapter.ViewHolder holder, int position) {

        holder.Name1.setText(distanceResultList.get(position).getOriginAddresses().toString());
        holder.Name2.setText(distanceResultList.get(position).getDestinationAddresses().toString());
        holder.Distance.setText(distanceResultList.get(position).getRows().get(0).getElements().get(0).getDistance().getText());
    }

    @Override
    public int getItemCount() {
        return distanceResultList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name1;
        TextView Name2;
        TextView Distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name1 = itemView.findViewById(R.id.Place1);
            Name2 = itemView.findViewById(R.id.Place2);
            Distance = itemView.findViewById(R.id.distance);

        }


    }
}
