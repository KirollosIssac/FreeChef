package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dishes_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes__menu);

        final String user = getIntent().getExtras().getString("ID");

        Button add = (Button)findViewById(R.id.Add);
        Button view = (Button)findViewById(R.id.View);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dishes_Menu.this,Add_Dish.class);
                i.putExtra("ID" , user);
                startActivity(i);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dishes_Menu.this,List.class);
                i.putExtra("ID" , user);
                startActivity(i);
            }
        });
    }
}