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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

//http://stackoverflow.com/questions/26543268/android-making-an-app-fullscreen
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    DrawingView dv;

    //Circle Cords
    int Circle_x, Circle_y = 0;
    //Circle Size
    int Circle_radius = 50;

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
        boolean DrawCircle = false;
        int x, y;
        public DrawingView(Context c)
        {
            super(c);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            switch(event.getActionMasked())
            {
                case MotionEvent.ACTION_DOWN:
                    x = (int)event.getX();
                    y = (int)event.getY();
                    DrawCircle = true;
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:
                    DrawCircle = false;
                    break;
            }

            return true;
        }

        @Override
        protected void onDraw(Canvas c)
        {
            if(DrawCircle)
            DrawCircleAndText(c,x,y);
        }

    }

    protected void DrawCircleAndText(Canvas c,int x, int y )
    {
        Paint p = new Paint(Color.RED);
        p.setStrokeWidth(1);

        c.drawCircle(x,y,Circle_radius, p);


        p.setColor(Color.BLUE);
        p.setTextSize(30);
        c.drawText("Counter :"+counter,10,100, p);
        counter++;
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
