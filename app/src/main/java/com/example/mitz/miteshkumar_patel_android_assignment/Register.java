package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Register extends AppCompatActivity {


    Button registerButton;
    EditText usernameTextView,emailTextView,passwordTextView;
    DbHelper dbHelper;
    Integer[] profileImages = {R.drawable.male1,R.drawable.male,R.drawable.male2,R.drawable.male3};
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DbHelper(this);
        usernameTextView = (EditText)findViewById(R.id.regUserNameTXT);
        emailTextView = (EditText)findViewById(R.id.regEmailTXT);
        passwordTextView = (EditText)findViewById(R.id.regPassTXT);
        registerButton = (Button) findViewById(R.id.signupBTN);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Register.this);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

               int randomImage = rand.nextInt(profileImages.length);
                int profileImage = profileImages[randomImage];
                String email = emailTextView.getText().toString().toLowerCase();
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();


                if(emailTextView.getText().toString().isEmpty() || passwordTextView.getText().toString().isEmpty()||usernameTextView.getText().toString().isEmpty()){
                    alert.setMessage("please provide all details.").show();
                   // Toast.makeText(getApplicationContext(), "please provide all details", Toast.LENGTH_LONG).show();


                }else {

                    dbHelper.insertEntry(username,email,password,profileImage);
                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

}
