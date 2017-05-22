package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Mitz on 16-04-23.
 */
public class DbHelper extends SQLiteOpenHelper {
    static  final String DATABASE_NAME = "USER.DB";
    static final  int DATABASE_VERSION = 1;
    final String TABLE_NAME = "USERINFO";
    final String _ID = "_id";
    final  String USERNAME = "username";
    final String EMAIL = "email";
    final String PASSWORD = "password";
    final String IMAGE = "image";

    final String MTABLE_NAME = "PRODUCTS";
    final String _MID = "_id";
    final  String MODELNAME = "name";
    final String MODELPRICE = "price";
    final String MODELQUANTITY = "quantity";
    final String MODELDESCRIPTION = "description";
    final String MODELIMAGE = "image";
    final String CATEGORY = "category";

    final String CTABLE_NAME = "CART";
    final String _CID = "_id";
    final String CUSTOMERID = "customerid";
    final  String CMODELNAME = "name";
    final String CMODELPRICE = "price";
    final String CMODELQUANTITY = "quantity";
    final String CMODELIMAGE = "image";





    public SQLiteDatabase db;

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table "+TABLE_NAME+ " ( "+_ID+ " integer primary key autoincrement,"+USERNAME+ " text,"+EMAIL+" text,"+PASSWORD+ " text,"+IMAGE+" integer)");
        db.execSQL("create table "+MTABLE_NAME+ " ( "+_MID+ " integer primary key autoincrement,"+MODELNAME+ " text,"+MODELPRICE+" double,"+MODELQUANTITY+ " integer,"+MODELDESCRIPTION+" text,"+MODELIMAGE+" integer,"+CATEGORY+ " text)");
        db.execSQL("create table "+CTABLE_NAME+ " ( "+_CID+ " integer primary key autoincrement,"+CUSTOMERID+" integer,"+CMODELNAME+ " text,"+CMODELPRICE+" double,"+MODELQUANTITY+ " integer,"+CMODELIMAGE+" integer,"+CATEGORY+ " text,foreign key("+CUSTOMERID+") references "+TABLE_NAME+"("+_ID+"))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+MTABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CTABLE_NAME);
        onCreate(db);

    }

    public void insertEntry(String userName ,String email, String password,int image){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME,userName);
        values.put(EMAIL,email);
        values.put(PASSWORD,password);
        values.put(IMAGE,image);
        db.insert(TABLE_NAME,null,values);


    }
    public Cursor checkUser( ){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);

        return  cursor;

    }

    public String getSingleEntry(String username ){
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,"email=?",new String[]{username},null,null,null);
        if(cursor.getCount() < 1){
            cursor.close();
            return "Not Exists";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
        cursor.close();
        return  password;

    }
    public int getUserID(String username){
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,"email=?",new String[]{username},null,null,null);
        if(cursor.getCount() < 1){
            cursor.close();
            return 1;
        }
        cursor.moveToFirst();
        int userID = cursor.getInt(cursor.getColumnIndex(_ID));
        cursor.close();
        return  userID;

    }

    public Cursor getSingleUser(int id){

        String newid;
        newid = Integer.toString(id);
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,"_id=?",new String[]{newid},null,null,null);
        cursor.moveToFirst();
        return  cursor;

    }
        public  void truncateData(){
            String query = "delete from "+TABLE_NAME;
            String query1= "delete from "+MTABLE_NAME;
            String query2 = "delete from "+CTABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            db.execSQL(query1);
            db.execSQL(query2);
    }

    public void insertProduct(String name ,double price, int quantity,String description,int image,String category){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MODELNAME,name);
        values.put(MODELPRICE,price);
        values.put(MODELQUANTITY,quantity);
        values.put(MODELDESCRIPTION,description);
        values.put(MODELIMAGE,image);
        values.put(CATEGORY,category);
        db.insert(MTABLE_NAME,null,values);

    }

    public void updateProduct(String name,int dec){

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MODELQUANTITY,dec);
        db.update(MTABLE_NAME,values,MODELNAME+ "= ?",new String[]{name} );



    }

    public Cursor getProductData( ){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+MTABLE_NAME,null);

        return  cursor;

    }


    public void insertMyCart(int id,String name ,double price, int image){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMERID,id);
        values.put(CMODELNAME,name);
        values.put(CMODELPRICE,price);
        values.put(CMODELIMAGE,image);
        db.insert(CTABLE_NAME,null,values);


    }

    public Cursor getCartData(int id){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+CTABLE_NAME+" where customerid =" +id ,null);
        return  cursor;

    }

    public Cursor getSearchCourseData(String searchText ){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+MTABLE_NAME+" where name LIKE '%"+searchText+"%'",null);
        return  cursor;

    }

    public void deleteRow(Long id){
        db.delete(CTABLE_NAME,_CID+"="+id,null);

    }

    public void updateProfile(int id, String name,String email,String password){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME,name);
        values.put(EMAIL,email);
        values.put(PASSWORD,password);
        String newid;
        newid = Integer.toString(id);
        db.update(TABLE_NAME,values,_ID+ "= ?",new String[]{newid} );

    }


}
