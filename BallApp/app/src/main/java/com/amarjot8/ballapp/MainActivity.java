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

    DrawingView dv;

    //Circle Cords
    int Circle_x = 50; int Circle_y = 50;


    //sensor's values will be sored
    float sensor_x = 0, sensor_y = 0, sensor_z = 0;
    //Speed
    int speed_x, speed_y = 0;
    int counter = 0;

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

        //    System.out.println("sx : " + sensor_x + " sy : " + sensor_y + " sz : " + sensor_z );
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

        //Circle Size
        int Circle_radius = 50;

        public PointerPoint primary = null;
        List<PointerPoint> pointers = new ArrayList<>();

        public DrawingView(Context c)
        {
            super(c);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            //Checking which type of touch was made
            switch(event.getActionMasked())
            {
                //User Puts finger down
                case MotionEvent.ACTION_DOWN:
                    primary = new PointerPoint((int) event.getX(), (int) event.getY(), -1);
                    break;
                //Moving finger across screen
                case MotionEvent.ACTION_MOVE:
                    break;
                //User lifts finger
                case MotionEvent.ACTION_UP:
                    primary = null;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case  MotionEvent.ACTION_POINTER_DOWN:
                    break;
            }

            return true;
        }

        @Override
        protected void onDraw(Canvas c)
        {

            if(primary != null)
            {
                DrawCircleAndText(c, primary.x, primary.y, Circle_radius);

                for(PointerPoint pp : pointers)
                {
                    DrawCircleAndText(c, pp.x, pp.y,Circle_radius);
                }
            }
            invalidate();
        }

    }

    protected void DrawCircleAndText(Canvas c,int x, int y , int radius)
    {
        Paint p = new Paint();
        p.setColor(Color.GREEN);

        c.drawCircle(x,y,radius, p);


        p.setColor(Color.BLUE);
        p.setTextSize(55);
        c.drawText("Counter :"+counter,10,100, p);
        counter++;
    }

    protected boolean IsFingerOnBall(int EventX, int EventY, int BallX, int BallY)
    {
        boolean result = false;


        System.out.println(result);
        //System.out.println("Ball X" + BallX + " Ball Y " +  BallY + " Event X " + EventX + " EventY " + EventY + "  RESULT " + result);
        return result;
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
