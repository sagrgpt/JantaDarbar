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

import com.complaints.jd.jantadurbar.R;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

/**
 * Created by sagar on 10/4/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolderClass>{

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

    public RecyclerAdapter(List<DataStorageClass> dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public MyViewHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_card,parent,false);
        MyViewHolderClass holder = new MyViewHolderClass(view);
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