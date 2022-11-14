package com.vocesdelolimpo.triogb.Breakout;

import android.graphics.RectF;

import java.util.Random;

public class Ball {

    private RectF rect;
    private float xVelocity;
    private float yVelocity;
    private float ballWidth = 10;
    private float ballHeight = 10;

    Ball(int screenX, int screenY){

        float x = screenX / 2 - 150;
        float y = screenY - 150;


        xVelocity = 200;
        yVelocity = -400;
        rect = new RectF(x, y , x + ballWidth, y + ballHeight);

    }

    RectF getRect(){
        return rect;
    }

    void update(long fps){
        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
    }

    void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    void reverseXVelocity(){
        xVelocity = -xVelocity;
    }

    public void setXVelocity(float paddleMiddle, float ballMiddle){
        if((xVelocity > 0 && ballMiddle < paddleMiddle) || (xVelocity < 0 && ballMiddle > paddleMiddle)){
            reverseXVelocity();
        }
    }

//    void setRandomXVelocity(){
//        Random generator = new Random();
//        int answer = generator.nextInt(4);
//
//        if(answer == 0){
//            reverseXVelocity();
//        }
//    }


    void clearObstacleY(float y){
        rect.bottom = y;
        rect.top = y - ballHeight;
    }

    void clearObstacleX(float x){
        rect.left = x;
        rect.right = x + ballWidth;
    }

    void reset (int x, int y){
        rect.left = x / 2 + 80;
        rect.top = y - 150;
        rect.right = x / 2 + ballWidth + 60;
        rect.bottom = y - 150 - ballHeight;
    }

}
