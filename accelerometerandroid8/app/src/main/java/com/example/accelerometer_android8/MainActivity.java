package com.example.accelerometer_android8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.hardware.SensorEventListener;
import android.os.Bundle;

// Imports needed for this specific app:
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

// Got the idea to make the main activity also the sensor event listener from here:
//      https://www.youtube.com/watch?v=pkT7DU1Yo9Q
public class MainActivity extends Activity implements SensorEventListener {
    // Create our views/variables
    private TextView xvalue;
    private TextView yvalue;
    private TextView zvalue;
    private Button startButton;
    public boolean started = false;


    /**
     * Anything in this block is executed when the code starts
     * @param savedInstanceState is what is passed to our function, contains variables that we saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            started = (boolean) savedInstanceState.get("started");
        }
        Log.d("starting_up", String.valueOf(started));


        // My Accel Sensor Code
        // https://developer.android.com/reference/android/widget/TextView
        // https://developer.android.com/reference/android/hardware/SensorManager
        xvalue = (TextView) findViewById(R.id.XVALUE);
        yvalue = (TextView) findViewById(R.id.YVALUE);
        zvalue = (TextView) findViewById(R.id.ZVALUE);
        SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); // This is kinda like the "mom" class, it allows us to access sensor related settings
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // This is our Accelerometer Sensor, the SensorManager assigns it
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL); // Our SensorManager then registers our class (MainActivity) as the listener!


        // Button code - https://developer.android.com/reference/android/widget/Button
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // https://stackoverflow.com/questions/11169360/android-remove-button-dynamically
                ViewGroup layout = (ViewGroup) startButton.getParent(); // Finds the current view group object button is in
                layout.removeView(startButton); // Removes the buttons
                started = true;
            }
        });
        // If our button has been pressed, remove it bc we don't need it anymore!
        if (started == true){
            ViewGroup layout = (ViewGroup) startButton.getParent(); // Finds the current view group object button is in
            layout.removeView(startButton); // Removes the buttons
        }
    }



    @Override
    /**
     * When ever there is a change in values, this function gets called by system!
     *
     */
    public void onSensorChanged(SensorEvent event) {
        if (started) {
            xvalue.setText(String.valueOf("X = " + (event.values[0]) + " m/s^2"));
            yvalue.setText(String.valueOf("Y = " + (event.values[1]) + " m/s^2"));
            zvalue.setText(String.valueOf("Z = " + (event.values[2]) + " m/s^2"));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // We don't really care about this!
    }

    /**
     * This is called when our app needs to save before exiting or changing orientations
     * We are using this to make sure our variable is saved when rotating
     * @param outState
     * From here: https://www.youtube.com/watch?v=TcTgbVudLyQ
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("started", started);
    }
}

