package com.example.freechef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class DishesDatabase extends SQLiteOpenHelper {

    private static String Databasename = "DDD";
    private String DishCreation="create table dishes"+"( id integer primary key autoincrement," + "userid text not null , Name text not null , Description text not null , Price text not null , Rate text not null , img blob not null)";

    SQLiteDatabase db;

    public DishesDatabase(Context context)
    {
        super(context, Databasename,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DishCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists dishes");
        onCreate(db);
    }

    public void AddDishes(String userid ,String Name , String desc , String price ,String rate , byte[]image )
    {
        image = imagemTratada(image);
        db=getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("userid" , userid);
        c.put("Name",Name);
        c.put("Description",desc);
        c.put("Price",price);
        c.put("Rate", rate );
        c.put("img",image);
        db.insert("dishes",null , c);
        db.close();
    }

    public Cursor FetchAllDishes()
    {
        db=getReadableDatabase();
        String[] row={"id","userid" , "Name" , "Description" , "Price" , "Rate" , "img"};

        Cursor cursor=db.query("dishes",row,null,null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public Dish Returndish_userid (String userId) {

        db=getReadableDatabase();
        Dish d=null;
        Cursor c=FetchAllDishes();
        while(!(c.isAfterLast()))
        {
            if(userId.equals(c.getString(1)))
            {
                d=new Dish(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getBlob(6));
                break;
            }
            c.moveToNext();
        }
        db.close();
        return d;
    }

    public int Returned_DishID (String name,String des,String price,byte[]image) {

        db=getReadableDatabase();
        Dish d=null;
        Cursor c=FetchAllDishes();
        int i=0;
        while(!(c.isAfterLast()))
        {
            if( name.equals(c.getString(2)) && des.equals(c.getString(3))&&price.equals(c.getString(4)))
            {
                i=Integer.parseInt(c.getString(0));
                break;
            }
            c.moveToNext();
        }
        db.close();
        return i;
    }
    public Dish Returndish_dishid (String name,String des,String price,byte[]image) {

        db=getReadableDatabase();
        Dish d=null;
        Cursor c=FetchAllDishes();
        while(!(c.isAfterLast()))
        {
            int i=Integer.parseInt(c.getString(0));
            if(name.equals(c.getString(2)) && des.equals(c.getString(3))&&price.equals(c.getString(4)))
            {
                d=new Dish(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getBlob(6));
                break;
            }
            c.moveToNext();
        }
        db.close();
        return d;
    }


    public void Edit_Dish(String old_name,String Nname,String Ndes,String Nprice,String Nrate,byte[] Nimg)
    {
        Nimg=imagemTratada(Nimg);
        db=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Name",Nname);
        row.put("Description",Ndes);
        row.put("Price",Nprice);
        row.put("Rate",Nrate);
        row.put("img",Nimg);
        db.update("dishes",row,"Name like?",new String[]{old_name});
        db.close();
    }

    public void DeleteDish( String userid , String name,String des,String price )
    {
        db=getWritableDatabase();
        Cursor c=FetchAllDishes();
        while(!(c.isAfterLast()))
        {
            if(userid.equals(c.getString(1)) && name.equals(c.getString(2)) && des.equals(c.getString(3))&&price.equals(c.getString(4)))
            {
                int i=Integer.parseInt(c.getString(0));
                deleteID(i);
                break;
            }
            c.moveToNext();
        }
        db.close();
    }
    private void deleteID(int id)
    {
        db=getWritableDatabase();
        db.delete("dishes","id='"+ id+"'",null);
        db.close();
    }

    private byte[] imagemTratada(byte[] imagem_img)
    {
        while (imagem_img.length > 500000)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;
    }

}
