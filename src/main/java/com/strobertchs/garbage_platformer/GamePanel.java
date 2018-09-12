package com.strobertchs.garbage_platformer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Darius on 11/30/2016.
 */


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    //screen resolution - subject to change depending on gilberts design for the background image
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;

    private MainThread thread;
    private Background bg;
    public GamePanel(Context context){
        super(context);

        // get returned parameters via callback from surfaceHolder
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamepanel focusable to handle events
        setFocusable(true);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();


            }

            catch(InterruptedException e){e.printStackTrace();}
            retry = false;

        }
    }

    @Override
    public  void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        bg.setVector(-5);
        //gameloop can be started after surface created
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return super.onTouchEvent(event);
    }

    public void update(){

        bg.update();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas){

        if(canvas != null) {
            bg.draw(canvas);
        }
    }
}
