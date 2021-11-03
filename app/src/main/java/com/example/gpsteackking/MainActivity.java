package com.example.gpsteackking;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationRequest;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static  final  int DEFAULT_UPDATE_INTERVAL = 30;
    public static  final  int FAST_UPDATE_INTERVAL = 5;

    TextView tv_lat, tv_lon, tv_altitude, tv_accuract, tv_speed, tv_sensor, tv_updates, tv_address;
    Switch sw_locationupdate, sw_gps;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean updateOn = false;

    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuract = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        sw_gps = findViewById(R.id.sw_gps);
        sw_locationupdate = findViewById(R.id.sw_locationsupdates);

        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);

        locationRequest.setPriority(LocationRequest.PRIORITY_BALACED_POWER_ACCURACY);
    }
}