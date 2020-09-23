package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class User_Outcoming_Orders_History extends AppCompatActivity {

    String Log_in_User;
    ArrayList<Dish> dishesList;
    ArrayList<Order> ordersList;
    Cursor cursor;
    Dish current_dish;
    Order current_order;
    DishesDatabase Dishesdatabase = new DishesDatabase(this);
    OrdersDatabase Ordersdatabase = new OrdersDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Log_in_User = getIntent().getExtras().getString("ID");

        ListView list_view = (ListView)findViewById(R.id.Main_Menu);

        dishesList= new ArrayList<>();
        ordersList = new ArrayList<>();

        cursor = Ordersdatabase.Return_to_user(Log_in_User);

        while(!(cursor.isAfterLast()))
        {
            ordersList.add(new Order(Log_in_User,cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            cursor.moveToNext();
        }

        for(int i=0;i<ordersList.size();i++)
        {
            dishesList.add(Dishesdatabase.Returndish_dishid(Integer.parseInt(ordersList.get(i).getDish())));
        }

        DishAdapter dish_adapter = new DishAdapter(this,dishesList);
        list_view.setAdapter(dish_adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(User_Outcoming_Orders_History.this,Order_Details.class);
                i++;
                intent.putExtra("ID",i);
                startActivity(intent);
            }
        });
    }
}