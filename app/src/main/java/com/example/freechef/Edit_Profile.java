package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Edit_Profile extends AppCompatActivity {

    UsersDatabase U;
    EditText first;
    EditText second;
    EditText email;
    EditText phone;
    EditText date;
    Spinner gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        U = new UsersDatabase(this);
        first = (EditText) findViewById(R.id.firstname_pp);
        second = (EditText) findViewById(R.id.lastname_pp);
        email = (EditText) findViewById(R.id.email_pp);
        phone = (EditText) findViewById(R.id.phone_number_pp);
        date = (EditText) findViewById(R.id.date_pp);
        gender = (Spinner) findViewById(R.id.gender_pp);

        Intent i = getIntent();

        final User u = (User) i.getSerializableExtra("user");
        first.setText(u.getFirstname());
        second.setText(u.getSecondname());
        email.setText(u.getEmail());
        phone.setText(u.getPhone_number());
        date.setText(u.getDataofbirth());

        if (u.getGender().equals("Male")) {
            gender.setSelection(0);
        } else {
            gender.setSelection(1);
        }

        Button update = (Button) findViewById(R.id.Update_pp);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                if (first.getText().toString().equals(str) || second.getText().toString().equals(str) || email.getText().toString().equals(str) || phone.getText().toString().equals(str) || date.getText().toString().equals(str)) {
                    Toast.makeText(getApplicationContext(), "Please fill all spaces", Toast.LENGTH_LONG).show();
                }
                else
                {
                    U.EditUser(first.getText().toString(), second.getText().toString(), u.getUsername(), email.getText().toString(), u.getPassword(), gender.getSelectedItem().toString(),date.getText().toString(), phone.getText().toString());
                    Toast.makeText(getApplicationContext(), " Information Updated successfully ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Edit_Profile.this, Profile.class);
                    intent.putExtra("ID" , u.getUsername());
                    startActivity(intent);
                    finish();
            }
            }
        });
        };
    }
