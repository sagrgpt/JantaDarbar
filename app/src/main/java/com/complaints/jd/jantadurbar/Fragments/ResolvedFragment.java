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
import com.complaints.jd.jantadurbar.Adapters.ResolvedAdapter;
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

public class ResolvedFragment extends Fragment {

    private RecyclerView recyclerView;
    private ResolvedAdapter mAdapter;
    final List<DataStorageClass> dataArray = new ArrayList<>();

    public ResolvedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resolved, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerViewContainer2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

    }

    private void getData() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Resolved");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void fetchData(DataSnapshot dataSnapshot){
        Log.d("Datalog", "onChildAdded:" + dataSnapshot.getKey());
        DataStorageClass info = new DataStorageClass();
        Map<String, String> map = (Map) dataSnapshot.getValue();
        info.title = map.get("title");
        info.description = map.get("description");
        info.wardNo = map.get("wardNo");
        info.city = map.get("city");
        info.landmark = map.get("landmark");
        dataArray.add(info);
        mAdapter = new ResolvedAdapter(dataArray);
        recyclerView.setAdapter(mAdapter);
    }
}