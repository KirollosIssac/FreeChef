package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Chef_Profile extends AppCompatActivity {

    UsersDatabase U ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef__profile);

        U = new UsersDatabase(this);
        String chef = getIntent().getExtras().getString("chef_id");
        User x = U.Fetchuser(chef);

        TextView first = (TextView)findViewById(R.id.chef_firstname);
        TextView second = (TextView)findViewById(R.id.chef_secondname);
        TextView email = (TextView)findViewById(R.id.chef_email);
        TextView phone = (TextView)findViewById(R.id.chef_phonenumber);
        TextView gender = (TextView)findViewById(R.id.chef_gender);

        first.setText(x.getFirstname());
        second.setText(x.getSecondname());
        email.setText(x.getEmail());
        phone.setText(x.getPhone_number());
        gender.setText(x.getGender());

    }
}