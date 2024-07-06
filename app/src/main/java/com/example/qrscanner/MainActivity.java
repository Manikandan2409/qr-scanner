package com.example.qrscanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.qrscanner.databinding.ActivityMainBinding;
import com.example.qrscanner.ui.MobileNumberHandler;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private  String mobileNumber;
    private  String input;
    // Add this constant to define the permission request code
    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 123;
    private static final int INSTAGRAM_PERMISSION_REQUEST_CODE = 125;
    private static final int FACEBOOK_PERMISSION_REQUEST_CODE = 126;
    private static final int EMAIL_PERMISSION_REQUEST_CODE = 124;
    private static final int PERMISSION_REQUEST_CODE = 123;
    private ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();


             input= result.getContents();
//            if (Patterns.WEB_URL.matcher(input).matches()) {
//                // It's a URL
//                openUrl(input);
//            } else
                if (isPhoneNumber(input)) {
                // It's a phone number
                MobileNumberHandler.checkAndCallPhoneNumber(this,input);
            }
//                else if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
//                // It's an email address
//                sendEmail();
//            }
                else {
                // It's neither a URL, nor a phone number, nor an email
                // Handle accordingly (e.g., show an error message)
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private boolean isPhoneNumber(String input) {
        // You can implement your own logic to check if the input is a valid phone number
        // For simplicity, I'm using a basic check here
        return input.matches("\\d{10}");
    }
    private void openUrl(String url) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permissions are already granted, proceed with the action
            startBrowserIntent(url);
        } else {
            // Request permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void startBrowserIntent(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }


    private void sendEmail() {
        // Create an intent to open the email app
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(input));

        // Check if there's an app that can handle the intent
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            // No email app found
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAndOpenInstagram() {
        if (isAppInstalled("com.instagram.android")) {
            // Instagram is installed, open it
            openInstagram();
        } else {
            // Instagram is not installed, handle accordingly
            Toast.makeText(this, "Instagram app not installed", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkAndOpenFacebook() {
        if (isAppInstalled("com.facebook.katana")) {
            // Facebook is installed, open it
            openFacebook();
        } else {
            // Facebook is not installed, handle accordingly
            Toast.makeText(this, "Facebook app not installed", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void openInstagram() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
        if (intent != null) {
            startActivity(intent);
        } else {
            // Instagram app not found
            Toast.makeText(this, "Instagram app not installed", Toast.LENGTH_SHORT).show();
        }
    }
    private void openFacebook() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
        if (intent != null) {
            startActivity(intent);
        } else {
            // Facebook app not found
            Toast.makeText(this, "Facebook app not installed", Toast.LENGTH_SHORT).show();
        }
    }



    // Add this method to handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initiate the call
                MobileNumberHandler.initiateCall(this,input);
            } else {
                Toast.makeText(this, "CALL_PHONE permission denied", Toast.LENGTH_SHORT).show();
                // Handle the case where the user denied the CALL_PHONE permission
            }
        }
    }




    private void showCamera() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setPrompt("Scan Qr code");
        options.setCameraId(0);
        options.setBeepEnabled(false);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(false);
        qrCodeLauncher.launch(options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
       // initViews();
    }


    private void initBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.menu.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, generateQr.class);
            startActivity(intent);
        });

        binding.fab.setOnClickListener(view -> checkPermissionAndShowActivity(this));
    }

    private void initViews() {
        binding.fab.setOnClickListener(view -> checkPermissionAndShowActivity(this));

    }

    private void checkPermissionAndShowActivity(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(context, "Camera permission required!!", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }
    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            showCamera();
        } else {
            Toast.makeText(this, "Camera permission required!!", Toast.LENGTH_SHORT).show();
            // Handle the case where the user denied the permission
        }
    });

}
