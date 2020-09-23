package com.example.freechef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    ArrayList<Dish> dishesList;
    Cursor cursor;
    Dish s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final String user = getIntent().getExtras().getString("ID");

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
        int select=(info.position);
        select++;
        DishesDatabase dish=new DishesDatabase(this);
        if(item.getItemId()==R.id.editDish)
        {
            Intent i=new Intent(List.this,Edit_Dish.class);
            Dish d=dish.Returndish_dishid(select);
            String name=d.getName();
            String des=d.getDescription();
            String price=d.getPrice();
            byte[]arr=d.getImage();
            i.putExtra("Name",name);
            i.putExtra("des",des);
            i.putExtra("price",price);
            i.putExtra("image",arr);
            startActivity(i);
            return true;
        }
        return false;
    }
}