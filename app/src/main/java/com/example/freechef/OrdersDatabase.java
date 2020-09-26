package com.example.freechef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OrdersDatabase extends SQLiteOpenHelper {

    private static String Databasename = "Orders_Databasee";
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

    public void DeleteOrder(int id)
    {
        db=getWritableDatabase();
        db.delete("orders","id='"+ id+"'",null);
        db.close();
    }

    public int ReturnOrderID (String userid , String dishid) {

        db=getReadableDatabase();
        Cursor cursor = FetchAllOrders();
        db.close();
        while (!cursor.isAfterLast()){
                if(cursor.getString(1).equals(userid) && cursor.getString(2).equals(dishid))
                {
                    return Integer.parseInt(cursor.getString(0));
                }
            cursor.moveToNext();
         }
        return 0;
    }


    public Cursor FetchAllOrders()
    {
        db=getReadableDatabase();
        String[] row={"id", "touser" , "dish" , "fromuser" , "quantity" , "price"};

        Cursor cursor=db.query("orders",row,null,null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public Order Return_Order_byId(int id)
    {
        db=getReadableDatabase();
        Order order = null;
        Cursor cursor = FetchAllOrders();
        while(!(cursor.isAfterLast()))
        {
            int i=Integer.parseInt(cursor.getString(0));
            if(i==id)
            {
                 order =new Order(cursor.getString(1),cursor.getString(3),cursor.getString(2),cursor.getString(4),cursor.getString(5));
                break;
            }
            cursor.moveToNext();
        }
        db.close();
        return order;
    }
}
