package com.complaints.jd.jantadurbar;

import com.complaints.jd.jantadurbar.Adapters.DataStorageClass;
import com.complaints.jd.jantadurbar.Adapters.ViewPagerAdapter;
import com.complaints.jd.jantadurbar.Fragments.NewsFeedFragment;
import com.complaints.jd.jantadurbar.Fragments.ResolvedFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    EditText title,description,landmark,wardNo,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Setting the tab bar for the tab layout **/
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPage(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.raise_issue);
                title = (EditText)dialog.findViewById(R.id.createTitle);
                description = (EditText)dialog.findViewById(R.id.createDescription);
                landmark = (EditText)dialog.findViewById(R.id.makeLandmark);
                wardNo = (EditText)dialog.findViewById(R.id.makeWardNo);
                city= (EditText)dialog.findViewById(R.id.makeCity);
                Button registerComplaint = (Button) dialog.findViewById(R.id.onSave);
                registerComplaint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean flag = saveData();
                        if(flag){
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });



    }

    public Boolean saveData(){
        long time = System.currentTimeMillis();
        DataStorageClass dataStorageClass = new DataStorageClass(title.getText().toString(),
                description.getText().toString(),
                landmark.getText().toString(),
                wardNo.getText().toString(),
                city.getText().toString());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("NewsFeed").child(String.valueOf(time));
        mDatabase.setValue(dataStorageClass);
        return true;
    }

    public void setUpViewPage(ViewPager mViewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new NewsFeedFragment(),"News Feed");
        viewPagerAdapter.addFragment(new ResolvedFragment(),"Resolved");
        mViewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        return true;
    }
}
