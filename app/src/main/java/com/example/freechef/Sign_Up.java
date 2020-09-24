package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_Up extends AppCompatActivity {

    Spinner Gender;
    UsersDatabase U;
    EditText date;
    EditText email;
    EditText firstname;
    EditText lastname;
    EditText password;
    EditText phone;
    Button register;
    EditText username;
    TextView warn_email;
    TextView warn_username;
    TextView warn_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        U = new UsersDatabase(this);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone_number);
        date = (EditText) findViewById(R.id.date);
        Gender = (Spinner) findViewById(R.id.gender);
        warn_username = (TextView) findViewById(R.id.warn_username);
        warn_email = (TextView) findViewById(R.id.warn_email);
        warn_pass = (TextView) findViewById(R.id.warn_password);

        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = "";
                if (!firstname.getText().toString().equals(str) &&
                        !lastname.getText().toString().equals(str) &&
                        !username.getText().toString().equals(str) &&
                        !email.getText().toString().equals(str) &&
                        !password.getText().toString().equals(str) &&
                        !phone.getText().toString().equals(str) &&
                        !date.getText().toString().equals(str) &&
                        Gender.getSelectedItem().toString() != null)
                {
                    Pair<Cursor, Cursor> check = new Pair( U.Returnalluser().first, U.Returnalluser().second);
                    boolean valid = true;
                    while (! check.first.isAfterLast()) {
                        if (check.first.getString(0).equals(username.getText().toString())) {
                            valid = false;
                            warn_username.setText("This Username is already used");
                            warn_username.setBackgroundColor(Color.DKGRAY);
                            break;
                        }
                        check.first.moveToNext();
                    }
                    while (!check.second.isAfterLast()) {
                        if (check.second.getString(0).equals(email.getText().toString())) {
                            valid = false;
                            warn_email.setText("This Email is already used");
                            warn_email.setBackgroundColor(Color.DKGRAY);
                            break;
                        }
                        check.second.moveToNext();
                    }
                    if (password.getText().toString().length() < 6) {
                        valid = false;
                        warn_pass.setText("This Password is too short");
                        warn_pass.setBackgroundColor(Color.DKGRAY);
                    }
                    if (valid) {
                        Sign_Up.this.U.AddUser(firstname.getText().toString(),lastname.getText().toString(), username.getText().toString(),email.getText().toString(), password.getText().toString(),Gender.getSelectedItem().toString(), date.getText().toString(), phone.getText().toString());
                        Intent i = new Intent(Sign_Up.this, Main_Menu.class);
                        i.putExtra("ID",username.getText().toString());
                        startActivity(i);
                        String name = "Welcome " +firstname.getText().toString();
                        Toast.makeText(getApplicationContext(), name , Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
                else
                    Toast.makeText(Sign_Up.this.getApplicationContext(), "Please fill all spaces", Toast.LENGTH_LONG).show();
            }
        });
    }
}