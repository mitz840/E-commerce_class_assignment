package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button loginButton;
    Button RegisterButton;
    EditText EmailTextView,PasswordTextView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        loginButton = (Button)findViewById(R.id.loginBTN);
        RegisterButton = (Button)findViewById(R.id.registerBTN);
        EmailTextView = (EditText)findViewById(R.id.loginTXT);
        PasswordTextView = (EditText)findViewById(R.id.passTXT);
       Cursor cursor = dbHelper.checkUser();
//        System.out.println(cursor.getCount());




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = EmailTextView.getText().toString().toLowerCase();
                String password = PasswordTextView.getText().toString();
                String storedPassword = dbHelper.getSingleEntry(useremail);
                int userID = dbHelper.getUserID(useremail);



                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PasswordTextView.setText("");


                    }
                });

                if(!EmailTextView.getText().toString().isEmpty() && !PasswordTextView.getText().toString().isEmpty()) {


                    if (password.equals(storedPassword)) {
                        Intent i = new Intent(getApplicationContext(), Welcome.class);
                        i.putExtra("USER",EmailTextView.getText().toString());
                        i.putExtra("USERID",userID);
                        startActivity(i);
                        Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(MainActivity.this, "Wrong Username OR Password", Toast.LENGTH_LONG).show();
                        alert.setMessage("Wrong Username OR Password.").show();
                    }
                }
                else if (EmailTextView.getText().toString() == "" || PasswordTextView.getText().toString()== ""){
                   // Toast.makeText(MainActivity.this, "Username OR Password can not be blank space", Toast.LENGTH_LONG).show();
                    alert.setMessage("Username OR Password can not be blank space").show();


                }
                else{
                    Toast.makeText(MainActivity.this, "Username OR password field can't be empty", Toast.LENGTH_LONG).show();
                    alert.setMessage("Username OR Password can not be blank space").show();


                }

            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);

            }
        });
    }
}
