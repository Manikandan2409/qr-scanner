package com.example.qrscanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.qrscanner.databinding.ActivityGeneratedQrBinding;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class generatedQr extends AppCompatActivity {

    Bitmap qrcodebitmap;
    Bitmap b1;
    ActivityGeneratedQrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGeneratedQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String mobile = getIntent().getStringExtra("QR_CODE_NUMBER");
        Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
        showAlertBox();
        qrcodebitmap = qrGenerator(mobile);
        //ImageView img = binding.qr;
        // img.setImageBitmap(qrcodebitmap);

        //b1 = BitmapFactory.decodeResource(getResources(), R.drawable.sc);

        //img.setImageBitmap(b1);
    }

    private Bitmap qrGenerator(String mobile) {
        try {
            BarcodeEncoder be = new BarcodeEncoder();
            Bitmap bitmap = be.encodeBitmap(String.valueOf(Uri.parse("tel:" + mobile)), BarcodeFormat.QR_CODE, 700, 700);
            Toast.makeText(this, "Qr created !!", Toast.LENGTH_SHORT).show();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAlertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog");
        LayoutInflater inflater = getLayoutInflater();

        // Set positive button and its click listener
        builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes, perform some action
                // For example, you can close the dialog or navigate to another screen

                shareImage(qrcodebitmap);
                //dialog.dismiss();
            }
        });

        // Set negative button and its click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked No, perform some action or simply close the dialog
                //  shareQRCode();
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


//    private void shareImage(Bitmap bitmap) {
//        // Save the bitmap to a temporary file (you can use your own logic for this)
//        File imagePath = new File(getCacheDir(), "images");
//
//        try {
//            if (!imagePath.exists()) {
//                imagePath.mkdirs();
//            }
////            File imageFile = new File(imagePath, "shared_image.png");
////            FileOutputStream fos = new FileOutputStream(imageFile);
////            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
////            fos.flush();
////            fos.close();
//
//            // Create an Intent to share the image
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("image/*");
//           // Log.d();
//            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", R.drawable.sc);
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//            startActivity(Intent.createChooser(shareIntent, "Share Image"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


    private void shareImage(Bitmap bitmap) {
        // Save the bitmap to a temporary file
        File imagePath = new File(getCacheDir(), "images");
        File imageFile = new File(imagePath, imageName()+".png");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create an Intent to share the image
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");

        // Use FileProvider to get a content URI for the file
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", imageFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

        // Grant read permissions to the receiving app
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start the activity to choose an app for sharing
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }
    private  String imageName(){
    String time= null;
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }

        // Define the format you want
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        }

        // Format the date and time into a string without any special characters
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        time = now.format(formatter);
        }
        return time;
    }
}

//    private void shareQRCode() {
//        String mobile = getIntent().getStringExtra("QR_CODE_BITMAP");
//        Bitmap qrcodebitmap = qrGenerator(mobile); // Replace with your actual data
//
//        try {
//            File cachePath = new File(getCacheDir(), "images");
//            cachePath.mkdirs(); // don't forget to make the directory
//            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
//            qrcodebitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            stream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        File imagePath = new File(getCacheDir(), "images");
//        File newFile = new File(imagePath, "image.png");
//        Uri contentUri = FileProvider.getUriForFile(this, "com.example.qrscanner.fileprovider", newFile);
//
//        if (contentUri != null) {
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
//            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
//            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
//        }
//    }

