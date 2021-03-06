package com.example.azra.evacusafe_ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import android.hardware.SensorEventListener;
import android.widget.ToggleButton;

import static android.hardware.Sensor.TYPE_STEP_DETECTOR;

public class ScrollingActivity_user extends AppCompatActivity implements SensorEventListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private SensorManager senSensorManager;
    private SensorManager senSensorManager1;
    private Sensor senAccelerometer;
    private Sensor senPressure;
    private float newposition_x;
    private float newposition_y;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senSensorManager1 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senPressure = senSensorManager1.getDefaultSensor(Sensor.TYPE_PRESSURE);

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager1.registerListener(this, senPressure, SensorManager.SENSOR_DELAY_NORMAL);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            TextView editText4 = (TextView)findViewById(R.id.editText4);
            editText4.setText("lat :" + String.valueOf(mLastLocation.getLatitude()));
            TextView editText5 = (TextView)findViewById(R.id.editText5);
            editText5.setText("long :" + String.valueOf(mLastLocation.getLongitude()));

        }
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

            if (mySensor.getType() == Sensor.TYPE_PRESSURE) {
                float x = sensorEvent.values[0];
                TextView editText6 = (TextView) findViewById(R.id.editText6);
                editText6.setText("pressure :" + Float.toString(x));
                /*float y = sensorEvent.values[1];
                TextView editText2 = (TextView) findViewById(R.id.editText2);
                editText2.setText("y :" + Float.toString(y));
                float z = sensorEvent.values[2];
                TextView editText3 = (TextView) findViewById(R.id.editText3);
                editText3.setText("z :" + Float.toString(z));*/
            }

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            TextView editText1 = (TextView)findViewById(R.id.editText1);
            editText1.setText("x :" + Float.toString(x));
            float y = sensorEvent.values[1];
            TextView editText2 = (TextView)findViewById(R.id.editText2);
            editText2.setText("y :" + Float.toString(y));
            //float z = sensorEvent.values[2]; //z is gravity
            //TextView editText3 = (TextView)findViewById(R.id.editText3);
            //editText3.setText("z :" + Float.toString(z));

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                //float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (x > 1 || y > 1 || x < -1 || y < -1) {
                    newposition_x += x;
                    newposition_y += y;
                    TextView editText3 = (TextView)findViewById(R.id.editText3);
                    editText3.setText("coor :" + Float.toString(newposition_x) + ", " + Float.toString(newposition_y));
                }

                last_x = x;
                last_y = y;
                //last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
        senSensorManager1.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager1.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            Intent intent = new Intent(this, MapsActivityuser.class);
            startActivity(intent);
        }
    }
}