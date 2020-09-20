package com.example.freechef;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Edit_Dish extends AppCompatActivity {

    Uri uri;
    int selectedphoto=1;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__dish);


        final EditText name=(EditText)findViewById(R.id.dishname);
        final EditText des=(EditText)findViewById(R.id.description);
        final EditText price=(EditText)findViewById(R.id.price);
        img=(ImageView)findViewById(R.id.photo);

        final DishesDatabase database = new DishesDatabase(this);


        name.setText(getIntent().getExtras().getString("Name"));
        des.setText(getIntent().getExtras().getString("des"));
        price.setText(getIntent().getExtras().getString("price"));

        final byte[]arr3=getIntent().getExtras().getByteArray("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(arr3, 0, arr3.length);
        img.setImageBitmap(bmp);

        Button update=(Button)findViewById(R.id.updatedish);
        Button addphoto=(Button)findViewById(R.id.addphoto);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                byte [] arr = getBitmapAsByteArray(bitmap);

                String n=name.getText().toString();
                String d=des.getText().toString();
                String p=price.getText().toString();
                database.Edit_Dish(getIntent().getExtras().getString("Name"),n,d,p,"5",arr);
                Toast.makeText(getApplicationContext()," Dish Edited successfully ",Toast.LENGTH_SHORT).show();
            }
        });

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in,selectedphoto);
            }
        });

    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == selectedphoto && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {

                Bitmap bit = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                img.setImageBitmap(bit);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}