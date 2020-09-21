package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class All_Dishes_Menu extends AppCompatActivity {

    ArrayList<Dish> dishesList;
    Cursor cursor;
    Dish s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);


        ListView list_view = (ListView)findViewById(R.id.Main_Menu);

        dishesList= new ArrayList<>();

        DishesDatabase database = new DishesDatabase(this);
        cursor= database.FetchAllDishes();

        while(!(cursor.isAfterLast()))
        {
            byte[] photo=cursor.getBlob(6);
            dishesList.add(new Dish(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),photo));
            cursor.moveToNext();
        }

        DishAdapter dish_adapter = new DishAdapter(this,dishesList);
        list_view.setAdapter(dish_adapter);

        View On_Click = (View)findViewById(R.id.click);

        On_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}