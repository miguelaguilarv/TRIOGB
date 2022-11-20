package com.vocesdelolimpo.triogb.Breakout;

import android.graphics.RectF;

import com.vocesdelolimpo.triogb.R;

import java.util.Random;

public class Fire {

    private RectF rect;
    private float yVelocity;
    private float fireWidth = 30;
    private float fireHeight = 30;


    public Fire( int screenX, int screenY) {

        float x = screenX;
        float y = screenY - 1500;

        yVelocity = -400;

        rect = new RectF(x, y , x + fireWidth, y + fireHeight);
    }
    RectF getRect(){
        return rect;
    }

    void update(long fps){

        rect.top = rect.top - (yVelocity / fps);
        rect.bottom = rect.top + fireHeight;
    }


    void clearObstacleY(float y){
        rect.bottom = y;
        rect.top = y - fireHeight;
    }


    void reset (int x, int y){
        rect.left = x / 2 + 55;
        rect.top = y - 150;
        rect.right = x / 2 + fireWidth + 60;
        rect.bottom = y - 150 - fireHeight;
    }
}

