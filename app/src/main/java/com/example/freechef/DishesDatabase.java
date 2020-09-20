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

public class DishesDatabase extends SQLiteOpenHelper {

    private static String Databasename = "DishesDatabasee";
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
        String[] row={ "userid" , "Name" , "Description" , "Price" , "Rate" , "img"};

        Cursor cursor=db.query("dishes",row,null,null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor Returndish_userid (String id) {

        db=getReadableDatabase();

        String selectQuery = "SELECT Name,Description,Price,Rate,img from dishes where userid = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;

    }
    public Dish Returndish_dishid (String id) {

        db=getReadableDatabase();

        String selectQuery = "SELECT Name,Description,Price,Rate,img from dishes where userid = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        db.close();
        return (new Dish(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getBlob(5)));

    }


    public void Edit_Dish(String old_name,String Nname,String Ndes,String Nprice,String Nrate,byte[] Nimg)
    {
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
    private byte[] imagemTratada(byte[] imagem_img)
    {
        while (imagem_img.length > 500000)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;
    }

}
