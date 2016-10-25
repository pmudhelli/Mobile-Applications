package com.example.prashanthmudhelli.musiconclap;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;

import static com.example.prashanthmudhelli.musiconclap.R.raw.ambulance;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    TextView proximityView;
    SensorManager sensorManager;
    Sensor proximitySensor;
    MediaPlayer media;
    Uri uri;
    float proxDist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Log.d("PM", "Max Range: " +proximitySensor.getMaximumRange());
        proximityView = (TextView) findViewById(R.id.proximity);

        sensorManager.registerListener(this, proximitySensor, sensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        media = new MediaPlayer();
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ambulance);
        setMedia();
    }

    public void selectSong(View view) {
        int selected = ((RadioButton) view).getId();
        uri = null;

        if(selected == R.id.song2) {
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dhoom);
        }
        else {
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ambulance);
        }

        //media.stop();
        media.reset();
        setMedia();
        songAction();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        proxDist = event.values[0];
        proximityView.setText(String.valueOf(proxDist));
        songAction();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(media !=null){
            if (media.isPlaying()){
                media.stop();
            }
            media.release();
        }
    }

    public void songAction() {
        if(proxDist == proximitySensor.getMaximumRange()) {
            if(media.isPlaying())
                media.pause();
        }
        else {
            media.start();
        }
    }

    public void setMedia() {
        try {
            media.setDataSource(MainActivity.this, uri);
            media.prepare();
            //media.start();
        }
        catch (Exception e) {
            Log.d("PM", e.getMessage());
        }
    }
}