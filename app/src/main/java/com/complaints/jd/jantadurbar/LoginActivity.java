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


/**This application uses firebase database itself to get login authentication for the users
 * and not the firebase authentication api. For more understanding on the database and login
 * connection of the application, please view the database itself from the firebase console.*/
package com.complaints.jd.jantadurbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    final private String TAG="user Status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Admin Panel");

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin(username.getText().toString(), password.getText().toString());
            }
        });
    }

    public void startLogin(final String email, final String password){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Login");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pass = dataSnapshot.child(email).child("password").getValue(String.class);
                if(pass == null){
                    Snackbar.make(getCurrentFocus(),"Invalid Username",Snackbar.LENGTH_SHORT)
                            .setAction("Action",null).show();
                }
                else if(!pass.equals(password)){
                        Snackbar.make(getCurrentFocus(),"Invalid Password",Snackbar.LENGTH_SHORT)
                                .setAction("Action",null).show();
                    }
                else{
                    SharedPreferences sharedPreferences = getSharedPreferences("Settings",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("IsAdmin",true);
                    editor.putString("Username",email);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(getCurrentFocus(),"Internet Connection slow!!",Snackbar.LENGTH_SHORT)
                        .setAction("Action",null).show();
            }
        });

    }

}