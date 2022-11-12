package com.vocesdelolimpo.triogb;

import android.graphics.RectF;

public class Bat {

    //ReacF es un objeto que mantiene cuatro coordenadas.
    private RectF rect;

    //Que tan grande va ser nuestra plataforma
    private float length;

    //X es el rectangulo que forma nuestra plataforma
    private float x;

    //Guarda los pixeles por segundo que nuestra plataforma se va a mover
    private float paddleSpeed;

    //En que direccion se va a mover la plataforma
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;

    //Si se esta moviendo la plataforma y en que direccion
    private int paddleMoving = STOPPED;

    //Constructor, se crea un objeto desde esta clase y se pasa en la pantalla con una ancho y alto
    Bat(int screenX, int screenY){

        length = 230;
        float height = 20;

        //Se pone la plataforma en el centro de la pantalla al inicio
        x = screenX / 2;

        //Se le resta 150 para que la plataforma se logre ver en la pantalla. sino apareceria muy en fondo de la pantalla
        float y = screenY - 150;

        rect = new RectF(x, y , x + length, y + height);

        //La velocidad de la plataforma en pixeles por segundo
        paddleSpeed = 550;

    }

    //Es un getter para crear el rectangulo de la plataforma disponible en la clase BrealOutView
    RectF getRect(){
        return rect;
    }


    //Este metodo sera usado para cambiar/establecer si la plataforma va a la derecha, izquierda o a ninguna parte.
    void setMovementState(int state){

        paddleMoving = state;
    }

    //Este metodo se llama desde BreakOutEngine en update, determina si la plataforma
    //necesita moverse y cambiar las coordenadas contenidas en rect is es necesario.
    void update(long fps){
        if (paddleMoving == LEFT){

            x = x - paddleSpeed / fps;

        }
        if(paddleMoving == RIGHT){

            x = x + paddleSpeed / fps;

        }

        rect.left = x;
        rect.right = x + length;

    }
    void reset(int screenX){
        x = screenX / 2;
    }
}
