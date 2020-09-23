package com.example.freechef;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        final String o = getIntent().getExtras().getString("ID");
        Button Dishes = (Button)findViewById(R.id.dishes);
        Button Order_Dish=(Button)findViewById(R.id.Order_Dish);
        Button Orders =(Button)findViewById(R.id.orders);
        Button recived = (Button)findViewById(R.id.recieveddishes);
        Button logout = (Button) findViewById(R.id.logout);
        Button profile = (Button) findViewById(R.id.profile);

        Dishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Menu.this,Dishes_Menu.class);
                intent.putExtra("ID" , o);
                startActivity(intent);
            }
        });

        recived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Menu.this,Received_Dishes.class);
                intent.putExtra("ID" , o);
                startActivity(intent);
            }
        });

        Order_Dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main_Menu.this,All_Dishes_Menu.class);
                i.putExtra("ID" , o);
                startActivity(i);
            }
        });

        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main_Menu.this,User_Outcoming_Orders_History.class);
                i.putExtra("ID" , o);
                startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Main_Menu.this, Profile.class);
                intent.putExtra("ID", o);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main_Menu.this, Sign_In.class));
                finish();
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Main_Menu.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}