package com.example.gpstrackking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int PREMISSION_CODE = 101;
    TextView locationText;
    Button getLocation;
    String[] permissions_all = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    LocationManager locationManager;
    Location loc;
    boolean isGpsLocation;
    boolean isNetworkLocation;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog= new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Fetching location...");

        locationText = findViewById(R.id.location);
        getLocation = findViewById(R.id.getlocation);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
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
        //
        locationManager = (LocationManager)getSystemService(Service.LOCATION_SERVICE);
        isGpsLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkLocation = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isGpsLocation && !isNetworkLocation){
            showSettingForLocation();
            getLastLocation();
        }
        else {
            getFinalLocation();
        }
    }

    private void getLastLocation() {
        if(locationManager!=null){
            try {
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria,false);
                Location location = locationManager.getLastKnownLocation(provider);
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PREMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getFinalLocation();
                }
                else {
                    Toast.makeText(this, "Premission Failed", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void getFinalLocation() {

        try {
            if(isGpsLocation){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000*60,10,MainActivity.this);
                if(locationManager!=null){
                    loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(loc!=null){
                        updateUi(loc);
                    }
                }
            }
            else if(isNetworkLocation){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000*60,10,MainActivity.this);
                if(locationManager!=null){
                    loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(loc!=null){
                        updateUi(loc);
                    }
                }
            }
            else {
                Toast.makeText(this,"Can't get Location",Toast.LENGTH_SHORT).show();
            }

        }catch (SecurityException e) {
            Toast.makeText(this,"Can't get Location",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUi(Location loc) {
        progressDialog.dismiss();
        if(loc.getLatitude()==0 && loc.getLongitude()==0){
            getDeviceLocation();
        }
        else {
            progressDialog.dismiss();
            locationText.setText("Location : "+loc.getLatitude()+" , "+loc.getLongitude());
        }
    }

    private void showSettingForLocation() {
        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
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
        });
        al.show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        updateUi(location);
    }
    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}