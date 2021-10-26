package com.example.apnakhata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerpage extends AppCompatActivity {

    TextInputEditText regName,regShopName,regEmail,regPhoneNo,regUsername,regPassword;
    Button register_btn;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        regName = findViewById(R.id.reg_Name);
        regShopName = findViewById(R.id.reg_ShopName);
        regEmail = findViewById(R.id.reg_Email);
        regPhoneNo = findViewById(R.id.reg_PhoneNo);
        regUsername = findViewById(R.id.reg_Username);
        regPassword = findViewById(R.id.reg_Password);
        register_btn = (Button) findViewById(R.id.register);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Users");

                if(!validateName() | !validateShopName() | !validateEmail() | !validatePhoneNo() | !validateUsername() | !validatePassword())
                {
                    return;
                }

                String name = regName.getText().toString();
                String shopname = regShopName.getText().toString();
                String email = regEmail.getText().toString();
                String phoneNo = regPhoneNo.getText().toString();
                String username = regUsername.getText().toString();
                String password = regPassword.getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name,shopname,email,phoneNo,username,password);
                reference.child(username).setValue(helperClass);
                Toast.makeText(registerpage.this,"Registration Successful", Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(registerpage.this,login.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateName() {
        String val = regName.getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            return true;
        }
    }

    private Boolean validateShopName() {
        String val = regShopName.getText().toString();
        if (val.isEmpty()) {
            regShopName.setError("Field cannot be empty");
            return false;
        } else {
            regShopName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid Email Address");
            return false;
        } else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getText().toString();
        String phonePattern = "[0-9]{10}";
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(phonePattern)){
            regPhoneNo.setError("Invalid Phone No.");
            return false;
        }else {
            regPhoneNo.setError(null);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if(val.length() >= 15){
            regUsername.setError("Username too long");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getText().toString();
        String passwordPattern = "^" + "\\A\\w{4,20}\\z" + "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(passwordPattern)){
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }
}