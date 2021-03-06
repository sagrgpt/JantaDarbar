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

package com.complaints.jd.jantadurbar;

import com.complaints.jd.jantadurbar.Adapters.DataStorageClass;
import com.complaints.jd.jantadurbar.Adapters.ViewPagerAdapter;
import com.complaints.jd.jantadurbar.Fragments.NewsFeedFragment;
import com.complaints.jd.jantadurbar.Fragments.ResolvedFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Variable used to implement exit function
    boolean doubleBackToExitPressedOnce = false;

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
                dialog.setTitle(R.string.register_complaint_title);
                title = (EditText)dialog.findViewById(R.id.createTitle);
                description = (EditText)dialog.findViewById(R.id.createDescription);
                landmark = (EditText)dialog.findViewById(R.id.makeLandmark);
                wardNo = (EditText)dialog.findViewById(R.id.makeWardNo);
                city= (EditText)dialog.findViewById(R.id.makeCity);
                Button registerComplaint = (Button) dialog.findViewById(R.id.onSave);
                registerComplaint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!title.getText().toString().equals("") |
                                !description.getText().toString().equals("") |
                                !landmark.getText().toString().equals("") |
                                !wardNo.getText().toString().equals("") |
                                !city.getText().toString().equals("") ) {
                            Boolean flag = saveData();
                            if (flag) {
                                dialog.dismiss();
                            }
                        }
                        else{

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
        viewPagerAdapter.addFragment(new NewsFeedFragment(),getString(R.string.news_feed_title));
        viewPagerAdapter.addFragment(new ResolvedFragment(),getString(R.string.resolved_feed_title));
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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit_toast, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
