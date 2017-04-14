/*
 * Copyright (C) 2016 Jd, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.complaints.jd.jantadurbar.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.complaints.jd.jantadurbar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Collections;
import java.util.List;

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.MyViewHolderClass>{

        List<DataStorageClass> dataArray = Collections.emptyList();


public static class MyViewHolderClass extends RecyclerView.ViewHolder{

    private static TextView title;
    private static TextView description;
    private static TextView landmark;
    private static TextView city;
    private static TextView ward;
    private static TextView uniqueId;
    private Button success;

    public MyViewHolderClass(View view) {
        super(view);
        uniqueId = (TextView) view.findViewById(R.id.uniqueId);
        title = (TextView) view.findViewById(R.id.complainTitle);
        description = (TextView) view.findViewById(R.id.description);
        landmark = (TextView) view.findViewById(R.id.landmark);
        city = (TextView) view.findViewById(R.id.city);
        ward = (TextView) view.findViewById(R.id.ward_no);
        success = (Button) view.findViewById(R.id.successBtn);


    }

}

    public AdminRecyclerAdapter(List<DataStorageClass> dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public AdminRecyclerAdapter.MyViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_feed_card,parent,false);
        AdminRecyclerAdapter.MyViewHolderClass holder = new AdminRecyclerAdapter.MyViewHolderClass(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdminRecyclerAdapter.MyViewHolderClass holder, int position) {
        final DataStorageClass currentData = dataArray.get(position);
        holder.title.setText(currentData.title);
        holder.description.setText(currentData.description);
        holder.landmark.setText(currentData.landmark);
        holder.city.setText(currentData.city);
        holder.ward.setText("Ward No. : "+currentData.wardNo);
        holder.uniqueId.setText(currentData.id);
        holder.success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(),"Button Click Working!!", Toast.LENGTH_SHORT).show();
                removeItem(currentData, holder);
                moveData(holder);
            }
        });

//        holder.title.setText("Title");
//
//        holder.description.setText("description");
//        holder.landmark.setText("Landmark");
//        holder.city.setText("city");
//        holder.ward.setText("Ward No. : 20");
    }

    private void removeItem(DataStorageClass data, AdminRecyclerAdapter.MyViewHolderClass holder) {
        int position = dataArray.indexOf(data);
        dataArray.remove(position);
        notifyItemRemoved(position);
        moveData(holder);
    }

    public static void moveData(AdminRecyclerAdapter.MyViewHolderClass holder){
        DatabaseReference m1Database = FirebaseDatabase.getInstance().getReference("NewsFeed");
        DatabaseReference m2Database = FirebaseDatabase.getInstance().getReference("Resolved");
        DataStorageClass dataStorageClass = new DataStorageClass(holder.title.getText().toString(),
                holder.description.getText().toString(),
                holder.landmark.getText().toString(),
                holder.ward.getText().toString(),
                holder.city.getText().toString());
        m2Database.child(holder.uniqueId.getText().toString()).setValue(dataStorageClass);
        m1Database.child(holder.uniqueId.getText().toString()).removeValue();
    }


    @Override
    public int getItemCount() {
        return dataArray.size();
    }
}