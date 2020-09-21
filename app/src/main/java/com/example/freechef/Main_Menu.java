package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        Button Dishes = (Button)findViewById(R.id.dishes);
        Button Order_Dish=(Button)findViewById(R.id.Order_Dish);

        Dishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Menu.this,Dishes_Menu.class);
                startActivity(intent);
            }
        });

        Order_Dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main_Menu.this,All_Dishes_Menu.class);
                startActivity(i);
            }
        });
    }
}