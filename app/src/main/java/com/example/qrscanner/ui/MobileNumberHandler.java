package com.example.qrscanner.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MobileNumberHandler  {
    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 123;
    public  static void checkAndCallPhoneNumber(Activity mainActivity,String mobileNumber) {
        // Permission granted, initiate the call
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            initiateCall(mainActivity, mobileNumber);
        else {
            // Request CALL_PHONE permission
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
        }
    }
    public static  void initiateCall(Activity activity, String mobileNumber) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // The app has the CALL_PHONE permission, initiate the call
            Intent intent = new Intent(Intent.ACTION_CALL);
            // intent.setData(Uri.parse("tel:" + phoneNumber));
            intent.setData(Uri.parse(mobileNumber));
            activity.startActivity(intent);
        } else {
            // Request CALL_PHONE permission
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
        }
    }

}
