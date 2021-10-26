package com.example.apnakhata;

import android.Manifest;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Billing_Activity extends AppCompatActivity {
    Button generate_pdf,view_button,share_button;
    TextInputLayout customer_name,customer_mobile,item1,item2,item3,item4,item5,qty1,qty2,qty3,qty4,qty5,price1,price2,price3,price4,price5;
    int pagewidth = 1200;
    Date dateObj;
    DateFormat dateFormat;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        generate_pdf = findViewById(R.id.generate_pdf);
        view_button = findViewById(R.id.view_button);
        share_button = findViewById(R.id.share_button);
        customer_name = findViewById((R.id.customer_name));
        customer_mobile = findViewById((R.id.customer_mobile));
        item1 = findViewById((R.id.item1));
        qty1 = findViewById((R.id.qty1));
        price1 = findViewById((R.id.price1));
        item2 = findViewById((R.id.item2));
        qty2 = findViewById((R.id.qty2));
        price2 = findViewById((R.id.price2));
        item3 = findViewById((R.id.item3));
        qty3 = findViewById((R.id.qty3));
        price3 = findViewById((R.id.price3));
        item4 = findViewById((R.id.item4));
        qty4 = findViewById((R.id.qty4));
        price4 = findViewById((R.id.price4));
        item5 = findViewById((R.id.item5));
        qty5 = findViewById((R.id.qty5));
        price5 = findViewById((R.id.price5));

        view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                viewPDF();
            }
        });

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                sharePDF();
            }
        });

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);

        if(Build.VERSION.SDK_INT > 29) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

            }
        }
        createPDF();
    }
    int min=1000;
    int max=2000;
    public void setRandomNum()
    {
        GlobalVariable.random_Num = ThreadLocalRandom.current().nextInt(min, max + 1);;
    }

    private void createPDF(){
        generate_pdf.setOnClickListener((v) -> {

            setRandomNum();
            dateObj = new Date();
            PdfDocument myPDFDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Paint titlePaint = new Paint();

            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
            PdfDocument.Page myPage1 = myPDFDocument.startPage(myPageInfo1);
            Canvas canvas = myPage1.getCanvas();

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            titlePaint.setTextSize(70);
            canvas.drawText(GlobalVariable.shopname,pagewidth/2,270,titlePaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            titlePaint.setTextSize(70);
            canvas.drawText("Invoice",pagewidth/2,500,titlePaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTextSize(35f);
            myPaint.setColor(Color.BLACK);
            canvas.drawText("Customer Name: " + customer_name.getEditText().getText(),20,590,myPaint);
            canvas.drawText("Contact No: " + customer_mobile.getEditText().getText(),20,640,myPaint);

            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Invoice No.: "+GlobalVariable.random_Num,pagewidth-20,590,myPaint);

            dateFormat = new SimpleDateFormat("dd/MM/yy");
            canvas.drawText("Date: " + dateFormat.format(dateObj),pagewidth-20,640,myPaint);

            dateFormat = new SimpleDateFormat("HH:mm:ss");
            canvas.drawText("Time: " + dateFormat.format(dateObj),pagewidth-20,690,myPaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(2);
            canvas.drawRect(20,780,pagewidth-20,860,myPaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setStyle(Paint.Style.FILL);
            canvas.drawText("Si. No.",40,830,myPaint);
            canvas.drawText("Item Description",200,830,myPaint);
            canvas.drawText("Price",700,830,myPaint);
            canvas.drawText("Qty",900,830,myPaint);
            canvas.drawText("Total",1050,830,myPaint);

            canvas.drawLine(180,790,180,840,myPaint);
            canvas.drawLine(680,790,680,840,myPaint);
            canvas.drawLine(880,790,880,840,myPaint);
            canvas.drawLine(1030,790,1030,840,myPaint);

            float total1,price_1,qty_1 =0;
            canvas.drawText("1.",40,950,myPaint);
            canvas.drawText(item1.getEditText().getText().toString(),200,950,myPaint);
            canvas.drawText(price1.getEditText().getText().toString(),700,950,myPaint);
            canvas.drawText(qty1.getEditText().getText().toString(),900,950,myPaint);
            price_1 = Float.parseFloat(price1.getEditText().getText().toString());
            qty_1 = Float.parseFloat(qty1.getEditText().getText().toString());
            total1= price_1*qty_1;
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(total1),pagewidth-40,950,myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);

            float total2,price_2,qty_2 =0;
            canvas.drawText("2.",40,1050,myPaint);
            canvas.drawText(item2.getEditText().getText().toString(),200,1050,myPaint);
            canvas.drawText(price2.getEditText().getText().toString(),700,1050,myPaint);
            canvas.drawText(qty2.getEditText().getText().toString(),900,1050,myPaint);
            price_2 = Float.parseFloat(price2.getEditText().getText().toString());
            qty_2 = Float.parseFloat(qty2.getEditText().getText().toString());
            total2= price_2*qty_2;
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(total2),pagewidth-40,1050,myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);

            float total3,price_3,qty_3 =0;
            canvas.drawText("3.",40,1150,myPaint);
            canvas.drawText(item3.getEditText().getText().toString(),200,1150,myPaint);
            canvas.drawText(price3.getEditText().getText().toString(),700,1150,myPaint);
            canvas.drawText(qty3.getEditText().getText().toString(),900,1150,myPaint);
            price_3 = Float.parseFloat(price3.getEditText().getText().toString());
            qty_3 = Float.parseFloat(qty3.getEditText().getText().toString());
            total3= price_3*qty_3;
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(total3),pagewidth-40,1150,myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);

            float total4,price_4,qty_4 =0;
            canvas.drawText("4.",40,1250,myPaint);
            canvas.drawText(item4.getEditText().getText().toString(),200,1250,myPaint);
            canvas.drawText(price4.getEditText().getText().toString(),700,1250,myPaint);
            canvas.drawText(qty4.getEditText().getText().toString(),900,1250,myPaint);
            price_4 = Float.parseFloat(price4.getEditText().getText().toString());
            qty_4 = Float.parseFloat(qty4.getEditText().getText().toString());
            total4= price_4*qty_4;
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(total4),pagewidth-40,1250,myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);

            float total5,price_5,qty_5 =0;
            canvas.drawText("5.",40,1350,myPaint);
            canvas.drawText(item5.getEditText().getText().toString(),200,1350,myPaint);
            canvas.drawText(price5.getEditText().getText().toString(),700,1350,myPaint);
            canvas.drawText(qty5.getEditText().getText().toString(),900,1350,myPaint);
            price_5 = Float.parseFloat(price5.getEditText().getText().toString());
            qty_5 = Float.parseFloat(qty5.getEditText().getText().toString());
            total5= price_5*qty_5;
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(total5),pagewidth-40,1350,myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);

            float subtotal = total1+total2+total3+total4+total5;
            canvas.drawLine(680,1500,pagewidth-20,1500,myPaint);
            canvas.drawText("Sub Total",700,1550,myPaint);
            canvas.drawText(":",900,1550,myPaint);
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(subtotal),pagewidth-40,1550,myPaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("GST (18%)",700,1600,myPaint);
            canvas.drawText(":",900,1600,myPaint);
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(subtotal*18/100),pagewidth-40,1600,myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);

            myPaint.setColor(Color.rgb(247,147,30));
            canvas.drawRect(680,1650,pagewidth-20,1750,myPaint);

            myPaint.setColor(Color.BLACK);
            myPaint.setTextSize(50f);
            myPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Total",780,1715,myPaint);
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(subtotal+(subtotal*18/100)),pagewidth-40,1715,myPaint);

            myPDFDocument.finishPage(myPage1);

            File file = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+GlobalVariable.random_Num+".pdf");
            try{
                myPDFDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(Billing_Activity.this,"Pdf Saved Successfully", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e){
                e.printStackTrace();
                Toast.makeText(Billing_Activity.this,"Pdf not Saved Successfully", Toast.LENGTH_SHORT).show();
            }

            myPDFDocument.close();
        });
    }

    public void sharePDF(){
        File file = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+GlobalVariable.random_Num+".pdf");
        if(file.exists()){
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            share.setType("application/pdf");
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(share);
        }
    }

    public void viewPDF(){
        File file = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+GlobalVariable.random_Num+".pdf");
        if(file.exists()){
            File file1 = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+GlobalVariable.random_Num+".pdf");
            Intent view = new Intent(Intent.ACTION_VIEW);
            view.setDataAndType(Uri.fromFile(file1), "application/pdf");
            view.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(view);
        }
    }
}