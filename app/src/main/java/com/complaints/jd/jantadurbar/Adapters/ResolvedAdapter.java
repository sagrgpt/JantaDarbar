package com.complaints.jd.jantadurbar.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.complaints.jd.jantadurbar.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by sagar on 10/4/17.
 */

public class ResolvedAdapter extends RecyclerView.Adapter<ResolvedAdapter.MyViewHolderClass>{

    List<DataStorageClass> dataArray = Collections.emptyList();


    public static class MyViewHolderClass extends RecyclerView.ViewHolder{

        private TextView title, description, landmark, city, ward;

        public MyViewHolderClass(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.complainTitle);
            description = (TextView) view.findViewById(R.id.description);
            landmark = (TextView) view.findViewById(R.id.landmark);
            city = (TextView) view.findViewById(R.id.city);
            ward = (TextView) view.findViewById(R.id.ward_no);
        }
    }

    public ResolvedAdapter(List<DataStorageClass> dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public ResolvedAdapter.MyViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resolved_card,parent,false);
        ResolvedAdapter.MyViewHolderClass holder = new ResolvedAdapter.MyViewHolderClass(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolderClass holder, int position) {
        DataStorageClass currentData = dataArray.get(position);
        holder.title.setText(currentData.title);
        holder.description.setText(currentData.description);
        holder.landmark.setText(currentData.landmark);
        holder.city.setText(currentData.city);
        holder.ward.setText("Ward No. : "+currentData.wardNo);
//        holder.title.setText("Title");
//
//        holder.description.setText("description");
//        holder.landmark.setText("Landmark");
//        holder.city.setText("city");
//        holder.ward.setText("Ward No. : 20");
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

}
