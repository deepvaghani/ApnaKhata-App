package com.example.apnakhata;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;

public class History_Activity extends AppCompatActivity {

    TextInputLayout invoiceNo;
    Button ViewPdf,SharePdf;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listView = findViewById(R.id.listview);
        invoiceNo = findViewById(R.id.invoice_no);
        ViewPdf = findViewById(R.id.viewpdf);
        SharePdf = findViewById(R.id.sharepdf);

        ViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                viewPDF();
            }
        });

        SharePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                sharePDF();
            }
        });

        try {

            String filePath = getExternalFilesDir(null).getAbsolutePath()+ "/";

            File notes = new File(filePath);
            ArrayList<String> lines = new ArrayList<>();
            for (File file : notes.listFiles()) {
                lines.add(file.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lines);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void viewPDF(){
        File file = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+invoiceNo.getEditText().getText().toString()+".pdf");
        if(file.exists()){
            File file1 = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+invoiceNo.getEditText().getText().toString()+".pdf");
            Intent view = new Intent(Intent.ACTION_VIEW);
            view.setDataAndType(Uri.fromFile(file1), "application/pdf");
            view.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(view);
        }
        else{
            Toast.makeText(History_Activity.this,"File does no exist!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void sharePDF(){
        File file = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+invoiceNo.getEditText().getText().toString()+".pdf");
        if(file.exists()){
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            share.setType("application/pdf");
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(share);
        }
        else{
            Toast.makeText(History_Activity.this,"File does no exist!!", Toast.LENGTH_SHORT).show();
        }
    }
}