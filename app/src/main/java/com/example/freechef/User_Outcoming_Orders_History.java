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

public class User_Outcoming_Orders_History extends AppCompatActivity {

    String Log_in_User;
    ArrayList<Dish> dishesList;
    ArrayList<Order> ordersList;
    Cursor cursor;
    Dish current_dish;
    Order current_order;
    DishesDatabase Dishesdatabase;
    OrdersDatabase Ordersdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Dishesdatabase = new DishesDatabase(this);
        Ordersdatabase = new OrdersDatabase(this);

        Log_in_User = getIntent().getExtras().getString("ID");

        ListView list_view = (ListView)findViewById(R.id.Main_Menu);

        dishesList= new ArrayList<>();
        ordersList = new ArrayList<>();

        cursor = Ordersdatabase.FetchAllOrders();

        while(!(cursor.isAfterLast()))
        {
            if (Log_in_User.equals(cursor.getString(1))) {
                ordersList.add(new Order(Log_in_User , cursor.getString(3), cursor.getString(2), cursor.getString(4), cursor.getString(5)));
            }
            cursor.moveToNext();
        }

        for(int i=0;i<ordersList.size();i++)
        {
            dishesList.add(Dishesdatabase.Returndish_userid(ordersList.get(i).getFromuser()));
        }

        DishAdapter dish_adapter = new DishAdapter(this,dishesList);
        list_view.setAdapter(dish_adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(User_Outcoming_Orders_History.this,Order_Details.class);


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
                intent.putExtra("loginUserID",Log_in_User);
                startActivity(intent);
            }
        });
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}