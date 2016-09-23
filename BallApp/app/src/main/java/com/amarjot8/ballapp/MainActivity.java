package com.amarjot8.ballapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int rx, ry = 0;
    int dx =10, dy = 10;
    final int rwidth = 100; final int rhieght = 100;
    public class DrawingView extends View{
        public DrawingView(Context c){
            super(c);
        }

        @Override
        protected void onDraw(Canvas c)
        {
            Paint p = new Paint();
            p.setColor(Color.RED);
            c.drawRect(rx, ry, rx + rwidth, ry + rhieght, p);
            rx += dx; ry += dy;

            if(rx + rwidth > c.getWidth())
                dx = -dx;
            if(ry + rhieght > c.getHeight())
                dy = -dy;
            if(rx <0)
                dx = -dx;
            if(ry <0)
                dy = -dy;
            
            invalidate();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawingView dv = new DrawingView(this);
        setContentView(dv);//R.layout.activity_main);
    }
}
