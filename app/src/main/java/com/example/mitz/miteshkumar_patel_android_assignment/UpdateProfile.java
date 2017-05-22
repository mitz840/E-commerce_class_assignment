package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UpdateProfile extends AppCompatActivity {
    EditText name,email,password;
    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        name = (EditText)findViewById(R.id.UPnameEditText);
        email = (EditText)findViewById(R.id.UPemail);
        password = (EditText)findViewById(R.id.UPpass);
        dbHelper = new DbHelper(getApplicationContext());


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.update);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = password.getText().toString();
                dbHelper.updateProfile(i.getIntExtra("USERID",1),name1,email1,pass1);
                finish();





            }
        });
    }
}
