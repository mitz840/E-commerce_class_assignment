package com.example.mitz.miteshkumar_patel_android_assignment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



public class ProductList extends Fragment   {
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView profilepic ;
    TextView welomeTextView;
    SearchView searchView;
    DbHelper dbHelper;
    ListView mylistView;
    SimpleCursorAdapter cursorAdapter;
    Cursor productDataCursor,currentUserCursor;
    int userID;


    public static ProductList newInstance() {
        return new ProductList();
    }

    public ProductList() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);








        welomeTextView = (TextView)view.findViewById(R.id.welcomeTXT);
        mylistView = (ListView)view.findViewById(R.id.listView);
        profilepic = (ImageView)view.findViewById(R.id.profilePic);
        dbHelper = new DbHelper(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.activity_main_swipe_refresh_layout);
        searchView = (SearchView)view.findViewById(R.id.searchView) ;
        productDataCursor = dbHelper.getProductData();




            String[] from = {dbHelper.MODELNAME,dbHelper.MODELPRICE,dbHelper.MODELQUANTITY,dbHelper.MODELIMAGE,dbHelper.MODELDESCRIPTION};
            int[] to = {R.id.productList_name,R.id.productList_price,R.id.productList_quantity,R.id.productList_imageView,R.id.productList_description};
            cursorAdapter = new SimpleCursorAdapter(getContext(),R.layout.my_layout,productDataCursor,from,to,0);
            mylistView.setAdapter(cursorAdapter);





        Intent intent = getActivity().getIntent();
        int UID = intent.getIntExtra("USERID",0);
        currentUserCursor = dbHelper.getSingleUser(UID);
        int profilePicture = currentUserCursor.getInt(currentUserCursor.getColumnIndex("image"));
        String userName = currentUserCursor.getString(currentUserCursor.getColumnIndex("username"));

        welomeTextView.setText("welcome : "+userName);
        profilepic.setImageResource(profilePicture);
        userID = dbHelper.getUserID(userName);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        productDataCursor = dbHelper.getProductData();
                        cursorAdapter.swapCursor(productDataCursor);
                        cursorAdapter.notifyDataSetChanged();

                        mSwipeRefreshLayout.setRefreshing(false);


                    }
                }, 2500);


            }
        });

        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView modelNameTextView,priceTextView,quentityTextView,descriptionTextView;
                ImageView modelImageView;

                modelNameTextView = (TextView)view.findViewById(R.id.productList_name);
                priceTextView =(TextView)view.findViewById(R.id.productList_price);;
                quentityTextView = (TextView)view.findViewById(R.id.productList_quantity);
                descriptionTextView = (TextView)view.findViewById(R.id.productList_description);
                modelImageView = (ImageView)view.findViewById(R.id.productList_imageView);
                int modelimage = modelImageView.getImageAlpha();




                String name = modelNameTextView.getText().toString();
                String price = priceTextView.getText().toString();
                String descrption = descriptionTextView.getText().toString();
                String quantity = quentityTextView.getText().toString();



                Intent intent = new Intent(getActivity(),ModelDetails.class);

               intent.putExtra("USERID",userID);
                intent.putExtra("NAME",name);
                intent.putExtra("PRICE",price);
                intent.putExtra("DESCRIPTION",descrption);
                intent.putExtra("QUANTITY",quantity);
                intent.putExtra("IMAGE",modelimage);



                intent.putExtra("ID",position+1);
                modelImageView.buildDrawingCache();
                Bitmap image = modelImageView.getDrawingCache();

                 Bundle extras = new Bundle();
                extras.putParcelable("MODELIMAGE", image);
                intent.putExtras(extras);
                startActivity(intent);

                Intent intent1 = new Intent(getActivity(),UpdateProfile.class);
                intent1.putExtra("USERID",userID);
            }
        });

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddProduct.class);
                startActivity(intent);

            }
        });

        if(!userName.equalsIgnoreCase("admin")){
             fab.setVisibility(View.GONE);

        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productDataCursor = dbHelper.getSearchCourseData(newText);
                cursorAdapter.swapCursor(productDataCursor);
                cursorAdapter.notifyDataSetChanged();
                return false;
            }
        });




        return view;
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        productDataCursor = dbHelper.getProductData();
        cursorAdapter.swapCursor(productDataCursor);
        cursorAdapter.notifyDataSetChanged();
        Intent intent = getActivity().getIntent();
        int UID = intent.getIntExtra("USERID",0);
        currentUserCursor = dbHelper.getSingleUser(UID);
        int profilePicture = currentUserCursor.getInt(currentUserCursor.getColumnIndex("image"));
        String userName = currentUserCursor.getString(currentUserCursor.getColumnIndex("username"));

        welomeTextView.setText("welcome : "+userName);
        profilepic.setImageResource(profilePicture);
        userID = dbHelper.getUserID(userName);
    }





}
