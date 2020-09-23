package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
            byte[] photo=cursor.getBlob(6);
            dishesList.add(new Dish(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),photo));
            cursor.moveToNext();
        }

        DishAdapter dish_adapter = new DishAdapter(this,dishesList);
        list_view.setAdapter(dish_adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(All_Dishes_Menu.this,Dish_Info.class);
                intent.putExtra("ID" , user);
                i++;
                intent.putExtra("Dish_ID",i);
                startActivity(intent);
            }
        });

    }
}