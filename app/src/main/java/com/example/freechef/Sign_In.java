package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_In extends AppCompatActivity {

    UsersDatabase U;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        U = new UsersDatabase(this);
        Button sign_up = (Button) findViewById(R.id.sign_up);
        Button login = (Button) findViewById(R.id.login);
        final EditText username = (EditText) findViewById(R.id.p_username);
        final EditText pass = (EditText) findViewById(R.id.p_password);
        final TextView warn = (TextView) findViewById(R.id.warning);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = "";
                if (username.getText().toString().equals(str) || pass.getText().toString().equals(str)) {
                    Toast.makeText(Sign_In.this.getApplicationContext(), "Please fill all spaces", Toast.LENGTH_LONG).show();
                }
                else {
                    Cursor cursor = U.FetchAllusers();
                    boolean f = false;
                    while (!cursor.isAfterLast()) {
                        if (cursor.getString(0).equals(username.getText().toString()) && cursor.getString(1).equals(pass.getText().toString())) {
                            f = true;
                            String name = "Welcome " + cursor.getString(2);
                            Toast.makeText(getApplicationContext(), name , Toast.LENGTH_LONG).show();
                            break;
                        }
                        cursor.moveToNext();
                    }

                    if (!f) {
                        warn.setText("Invalid Username or Password");
                        warn.setBackgroundColor(Color.DKGRAY);
                    }
                    if (f) {
                        Intent i = new Intent(Sign_In.this, Main_Menu.class);
                        i.putExtra("ID", cursor.getString(0));
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Sign_In.this, Sign_Up.class));
            }
        });
    }

}