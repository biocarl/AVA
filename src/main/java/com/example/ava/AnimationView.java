package com.example.ava;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;


/**
 * Created by carlh on 05.12.2017.
 */

public class AnimationView extends android.support.v7.widget.AppCompatImageView {

    //Vars
    public AnimationView view;
    private ArrayList<Drawable> frames;
    private boolean isRunning = false;
    private boolean repeat = false;
    private int currentFrame = 0;
    private int delta = 200; //ms per frame


    public void setDelta(int delta) {
        this.delta = delta;
    }


    public AnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.view = this;
    }

    //Getter&Setter
    public ArrayList<Drawable> getFrames() {
        return frames;
    }
    public void setFrames(ArrayList<Drawable> frames) {
        this.frames = frames;
    }

    /*
    icon_1.png
    counter="1"
    front = "icon_"
    back= ".png"
     */
    public void setFrames(String front,String back,int start,Context context) {
        this.frames = new ArrayList<>();

        int counter = start;
        String identifierString = front+counter+back;
        int id = context.getResources().getIdentifier(identifierString, "drawable",context.getPackageName());
        while (id !=0){
            frames.add(context.getResources().getDrawable(id));
            //next loop
            counter++;
            identifierString = front+counter+back;
            id = context.getResources().getIdentifier(identifierString, "drawable",context.getPackageName());
        }
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    /**
     * Start frame anination
     */
    public void start(){
        if(!isRunning) {
            this.isRunning = true;
            run();
        }
    }

    //Internal start method
    private void run() {

        final int _runtime = (frames.size()+1) * delta; //onTick doesn't account for last frame

         CountDownTimer timer = new CountDownTimer(_runtime, delta) {
            public void onTick(long millisUntilFinished) {
                Log.e(getClass().getName(), "[tick]current: "+currentFrame, null);
                if(currentFrame < frames.size()) {
                    view.setImageDrawable(frames.get(currentFrame));
                    currentFrame++;
                }
            }
            public void onFinish() {
                if(repeat && isRunning) {
                    Log.e(getClass().getName(), "[finish]current: " + currentFrame, null);
                    Log.e(getClass().getName(), "[finish]size: " + frames.size(), null);
                    currentFrame = 0;
                    run();
                }
            }


        }.start();
    }

    public void stop(){
        this.isRunning = false;
    }

}
