package com.vocesdelolimpo.triogb.Breakout;

import android.graphics.RectF;

public class Rect {
    //ReacF es un objeto que mantiene cuatro coordenadas.
    private RectF rect;

    //Que tan grande va ser nuestra plataforma
    private float length;

    //X es el rectangulo que forma nuestra plataforma
    private float x;


    //Constructor, se crea un objeto desde esta clase y se pasa en la pantalla con una ancho y alto
    Rect(int screenX, int screenY){

        length = screenX;
        float height = 100;

        //Se pone la plataforma en el centro de la pantalla al inicio
        x = 0;

        //Se le resta 150 para que la plataforma se logre ver en la pantalla. sino apareceria muy en fondo de la pantalla
        float y = 0;

        rect = new RectF(x, y , x + length, y + height);


    }

    //Es un getter para crear el rectangulo de la plataforma disponible en la clase BrealOutView
    RectF getRect(){
        return rect;
    }


    //Este metodo sera usado para cambiar/establecer si la plataforma va a la derecha, izquierda o a ninguna parte.

    void reset(int screenX){
        x = screenX / 2;
    }
}
