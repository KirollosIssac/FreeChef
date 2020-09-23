package com.example.freechef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import androidx.annotation.Nullable;

public class UsersDatabase  extends SQLiteOpenHelper {

    private static String Databasename = "UsersDatabase";
    private String UsersCreation="create table users" + "( id integer primary key autoincrement," + "firstname text not null , secondname text not null, username text not null , email text not null , password text not null , gender text not null ,dateofbirth text not null , phone text not null )";
    SQLiteDatabase db;

    public UsersDatabase (@Nullable Context context) {
        super(context, Databasename, null ,1 );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
        onCreate(db);
    }

    public void AddUser(String firstname ,String secondname, String username , String email , String password , String gender , String date ,String phone )
    {
        db=getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("firstname" , firstname);
        c.put("secondname" , secondname);
        c.put("username",username);
        c.put("email",email);
        c.put("password",password);
        c.put("gender",gender);
        c.put("dateofbirth",date);
        c.put("phone" , phone);
        db.insert("users",null , c);
        db.close();
    }

    public void EditUser(String firstname, String secondname, String username, String email, String password, String gender, String date, String phone) {

        db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("firstname", firstname);
        c.put("secondname", secondname);
        c.put("username", username);
        c.put("email", email);
        c.put("password", password);
        c.put("gender", gender);
        c.put("dateofbirth", date);
        c.put("phone", phone);
        db.update("users", c, "username like?", new String[]{username});
        db.close();
    }


    public Pair<Cursor,Cursor> Returnalluser () {

        db=getReadableDatabase();

        String[] row={ "username"};
        Cursor usernames=db.query("users",row,null,null,null,null,null);

        if(usernames!=null)
        {
            usernames.moveToFirst();
        }

        String[] row2={ "email"};
        Cursor emails=db.query("users",row2,null,null,null,null,null);

        if(emails!=null)
        {
            emails.moveToFirst();
        }

        db.close();
        return (new Pair(usernames,emails));

    }
    public Cursor FetchAllusers() {

        db = getReadableDatabase();
        String [] row = {"username", "password", "firstname"};

        Cursor cursor = db.query("users", row , null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        this.db.close();
        return cursor;
    }

    public User Fetchuser(String id) {
        db = getReadableDatabase();
        String [] row ={"firstname", "secondname", "username", "email", "password", "phone", "gender", "dateofbirth"} ;

        Cursor cursor = db.query("users", row , null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();

        while (!cursor.isAfterLast()) {
            if (cursor.getString(2).equals(id)) {
                return new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            }
            cursor.moveToNext();
        }
        String str = id;
        return new User(null, null, null, null, null, null, null, null);
    }

}