package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText modelName,modelPrice,modelQuantity,modeldescription;
    DbHelper dbHelper;
    Spinner spinner;
    String selectedItem;
    Integer[] phoneImgs = {R.drawable.nexus};
    Integer[] watchImgs = {R.drawable.moto};
    Random rand = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        modelName = (EditText)findViewById(R.id.modelName_editText);
        modelPrice = (EditText)findViewById(R.id.modelPrice_editText);
        modelQuantity = (EditText)findViewById(R.id.modelQuantity_editText);
        modeldescription = (EditText)findViewById(R.id.modeldescprition_editText);
        dbHelper = new DbHelper(getApplicationContext());

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String > types = new ArrayList<>();
        types.add("Phones");
        types.add("Smartwatches");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types);
        spinner.setAdapter(spinnerAdapter);





        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.addNewItem);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(AddProduct.this);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                String name,description,category;
                int quantity,randomImage,modelImage;
                double price;






                if(modelName.getText().toString().isEmpty()||modeldescription.getText().toString().isEmpty()||modelQuantity.getText().toString().isEmpty()||modelPrice.getText().toString().isEmpty()){
                    alert.setMessage("please provide all details.").show();
                }else{
                    name = modelName.getText().toString();
                    description = modeldescription.getText().toString();
                    quantity = Integer.parseInt(modelQuantity.getText().toString());
                    price = Double.parseDouble(modelPrice.getText().toString());





                    category = selectedItem;
                    if(selectedItem.equalsIgnoreCase("phones")){
                        randomImage = rand.nextInt(phoneImgs.length);
                         modelImage = phoneImgs[randomImage];

                    }else{
                        randomImage = rand.nextInt(watchImgs.length);
                         modelImage = watchImgs[randomImage];


                    }



                  dbHelper.insertProduct(name,price,quantity,description,modelImage,category);

                    Toast.makeText(getApplicationContext(), "Item added successfully", Toast.LENGTH_LONG).show();
                    finish();



                }







            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        selectedItem = parent.getItemAtPosition(position).toString();


    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
