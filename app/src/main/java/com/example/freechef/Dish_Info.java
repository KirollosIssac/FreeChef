package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Dish_Info extends AppCompatActivity {

    DishesDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish__info);

        final String user = getIntent().getExtras().getString("ID");



        final String Name = getIntent().getExtras().getString("Name");
        final String Des=getIntent().getExtras().getString("Des");
        final String Price=getIntent().getExtras().getString("Price");
        final byte[] arr=getIntent().getExtras().getByteArray("Image");

        database= new DishesDatabase(this);
        final OrdersDatabase new_order = new OrdersDatabase(this);
        final Dish Dish_Selected = database.Returndish_dishid(Name,Des,Price,arr);

        ImageView Dish_Image = (ImageView)findViewById(R.id.Dish_Image);
        TextView Dish_Name = (TextView) findViewById(R.id.Dish_Name);
        TextView Dish_Description = (TextView)findViewById(R.id.Dish_Description);
        final TextView Dish_Price = (TextView)findViewById(R.id.Dish_Price);
        ImageView Quantity_Inc = (ImageView)findViewById(R.id.Quantity_Inc);
        ImageView Quantity_Dec = (ImageView)findViewById(R.id.Quantity_Dec);
        TextView Dish_Rate = (TextView)findViewById(R.id.Dish_Rate);
        final TextView Dish_Quantity = (TextView)findViewById(R.id.Quantity);
        Button Order_Button = (Button) findViewById(R.id.Order);


        Bitmap bmp= BitmapFactory.decodeByteArray(Dish_Selected.getImage(), 0 , Dish_Selected.getImage().length);
        Dish_Image.setImageBitmap(bmp);

        Dish_Name.setText(Dish_Selected.getName());
        Dish_Description.setText(Dish_Selected.getDescription());
        Dish_Price.setText(Dish_Selected.getPrice());
        Dish_Rate.setText(Dish_Selected.getRate());
        Dish_Quantity.setText("1");

        Quantity_Inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dish_Quantity.setText(String.valueOf(Integer.parseInt(Dish_Quantity.getText().toString())+1));
                Dish_Price.setText(String.valueOf(Integer.parseInt(Dish_Selected.getPrice())*Integer.parseInt(Dish_Quantity.getText().toString())));
            }
        });

        Quantity_Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(Dish_Quantity.getText().toString()) == 0)
                {
                    Toast.makeText(getApplicationContext(),"Quantity can't be less than 0",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Dish_Quantity.setText(String.valueOf(Integer.parseInt(Dish_Quantity.getText().toString())-1));
                    Dish_Price.setText(String.valueOf(Integer.parseInt(Dish_Selected.getPrice())*Integer.parseInt(Dish_Quantity.getText().toString())));
                }
            }
        });

        Order_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Dish_ID = database.Returned_DishID(Name,Des,Price );
                new_order.AddOrder(user,String.valueOf(Dish_ID),Dish_Selected.getUserid(),Dish_Quantity.getText().toString(),Dish_Price.getText().toString());
                Toast.makeText(getApplicationContext(),"Order Confirmed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}