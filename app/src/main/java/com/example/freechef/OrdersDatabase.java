package com.example.freechef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OrdersDatabase extends SQLiteOpenHelper {

    private static String Databasename = "OrdersDatabase";
    private String OrdersCreation="create table orders" + "( id integer primary key autoincrement," + "touser text not null , dish text not null, fromuser text not null , quantity text not null , Price text not null)";
    SQLiteDatabase db;

    public OrdersDatabase(@Nullable Context context) {
        super(context, Databasename, null ,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OrdersCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists orders");
        onCreate(db);
    }

    public void AddOrder(String touser ,String dish, String fromuser , String quant , String price )
    {
        db=getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("touser" , touser);
        c.put("dish" , dish);
        c.put("fromuser",fromuser);
        c.put("quantity",quant);
        c.put("Price",price);
        db.insert("orders",null , c);
        db.close();
    }

    public Cursor Return_to_user (String userid) {

        db=getReadableDatabase();

        String selectQuery = "SELECT fromuser,dish,quantity,Price from orders where touser = " + userid  ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;

    }

    public Cursor Return_to_chef (String chef_id) {

        db=getReadableDatabase();

        String selectQuery = "SELECT touser,dish,quantity,Price from orders where fromuser = " + chef_id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;

    }
}
