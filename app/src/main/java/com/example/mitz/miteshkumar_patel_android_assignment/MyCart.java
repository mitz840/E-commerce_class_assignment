package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MyCart extends Fragment {
    ListView mylistView;
    SimpleCursorAdapter cursorAdapter;
    Cursor cartDataCursor,currentUserCursor;
    DbHelper dbHelper;
    Button deleteBtn;
     int userID;

    public static MyCart newInstance() {
        return new MyCart();
    }

    public MyCart() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
         userID = i.getIntExtra("USERID",0);

        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        mylistView = (ListView)view.findViewById(R.id.mycartListView);
        dbHelper = new DbHelper(getActivity());
        cartDataCursor = dbHelper.getCartData(userID);
       // deleteBtn = (Button)view.findViewById(R.id.deleteButton);

        String[] from = {dbHelper.CMODELNAME,dbHelper.CMODELPRICE,dbHelper.CMODELIMAGE};
        int[] to = {R.id.mycartName,R.id.mycartprice,R.id.mycartImageView};
        //cursorAdapter = new SimpleCursorAdapter(getContext(),R.layout.cart_layout,cartDataCursor,from,to,0);
        cursorAdapter = new CustomAdaptor(userID,dbHelper,getContext(),R.layout.cart_layout,cartDataCursor,from,to,0);
        mylistView.setAdapter(cursorAdapter);
        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbHelper.deleteRow(id);

                Cursor deleteCursor = dbHelper.getCartData(userID);
                cursorAdapter.swapCursor(deleteCursor);
                cursorAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        Intent i = getActivity().getIntent();
        int userID = i.getIntExtra("USERID",0);
        //Refresh your stuff here
        cartDataCursor = dbHelper.getCartData(userID);
        cursorAdapter.swapCursor(cartDataCursor);
        cursorAdapter.notifyDataSetChanged();
    }

}
