package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

        final String Name = getIntent().getExtras().getString("Name");
        final String Des=getIntent().getExtras().getString("Des");
        final String Price=getIntent().getExtras().getString("Price");
        final byte[] arr=getIntent().getExtras().getByteArray("Image");
        final String UserId = getIntent().getExtras().getString("loginUserID");
        DishesDatabase Dishesdatabase = new DishesDatabase(this);
        final OrdersDatabase Ordersdatabase = new OrdersDatabase(this);

        final int DishID=Dishesdatabase.Returned_DishID( Name,Des,Price);

        final Cursor Order_selected = Ordersdatabase.FetchAllOrders();

        final Dish Dish_Selected = Dishesdatabase.Returndish_dishid(Name,Des,Price,arr);


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

        while(!(Order_selected.isAfterLast()))
        {
            if( DishID == Integer.parseInt(Order_selected.getString(2)) && UserId.equals(Order_selected.getString(1)) )
            {
                Dish_Quantity.setText(Order_selected.getString(4));
                Total_Price.setText(Order_selected.getString(5));
                break;
            }
            Order_selected.moveToNext();
        }

        Dish_Name.setText(Dish_Selected.getName());
        Dish_Description.setText(Dish_Selected.getDescription());
        Dish_Rate.setText(Dish_Selected.getRate());

        Delete_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID =Ordersdatabase.ReturnOrderID (UserId , String.valueOf(DishID) );

                Ordersdatabase.DeleteOrder(ID);
                Toast.makeText(getApplicationContext(),"Order Deleted Successfully!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Order_Details.this,User_Outcoming_Orders_History.class);
                i.putExtra("ID",UserId);
                startActivity(i);
            }
        });

        View_Chef_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Order_Details.this , Chef_Profile.class);
                i.putExtra("chef_id", Dish_Selected.getUserid());
                startActivity(i);
            }
        });
    }
}