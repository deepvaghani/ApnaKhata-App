package com.example.apnakhata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {
    EditText loginuser,loginpass;
    Button registerbutton,loginbutton;
    CheckBox checkBox;
    String pass;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String PREF_NAME = "prefs";
    public static final String KEY_REMEMBER = "remember";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASS = "password";

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        reference = FirebaseDatabase.getInstance().getReference("Users");

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        registerbutton = findViewById(R.id.registerbutton);
        loginbutton = findViewById(R.id.loginbutton);
        loginuser = findViewById(R.id.username);
        loginpass = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);

        if(sharedPreferences.getBoolean(KEY_REMEMBER,false))
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        loginuser.setText(sharedPreferences.getString(KEY_USERNAME,""));
        loginpass.setText(sharedPreferences.getString(KEY_PASS,""));

        loginuser.addTextChangedListener(this);
        loginpass.addTextChangedListener(this);
        checkBox.setOnCheckedChangeListener(this);

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }

    private void managePrefs(){
        if(checkBox.isChecked()){
            editor.putString(KEY_USERNAME,loginuser.getText().toString().trim());
            editor.putString(KEY_PASS,loginpass.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER,true);
            editor.apply();
        }
        else{
            editor.remove(KEY_USERNAME);
            editor.remove(KEY_PASS);
            editor.putBoolean(KEY_REMEMBER,false);
            editor.apply();
        }
    }
}