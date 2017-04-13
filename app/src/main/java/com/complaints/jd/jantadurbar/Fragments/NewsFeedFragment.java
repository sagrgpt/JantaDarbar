package com.complaints.jd.jantadurbar.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.complaints.jd.jantadurbar.Adapters.DataStorageClass;
import com.complaints.jd.jantadurbar.Adapters.RecyclerAdapter;
import com.complaints.jd.jantadurbar.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsFeedFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private Context mContext;
    final List<DataStorageClass> dataArray = new ArrayList<>();

    public NewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = container.getContext();
        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerViewContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

    }

    private void getData() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("NewsFeed");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                dataArray.clear();
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                dataArray.clear();
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                dataArray.clear();
                fetchData(dataSnapshot);
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

//        for(DataSnapshot ds: dataSnapshot.getChildren()){
            Log.d("Datalog", "onChildAdded:" + dataSnapshot.getKey());
            DataStorageClass info = new DataStorageClass();
            Map<String, String> map = (Map) dataSnapshot.getValue();
            info.title = map.get("title");
            info.description = map.get("description");
            info.wardNo = map.get("wardNo");
            info.city = map.get("city");
            info.landmark = map.get("landmark");
//  DataStorageClass tempInfo =(DataStorageClass) ds.getValue();
            dataArray.add(info);
//        }
        mAdapter = new RecyclerAdapter(dataArray);
        recyclerView.setAdapter(mAdapter);
    }

}