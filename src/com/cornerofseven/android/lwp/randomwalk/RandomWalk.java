package com.cornerofseven.android.lwp.randomwalk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;

import java.util.Random;

public class RandomWalk extends Thread{
    private static final int WAITTIME = 1000/15;
    private static final int RADIUS = 10;
    private static final int SHIFT = 24;
    private SurfaceHolder mHolder;
    private Random rand = new Random();
    int width;
    int height;
    int xpos;
    int ypos;
    Paint dotPaint = new Paint();
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN,
            Color.CYAN, Color.MAGENTA, Color.YELLOW};

    public RandomWalk(SurfaceHolder holder, Context context){
        mHolder = holder;
        Rect size = holder.getSurfaceFrame();
        setSize(size.width(), size.height());
        dotPaint.setColor(colors[0]);
        dotPaint.setStyle(Style.FILL);
        startWalk();
    }

    public void run(){
        try{
            Canvas can = mHolder.lockCanvas();
            can.drawCircle(xpos, ypos, RADIUS, dotPaint);
            switch(rand.nextInt(4)){
                case 0: //NORTH
                    ypos -= SHIFT;
                    break;
                case 1: 
                    xpos += SHIFT;
                    break;
                case 2:
                    ypos += SHIFT;
                    break;
                case 3:
                    xpos -= SHIFT;
                    break;
            }
            dotPaint.setColor(colors[rand.nextInt(6)]);
            can.drawCircle(xpos,ypos,RADIUS,dotPaint);
            mHolder.unlockCanvasAndPost(can);
            wait(WAITTIME);
        } catch(NullPointerException nex){
            //doNothing.
        } catch(InterruptedException iex){
            //AlsoDoNothing.
        }
    }

    public void startWalk(){
        start();
    }
    public void pauseWalk(){
        stopWalk();
    }
    public void resumeWalk(){
        startWalk();
    }
    public void stopWalk(){
    
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        xpos = width/2;
        ypos = height/2;
    }
}
