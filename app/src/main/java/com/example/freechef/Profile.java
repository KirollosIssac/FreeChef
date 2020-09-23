package com.example.freechef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    UsersDatabase U;
    TextView date;
    Button edit;
    TextView email;
    TextView first;
    TextView gender;
    String id;
    TextView phone;
    TextView second;
    User user;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        this.U = new UsersDatabase(this);
        String string = getIntent().getExtras().getString("ID");
        id = string;
        user = U.Fetchuser(string);
        first = (TextView) findViewById(R.id.profile_firstname);
        second = (TextView) findViewById(R.id.profile_secondname);
        username = (TextView) findViewById(R.id.profile_username);
        email = (TextView) findViewById(R.id.profile_email);
        phone = (TextView) findViewById(R.id.profile_phonenumber);
        gender = (TextView) findViewById(R.id.profile_gender);
        date = (TextView) findViewById(R.id.profile_dateofbirth);

        first.setText(this.user.getFirstname());
        second.setText(this.user.getSecondname());
        username.setText(this.user.getUsername());
        email.setText(this.user.getEmail());
        phone.setText(this.user.getPhone_number());
        gender.setText(this.user.getGender());
        date.setText(this.user.getDataofbirth());
        edit = (Button) findViewById(R.id.profile_edit);

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, Edit_Profile .class);
                i.putExtra("user", Profile.this.user);
                startActivity(i);
            }
        });
    }
    public void onBackPressed(){
        Intent i = new Intent(Profile.this, Main_Menu.class);
        i.putExtra("ID", user.getUsername());
        startActivity(i);
    }
}