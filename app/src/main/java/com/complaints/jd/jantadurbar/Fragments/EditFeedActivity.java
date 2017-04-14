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

package com.complaints.jd.jantadurbar.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.complaints.jd.jantadurbar.Adapters.AdminRecyclerAdapter;
import com.complaints.jd.jantadurbar.Adapters.DataStorageClass;
import com.complaints.jd.jantadurbar.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditFeedActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private Context mContext;
    private AdminRecyclerAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    final List<DataStorageClass> dataArray = new ArrayList<>();
    public EditFeedActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = container.getContext();
        return inflater.inflate(R.layout.fragment_edit_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerViewContainer);
        swipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipeOnEditFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();


    }

    @Override
    public void onRefresh() {
        dataArray.clear();
        getData();
    }

    private void getData() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("NewsFeed");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                fetchData(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchData(DataSnapshot dataSnapshot){
        Log.d("Datalog", "onChildAdded:" + dataSnapshot.getKey());
        DataStorageClass info = new DataStorageClass();
        Map<String, String> map = (Map) dataSnapshot.getValue();
        info.id = dataSnapshot.getKey();
        info.title = map.get("title");
        info.description = map.get("description");
        info.wardNo = map.get("wardNo");
        info.city = map.get("city");
        info.landmark = map.get("landmark");
        dataArray.add(info);
        mAdapter = new AdminRecyclerAdapter(dataArray);
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

}
