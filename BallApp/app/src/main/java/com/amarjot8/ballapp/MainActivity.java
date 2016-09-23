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

    public class DrawingView extends View{
        public DrawingView(Context c){
            super(c);
        }

        @Override
        protected void onDraw(Canvas c)
        {
            Paint p = new Paint();
            p.setColor(Color.RED);
            c.drawRect(rx, ry, 100, 100, p);
            rx++; ry++;
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
