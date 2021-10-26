package com.example.apnakhata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_Activity extends AppCompatActivity {
    TextInputLayout fullname,shopname,email,phoneno,username,password;
    TextView fullnameLabel,usernameLabel;
    DatabaseReference reference;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        reference = FirebaseDatabase.getInstance().getReference("Users");

        fullname = findViewById(R.id.name);
        shopname = findViewById(R.id.shop_name);
        email = findViewById(R.id.emailId);
        phoneno = findViewById(R.id.mobile_no);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        fullnameLabel = findViewById(R.id.full_name);
        usernameLabel = findViewById(R.id.user_name);
        update = (Button) findViewById(R.id.update);
        showdata();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateprofile(view);
                Intent intent= new Intent(Profile_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showdata() {
        reference.child(GlobalVariable.username).addListenerForSingleValueEvent(listener);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                String  pass = snapshot.child("password").getValue(String.class);
                String  emailid = snapshot.child("email").getValue(String.class);
                String  name = snapshot.child("name").getValue(String.class);
                String  shop = snapshot.child("shopname").getValue(String.class);
                String  phoneNo = snapshot.child("phoneno").getValue(String.class);
                String  user = snapshot.child("username").getValue(String.class);

                fullnameLabel.setText(name);
                usernameLabel.setText(user);
                fullname.getEditText().setText(name);
                shopname.getEditText().setText(shop);
                email.getEditText().setText(emailid);
                phoneno.getEditText().setText(phoneNo);
                username.getEditText().setText(user);
                password.getEditText().setText(pass);

                }

            }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void updateprofile(View view){


        isNameChanged();
        isShopNameChanged();
        isEmailChanged();
        isPhoneNoChanged();
        isUsernameChanged();
        isPasswordChanged();

            Toast.makeText(Profile_Activity.this,"Data has been updated", Toast.LENGTH_SHORT).show();

    }

    private boolean isNameChanged() {
        if(! GlobalVariable.name.equals(fullname.getEditText().getText().toString())) {
            reference.child(GlobalVariable.username).child("name").setValue(fullname.getEditText().getText().toString());
            GlobalVariable.name=fullname.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isShopNameChanged() {
        if(! GlobalVariable.shopname.equals(shopname.getEditText().getText().toString())) {
            reference.child(GlobalVariable.username).child("shopname").setValue(shopname.getEditText().getText().toString());
            GlobalVariable.shopname=shopname.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isEmailChanged() {
        if(! GlobalVariable.email.equals(email.getEditText().getText().toString())) {
            reference.child(GlobalVariable.username).child("email").setValue(email.getEditText().getText().toString());
            GlobalVariable.email=email.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isPhoneNoChanged() {
        if(! GlobalVariable.phoneno.equals(phoneno.getEditText().getText().toString())) {
            reference.child(GlobalVariable.username).child("phoneno").setValue(phoneno.getEditText().getText().toString());
            GlobalVariable.phoneno=phoneno.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isUsernameChanged() {
        if(! GlobalVariable.username.equals(username.getEditText().getText().toString())) {
            reference.child(GlobalVariable.username).child("username").setValue(username.getEditText().getText().toString());
            GlobalVariable.username=username.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isPasswordChanged() {
        if(! GlobalVariable.password.equals(password.getEditText().getText().toString())) {
            reference.child(GlobalVariable.username).child("password").setValue(password.getEditText().getText().toString());
            GlobalVariable.password=password.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }



}