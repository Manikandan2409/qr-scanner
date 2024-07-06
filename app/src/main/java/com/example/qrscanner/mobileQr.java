package com.example.qrscanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qrscanner.databinding.ActivityGenerateQrBinding;
import com.example.qrscanner.databinding.ActivityGeneratedQrBinding;
import com.example.qrscanner.databinding.ActivityMainBinding;
import com.example.qrscanner.databinding.ActivityMobileQrBinding;
import com.example.qrscanner.databinding.FragmentBottomSheetBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class mobileQr extends AppCompatActivity {
        ActivityMobileQrBinding binding;
        FragmentBottomSheetBinding fragmentbinding;
        ActivityGeneratedQrBinding generatedQr;
        Bitmap qrcode;
        EditText mobilenumber;
        String mobile;
        ImageView qr;
        LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_qr);
        initBinding();
    }
    private void initBinding(){

         binding = ActivityMobileQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
   binding.generate.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
//           mobile = binding.mobilenumber.getText().toString();
//           Toast.makeText(mobileQr.this, "Entered Number :"+ mobile, Toast.LENGTH_SHORT).show();
//           Intent i = new Intent(mobileQr.this, generatedQr.class);
//           i.putExtra("QR_CODE_NUMBER",mobile);
//           startActivity(i);
//       }
//   });
           generateQr();
       }
   });
   binding.download.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           captureAndSaveLayout();
       }
   });
    }

    public  void generateQr(){
        mobile = binding.mobilenumber.getText().toString();
       // Toast.makeText(this, "Value: "+ mobile, Toast.LENGTH_SHORT).show();
         qrcode = qrGenerator(mobile);
         qr = binding.generatedQR;
         qr.setImageBitmap(qrcode);
         binding.qrInfo.setText(binding.qrInfo.getText().toString() +binding.user.getText().toString());

    }
    private  Bitmap  qrGenerator(String mobile){
        try {
            BarcodeEncoder  be = new BarcodeEncoder();
          return  be.encodeBitmap(String.valueOf(Uri.parse("tel:"+mobile)), BarcodeFormat.QR_CODE, 400, 400);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private void captureAndSaveLayout() {
        Bitmap layoutBitmap = captureLayoutAsBitmap(binding.downloadLayout);
        if (layoutBitmap != null) {
            saveBitmap(layoutBitmap);
        }
    }
    private void saveBitmap(Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "layout_image.png");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }
    private Bitmap captureLayoutAsBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

}