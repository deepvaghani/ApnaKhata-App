package com.example.apnakhata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText loginuser,loginpass;
    Button registerbutton,loginbutton;
    String pass;
    public static  String PREFS_NAME = "MyPrefsFile";

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        reference = FirebaseDatabase.getInstance().getReference("Users");

        registerbutton = (Button) findViewById(R.id.registerbutton);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        loginuser = findViewById(R.id.username);
        loginpass = findViewById(R.id.password);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(login.this,registerpage.class);
                startActivity(intent);
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }
    public void login(View view){
        GlobalVariable.username = loginuser.getText().toString();
        pass = loginpass.getText().toString();
        reference.child(GlobalVariable.username).addListenerForSingleValueEvent(listener);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                GlobalVariable.name = snapshot.child("name").getValue(String.class);
                GlobalVariable.shopname = snapshot.child("shopname").getValue(String.class);
                GlobalVariable.email = snapshot.child("email").getValue(String.class);
                GlobalVariable.phoneno = snapshot.child("phoneno").getValue(String.class);
                GlobalVariable.password = snapshot.child("password").getValue(String.class);
                if(GlobalVariable.password.equals(pass)){

                    Intent intent= new Intent(login.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(login.this,"Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(login.this,"Record Not Found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
}