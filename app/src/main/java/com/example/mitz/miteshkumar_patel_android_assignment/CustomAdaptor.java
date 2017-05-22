package com.example.mitz.miteshkumar_patel_android_assignment;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Mitz on 16-04-30.
 */
public class CustomAdaptor extends SimpleCursorAdapter {
    DbHelper dbHelper;
     Context context;
     int layout,userID;
     Cursor c;
     final LayoutInflater inflater;
    Integer[] Imgs = {R.drawable.nexus,R.drawable.moto};
    Random rand = new Random();
    public CustomAdaptor(int userID,DbHelper dbHelper,Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.c = c;
        this.userID = userID;
        this.dbHelper = dbHelper;

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }
    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        TextView modelName = (TextView) view.findViewById(R.id.mycartName);
        TextView modelPrice = (TextView) view.findViewById(R.id.mycartprice);
        ImageView modelImage = (ImageView) view.findViewById(R.id.mycartImageView);
        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);

        modelName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        modelPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow("price")));

        int  randomImage = rand.nextInt(Imgs.length);
        int image = Imgs[randomImage];
        modelImage.setImageResource(image);

        long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        deleteButton.setTag(id);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = (long) ((Button) v).getTag();
                dbHelper.deleteRow(id);
                swapCursor(dbHelper.getCartData(userID));
                notifyDataSetChanged();

            }
        });
    }
}
