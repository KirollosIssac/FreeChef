package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class All_Dishes_Menu extends AppCompatActivity {

    ArrayList<Dish> dishesList;
    Cursor cursor;
    Dish s;
    DishesDatabase database = new DishesDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final String user = getIntent().getExtras().getString("ID");

        ListView list_view = (ListView)findViewById(R.id.Main_Menu);

        dishesList= new ArrayList<>();

        cursor= database.FetchAllDishes();

        while(!(cursor.isAfterLast()))
        {
            if (! user.equals(cursor.getString(1))){
                byte[] photo=cursor.getBlob(6);
                dishesList.add(new Dish(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),photo));
            }
            cursor.moveToNext();
        }

        DishAdapter dish_adapter = new DishAdapter(this,dishesList);
        list_view.setAdapter(dish_adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(All_Dishes_Menu.this,Dish_Info.class);
                intent.putExtra("ID" , user);

                TextView dishname=(TextView)view.findViewById(R.id.Item_Name);
                TextView dishdes=(TextView)view.findViewById(R.id.Item_Description);
                TextView dishprice=(TextView)view.findViewById(R.id.Item_Price);
                ImageView dishimg=(ImageView)view.findViewById(R.id.Dish_Image);

                String Name=dishname.getText().toString();
                String Des=dishdes.getText().toString();
                String Price=dishprice.getText().toString();
                BitmapDrawable drawable = (BitmapDrawable) dishimg.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                byte [] arr = getBitmapAsByteArray(bitmap);

                intent.putExtra("Name",Name);
                intent.putExtra("Des",Des);
                intent.putExtra("Price",Price);
                intent.putExtra("Image",arr);
                startActivity(intent);
            }
        });


    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }
}