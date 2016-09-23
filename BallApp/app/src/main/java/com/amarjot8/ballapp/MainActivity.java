package com.amarjot8.ballapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
//http://stackoverflow.com/questions/26543268/android-making-an-app-fullscreen
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Rectangles cords
    int rx, ry = 0;
    //Speed
    int dx = 10, dy = 10;
    //Rectangles Size
    final int rwidth = 100;
    final int rhieght = 100;
    //sensor's values will be sored
    float sx = 0, sy = 0, sz = 0;
    int counter =0;

    SensorManager sMang;
    Sensor acc;

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor s = event.sensor;
        //checking sensor that triggered this method is Accelerometer
        if(s.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            //Extracting information from sensor
            sx = event.values[0];
            sy = event.values[1];
            sz = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class DrawingView extends View {
        public DrawingView(Context c) {
            super(c);
        }

        @Override
        protected void onDraw(Canvas c) {
            Paint p = new Paint();
            Paint b = new Paint();
            p.setColor(Color.RED);
            b.setColor(Color.BLUE);
            c.drawRect(rx, ry, rx + rwidth, ry + rhieght, p);
            rx += dx;
            ry += dy;

            //When the ball hits the corner of the device, change direction
            if (rx + rwidth > c.getWidth())
                dx = -dx;
            if (ry + rhieght > c.getHeight())
                dy = -dy;
            if (rx < 0)
                dx = -dx;
            if (ry < 0)
                dy = -dy;

            b.setTextSize(30);
            c.drawText("Counter: "+counter,10,30,b);
            counter++;
            //Redraw when possible
            invalidate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sMang = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sMang.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        DrawingView dv = new DrawingView(this);
        setContentView(dv);//R.layout.activity_main);
        //Making the application Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        sMang.registerListener(this,acc,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sMang.unregisterListener(this);
    }

}
