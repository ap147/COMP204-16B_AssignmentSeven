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

import java.util.ArrayList;
import java.util.List;

//http://stackoverflow.com/questions/26543268/android-making-an-app-fullscreen
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Circle Size
    int Circle_radius = 50;
    //Circle Cords
    int Circle_x = 50; int Circle_y = 50;
    //Speed Ball travel at
    int speed_x, speed_y = 0;

    //sensor's values will be sored
    float sensor_x = 0, sensor_y = 0, sensor_z = 0;

    //The amount of time a circle is drawed & gets displayed on screen top left
    int counter = 0;

    //Used to decide how to draw ball
    boolean FingerDownOnBall = false;

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
        public class PointerPoint
        {
            public int x,y, index;
            public PointerPoint(int x, int y, int index)
            {
                this.x = x;
                this.y = y;
                this.index = index;
            }
        }


        public DrawingView(Context c)
        {
            super(c);
        }
        //Evertime the screen is touched
        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            //Checking which type of touch was made
            switch(event.getActionMasked())
            {
                //User Puts finger down
                case MotionEvent.ACTION_DOWN:
                    //Check if finger is on ball
                    IsFingerOnBall((int) event.getX(),(int) event.getY());
                    break;
                //Moving finger across screen
                case MotionEvent.ACTION_MOVE:
                    //While the finger is on Ball Keep updating Balls cordinates
                    if(FingerDownOnBall)
                    {
                        Circle_x = (int) event.getX();
                        Circle_y = (int) event.getY();
                    }
                    break;
                //User lifts finger
                case MotionEvent.ACTION_UP:
                    FingerDownOnBall = false;
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas c)
        {
            //Draws the ball
            DrawCircleAndText(c, Circle_x, Circle_y, Circle_radius);
            //If ball isnt picked up then let gravity sensor act on it
            if(!FingerDownOnBall)
            {
                speed_x -= sensor_x;
                speed_y += sensor_y;

                Circle_x += speed_x;
                Circle_y += speed_y;

                //When ball is going outside the phone, it stops it.
                if (Circle_x - Circle_radius < 0) { Circle_x = 0 + Circle_radius; speed_x = 0; }
                if (Circle_x + Circle_radius > c.getWidth()) { Circle_x = c.getWidth() - Circle_radius; speed_x = 0; }
                if (Circle_y  - Circle_radius< 0) { Circle_y = 0 + Circle_radius; speed_y = 0; }
                if (Circle_y + Circle_radius > c.getHeight()) { Circle_y = c.getHeight() - Circle_radius; speed_y = 0; }
            }
            //Redraw
            invalidate();
        }

    }
    //Draws ball Green, if ball finger is on ball then draws it in Red and increases size
    protected void DrawCircleAndText(Canvas c,int x, int y , int radius)
    {
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        //Draw Reg Big ball
        if(FingerDownOnBall)
        {
            p.setColor(Color.RED);
            c.drawCircle(x,y,75, p);
        }
        //Draw Default ball
        else
        {
            c.drawCircle(x,y,radius, p);
        }
        //Drawing counter
        p.setColor(Color.BLUE);
        p.setTextSize(55);
        c.drawText("Counter :"+counter,10,100, p);
        counter++;
    }

    //uses correct offsets and checks if cordinates given are touching the ball
    protected boolean IsFingerOnBall(int EventX, int EventY)
    {
        //If finger is on ball
        if((EventX <= (Circle_x + 50) && (EventX > Circle_x - 50)) && (EventY <= (Circle_y + 50) && (EventY > Circle_y - 50)))
        {
            FingerDownOnBall = true;
            return true;
        }
        else{
            FingerDownOnBall = false;
            return false;
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
