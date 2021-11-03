package com.example.gpstrackking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int PREMISSION_CODE = 101;
    TextView locationText;
    Button getLocation;
    String[] permissions_all = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    LocationManager locationManager;
    boolean isGpsLocation;
    boolean isNetworkLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationText = findViewById(R.id.location);
        getLocation = findViewById(R.id.getlocation);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    private void  getLocation() {
        if (Build.VERSION.SDK_INT >= 23){
            if(checkPermission()){
                getDeviceLocation();
            }
            else {
                reqestPermission();
            }
        }
        else {
            getDeviceLocation();
        }
    }
    private void reqestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,permissions_all,PREMISSION_CODE);
    }

    private boolean checkPermission() {
        for(int i=0; i<permissions_all.length ;i++) {
            int result = ContextCompat.checkSelfPermission(MainActivity.this,permissions_all[i]);
            if(result == PackageManager.PERMISSION_GRANTED) {
                continue;
            }
            else {
                return false;
            }
        }
        return false;
    }

    private void getDeviceLocation() {
        locationManager = (locationManager)getSystemService(Service.LOCATION_SERVICE);
        isGpsLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkLocation = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isGpsLocation && !isNetworkLocation){
            showSettingForLocation();
        }
        else {
            getFinalLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PREMISSION_CODE:
                if
        }
    }

    private void getFinalLocation() {

    }

    private void showSettingForLocation() {
        AlertDialog.Builder al = new AlertDialog.Builder((MainActivity.this);
        al.setTitle("Location Not Enable!");
        al.setMessage("Enable Location ?");
        al.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        al.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
    }

}