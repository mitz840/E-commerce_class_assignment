package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


public class MyProfile extends Fragment {
    TextView usernameTextView,emailTextView,passTextView;
    ImageView profilePic;
    Cursor singleUserDataCursor;
    DbHelper dbHelper;
    int userID;




    public static MyProfile newInstance() {
        return new MyProfile();
    }

    public MyProfile() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        usernameTextView = (TextView)view.findViewById(R.id.userNameTXT);
        emailTextView = (TextView)view.findViewById(R.id.emilTXT);
        passTextView = (TextView)view.findViewById(R.id.passTXT);
        profilePic = (ImageView)view.findViewById(R.id.myprofileImageView);

        dbHelper = new DbHelper(view.getContext());


        Intent intent = getActivity().getIntent();
        //String email = intent.getStringExtra("USER");
        userID = intent.getIntExtra("USERID",0);
        singleUserDataCursor =  dbHelper.getSingleUser(userID);
         String email = singleUserDataCursor.getString(singleUserDataCursor.getColumnIndex("email"));
        String userName = singleUserDataCursor.getString(singleUserDataCursor.getColumnIndex("username"));
        int profilePicture = singleUserDataCursor.getInt(singleUserDataCursor.getColumnIndex("image"));
        String password = singleUserDataCursor.getString(singleUserDataCursor.getColumnIndex("password"));



        usernameTextView.setText("Name : "+userName);
        emailTextView.setText("Email : "+email);
        passTextView.setText("Password  : "+password);
        profilePic.setImageResource(profilePicture);






        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.goToUpdate);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UpdateProfile.class);
                intent.putExtra("USERID",userID);
                startActivity(intent);

            }
        });





        return view;
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        Intent intent = getActivity().getIntent();
        singleUserDataCursor =  dbHelper.getSingleUser(userID);
        String email = singleUserDataCursor.getString(singleUserDataCursor.getColumnIndex("email"));
        String userName = singleUserDataCursor.getString(singleUserDataCursor.getColumnIndex("username"));
        int profilePicture = singleUserDataCursor.getInt(singleUserDataCursor.getColumnIndex("image"));
        String password = singleUserDataCursor.getString(singleUserDataCursor.getColumnIndex("password"));



        usernameTextView.setText("Name : "+userName);
        emailTextView.setText("Email : "+email);
        passTextView.setText("Password  : "+password);
        profilePic.setImageResource(profilePicture);
        //Refresh your stuff here

    }
}
