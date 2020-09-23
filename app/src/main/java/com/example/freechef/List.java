package com.example.freechef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class List extends AppCompatActivity {

    ArrayList<Dish> dishesList;
    Cursor cursor;
    Dish s;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        user = getIntent().getExtras().getString("ID");

        ListView list_view = (ListView)findViewById(R.id.Main_Menu);

        dishesList= new ArrayList<>();

        DishesDatabase database = new DishesDatabase(this);
        cursor= database.FetchAllDishes();

        while(!(cursor.isAfterLast()))
        {
            if(cursor.getString(1).equals(user)) {
                byte[] photo = cursor.getBlob(6);
                dishesList.add(new Dish(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), photo));
            }
            cursor.moveToNext();
        }

        DishAdapter dish_adapter = new DishAdapter(this,dishesList);
        list_view.setAdapter(dish_adapter);

        registerForContextMenu(list_view);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        TextView dishname=(TextView)info.targetView.findViewById(R.id.Item_Name);
        TextView dishdes=(TextView)info.targetView.findViewById(R.id.Item_Description);
        TextView dishprice=(TextView)info.targetView.findViewById(R.id.Item_Price);
        ImageView dishimg=(ImageView)info.targetView.findViewById(R.id.Dish_Image);

        String Name=dishname.getText().toString();
        String Des=dishdes.getText().toString();
        String Price=dishprice.getText().toString();
        BitmapDrawable drawable = (BitmapDrawable) dishimg.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        byte [] arr = getBitmapAsByteArray(bitmap);
        DishesDatabase dish=new DishesDatabase(this);
        if(item.getItemId()==R.id.editDish)
        {
            Intent i=new Intent(List.this,Edit_Dish.class);
            Dish d=dish.Returndish_dishid(Name,Des,Price,arr);
            String name=d.getName();
            String des=d.getDescription();
            String price=d.getPrice();
            byte[]arr2=d.getImage();
            int id = dish.Returned_DishID(name , des , price );
            i.putExtra("userid" ,user);
            i.putExtra("dishid" , id);
            i.putExtra("Name",name);
            i.putExtra("des",des);
            i.putExtra("price",price);
            i.putExtra("image",arr2);
            startActivity(i);
            return true;
        }
        else if(item.getItemId()==R.id.deletedish)
        {
            dish.DeleteDish(user , Name,Des,Price);
            Toast.makeText(getApplicationContext(),"Dish Deleted Successfully!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(List.this , Dishes_Menu.class);
            i.putExtra("ID", user);
            startActivity(i);
            return true;
        }
        return false;
    }



    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}