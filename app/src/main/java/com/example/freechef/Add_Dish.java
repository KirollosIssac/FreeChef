package com.example.freechef;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Add_Dish extends AppCompatActivity {

    int selectedphoto = 1;
    Uri uri;
    Button add_dish ;
    Button add_photo;
    TextView name;
    TextView desc;
    TextView price;
    ImageView I;
    DishesDatabase Dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__dish);

        final  String user = getIntent().getExtras().getString("ID");

        add_dish = (Button)findViewById(R.id.adddish);
        add_photo = (Button)findViewById(R.id.addphoto);
        name = (TextView)findViewById(R.id.dishname);
        desc = (TextView)findViewById(R.id.description);
        price = (TextView)findViewById(R.id.price);
        I = (ImageView)findViewById(R.id.photo);
        Dish = new DishesDatabase(this);

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in,selectedphoto);

            }
        });

        add_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!name.getText().toString().equals("") && !price.getText().toString().equals("") && !desc.getText().toString().equals("") ){
                    BitmapDrawable drawable = (BitmapDrawable) I.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    byte [] arr = getBitmapAsByteArray(bitmap);

                    Dish.AddDishes(user ,name.getText().toString(),desc.getText().toString(),price.getText().toString(),"5",arr);
                    Toast.makeText(getApplicationContext()," Dish added successfully ",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Add_Dish.this , Dishes_Menu.class);
                    i.putExtra("ID", user);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext()," Please fill spaces ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == selectedphoto && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {

                Bitmap bit = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                I.setImageBitmap(bit);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}