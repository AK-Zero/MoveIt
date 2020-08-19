package com.example.moveit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    Paint p1,mborder;
    float x,y,lx,ly;
    int stat=1,eng=0;
    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(@Nullable AttributeSet attrs){
        p1 = new Paint();
        p1.setAntiAlias(true);
        p1.setColor(Color.RED);
        mborder = new Paint();
        mborder.setAntiAlias(true);
        mborder.setColor(Color.BLACK);
        mborder.setStrokeWidth(40);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(stat==1) {
            x = (float) getWidth() / 3;
            y = (float) getHeight() / 3;
            lx = ly = 400;
            stat++;
        }
        canvas.drawLine(0,0,0,getHeight(),mborder);
        canvas.drawLine(0,0,getWidth(),0,mborder);
        canvas.drawLine(getWidth(),getHeight(),0,getHeight(),mborder);
        canvas.drawLine(getWidth(),0,getWidth(),getHeight(),mborder);

        canvas.drawRect(x,y,x+lx,y+ly,p1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value =  super.onTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN : {
                return true;
            }
            case MotionEvent.ACTION_MOVE : {
                float xx = event.getX();
                float yy = event.getY();

                double d1 = Math.sqrt(Math.pow(xx-x,2)+Math.pow(yy-y,2));
                double d2 = Math.sqrt(Math.pow(xx-x-lx,2)+Math.pow(yy-y,2));
                double d3 = Math.sqrt(Math.pow(xx-x,2)+Math.pow(yy-y-ly,2));
                double d4 = Math.sqrt(Math.pow(xx-x-lx,2)+Math.pow(yy-y-ly,2));

                if(xx>x && xx<x+lx && yy > y && yy<y+ly && eng==0 && d1>60 && d2>60 && d3>60 && d4>60) {
                    x=xx-lx/2;
                    y=yy-ly/2;
                    postInvalidate();
                }


                if(d1<60 && eng==0){
                    eng=1;
                }
                else if(d2<60 && eng==0){
                    eng=2;
                }
                else if(d3<60 && eng==0){
                    eng=3;
                }
                else if(d4<60 && eng==0){
                    eng=4;
                }
                return value;
            }
            case MotionEvent.ACTION_UP : {

                float xx = event.getX();
                float yy = event.getY();
                if(eng==1) {

                    if(xx<x){
                        lx+=x-xx;
                    }
                    else {
                        lx-=xx-x;
                    }
                    if(yy<y){
                        ly+=y-yy;
                    }
                    else {
                        ly-=yy-y;
                    }
                    x=xx;
                    y=yy;
                    eng=0;
                }
                else if(eng==2) {

                    if(yy<y){
                        ly+=y-yy;
                    }
                    else {
                        ly-=yy-y;
                    }
                    lx=xx-x;
                    y=yy;
                    eng=0;
                }
                else if(eng==3) {

                    if(xx<x){
                        lx+=x-xx;
                    }
                    else {
                        lx-=xx-x;
                    }
                    x=xx;
                    ly=yy-y;
                    eng=0;
                }
                else if(eng==4) {
                    lx=xx-x;
                    ly=yy-y;
                    eng=0;
                }
                postInvalidate();
                return value;
            }

        }
        return value;
    }
}
