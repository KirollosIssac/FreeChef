package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Received_Dishes extends AppCompatActivity {

    Cursor cursor;
    DishesDatabase Dishesdatabase;
    OrdersDatabase Ordersdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final String user = getIntent().getExtras().getString("ID");

        ArrayList<Order> orders = new ArrayList<Order>();
        ArrayList<Dish> dishes  = new ArrayList<Dish>();
        Dishesdatabase = new DishesDatabase(this);
        Ordersdatabase = new OrdersDatabase(this);
        cursor = Ordersdatabase.FetchAllOrders();

        while(!(cursor.isAfterLast()))
        {
            if (user.equals(cursor.getString(3))) {
                orders.add(new Order(cursor.getString(1) , user , cursor.getString(2), cursor.getString(4), cursor.getString(5)));
            }
            cursor.moveToNext();
        }

        for(int i=0;i<orders.size();i++)
        {
            dishes.add(Dishesdatabase.Returndish_Dishid(orders.get(i).getDish()));
        }

        ListView list =(ListView)findViewById(R.id.Main_Menu);
        DishAdapter dish_adapter = new DishAdapter(this,dishes);
        list.setAdapter(dish_adapter);
    }
}