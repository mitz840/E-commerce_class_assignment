package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ModelDetails extends AppCompatActivity {

    TextView nameTV,priceTV,qunatityTV,descriptionTV;
    ImageView imageIV;
    DbHelper dbHelper;
    int modelQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_details);

        nameTV = (TextView)findViewById(R.id.mdName);
        priceTV = (TextView)findViewById(R.id.mdPrice);
        qunatityTV = (TextView)findViewById(R.id.mdQuantity);
        descriptionTV = (TextView)findViewById(R.id.mdDescription);
        imageIV = (ImageView)findViewById(R.id.mdImageView);
        qunatityTV.setVisibility(View.GONE);


        Intent intent = getIntent();
        nameTV.setText(intent.getStringExtra("NAME"));
        priceTV.setText(intent.getStringExtra("PRICE"));
        qunatityTV.setText(intent.getStringExtra("QUANTITY"));
        descriptionTV.setText(intent.getStringExtra("DESCRIPTION"));
        Bundle extras = getIntent().getExtras();
        Bitmap image = (Bitmap) extras.getParcelable("MODELIMAGE");
        imageIV.setImageBitmap(image);
        modelQuantity = Integer.parseInt(qunatityTV.getText().toString());

//        imageIV.setImageResource(intent.getIntExtra("IMAGE",0));


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.addToCart);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DbHelper(getApplicationContext());
                Intent i = getIntent();
                String name = nameTV.getText().toString();
                double price = Double.parseDouble(priceTV.getText().toString());
             //  int image = dbHelper.getImage(i.getStringExtra("ID"));
               dbHelper.insertMyCart(i.getIntExtra("USERID",1),name,price,R.drawable.nexus);
                dbHelper.updateProduct(name,modelQuantity-1);


                AlertDialog.Builder alert = new AlertDialog.Builder(ModelDetails.this);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();



                    }
                });
                alert.setTitle("Success");
                alert.setMessage("Item added to cart").show();



            }
        });




    }
}
