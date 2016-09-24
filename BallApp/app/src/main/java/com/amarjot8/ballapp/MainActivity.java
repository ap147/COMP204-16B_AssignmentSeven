package com.amarjot8.ballapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
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
    int Rectangle_x, Rectangle_y = 0;

    //Rectangles Size
    final int rwidth = 100;
    final int rhieght = 100;
    //sensor's values will be sored
    float sensor_x = 0, sensor_y = 0, sensor_z = 0;
    //Speed
    int speed_x, speed_y = 0;
    int counter = 0;

    //Used to store Sesnor/ Register listner / Unregister listner
    SensorManager sMang;
    Sensor acc;

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor s = event.sensor;
        //checking sensor that triggered this method is Accelerometer
        if (s.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Extracting information from sensor
            sensor_x = event.values[0];
            sensor_y = event.values[1];
            sensor_z = event.values[2];

            System.out.println("sx : " + sensor_x + " sy : " + sensor_y + " sz : " + sensor_z );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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

            c.drawRect(Rectangle_x, Rectangle_y, Rectangle_x + rwidth, Rectangle_y + rhieght, p);

            speed_x = (int) sensor_x;
            speed_y =(int) sensor_y;

            Rectangle_x += speed_x;
            Rectangle_y += speed_y;

            //When the ball hits the corner of the device, change direction
            if (Rectangle_x < 0) { Rectangle_x = 0; speed_x = 0; }
            if (Rectangle_x + rwidth > c.getWidth()) { Rectangle_x = c.getWidth() - rwidth; speed_x = 0; }
            if (Rectangle_y < 0) { Rectangle_y = 0; speed_y = 0; }
            if (Rectangle_y + rhieght > c.getHeight()) { Rectangle_y = c.getHeight() - rhieght; speed_y = 0; }

            b.setTextSize(30);
            c.drawText("Counter: " + counter, 10, 30, b);
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
        setContentView(dv);
        //Making the application Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //locked in portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Registering Listener
        sMang.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregistering listner
        sMang.unregisterListener(this);
    }

}
