package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Order_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details);
        final int ID = getIntent().getExtras().getInt("ID");
        DishesDatabase Dishesdatabase = new DishesDatabase(this);
        final OrdersDatabase Ordersdatabase = new OrdersDatabase(this);
        final Order Order_selected = Ordersdatabase.Return_Order_byId(ID);
        final Dish Dish_Selected = Dishesdatabase.Returndish_dishid(ID);

        ImageView Dish_Image = (ImageView)findViewById(R.id.Dish_Image);
        TextView Dish_Name = (TextView) findViewById(R.id.Dish_Name);
        TextView Dish_Description = (TextView)findViewById(R.id.Dish_Description);
        final TextView Total_Price = (TextView)findViewById(R.id.Total_Price);
        TextView Dish_Rate = (TextView)findViewById(R.id.Dish_Rate);
        final TextView Dish_Quantity = (TextView)findViewById(R.id.Quantity);
        Button View_Chef_Profile = (Button)findViewById(R.id.View_Chef);
        Button Delete_Order = (Button)findViewById(R.id.Delete);

        Bitmap bmp= BitmapFactory.decodeByteArray(Dish_Selected.getImage(), 0 , Dish_Selected.getImage().length);
        Dish_Image.setImageBitmap(bmp);

        Dish_Name.setText(Dish_Selected.getName());
        Dish_Description.setText(Dish_Selected.getDescription());
        Total_Price.setText(Order_selected.getPrice());
        Dish_Rate.setText(Dish_Selected.getRate());
        Dish_Quantity.setText(Order_selected.getQuantity());

        Delete_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ordersdatabase.DeleteOrder(ID);
                Toast.makeText(getApplicationContext(),"Order Deleted Successfully!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Order_Details.this,User_Outcoming_Orders_History.class);
                startActivity(i);
            }
        });

        View_Chef_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moamen
            }
        });
    }
}