package com.example.qrscanner;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.qrscanner.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.qrscanner.databinding.ActivityGenerateQrBinding;

public class generateQr extends AppCompatActivity {

    private  ActivityGenerateQrBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FloatingActionButton qr = findViewById(R.id.menu);
//        qr.setOnClickListener(new  View.OnClickListener(){
//            public  void  onClick(View view){
//                Intent intent = new Intent(generateQr.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });

        initbinding();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_generate_qr);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    public   void  initbinding(){
        binding =ActivityGenerateQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


                binding.fab.setOnClickListener(view ->  {
            Intent intent = new Intent(generateQr.this, MainActivity.class);
            startActivity(intent);
        });
        binding.mobile.setOnClickListener(view -> {
            Intent i = new Intent(generateQr.this, mobileQr.class);
            startActivity(i);
        });

    }
//    public void onGenerateQrClick(View view) {
//        // Handle the click event for generating QR code TextView
//        // Navigate to FragmentMobile
//        fragmentMobile = new FragmentMobile();
//        getSupportFragmentManager().beginTransaction()
//                .replace(binding.frame.getId(), fragmentMobile.getParentFragment()) // Replace with your fragment container ID
//                .addToBackStack(null)
//                .commit();
//    }


}