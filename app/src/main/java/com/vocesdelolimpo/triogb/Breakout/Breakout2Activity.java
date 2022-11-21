package com.vocesdelolimpo.triogb.Breakout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Display;

import com.vocesdelolimpo.triogb.R;

public class Breakout2Activity extends AppCompatActivity {

    BreakoutEngine breakoutEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakout2);

        // Obtener un ojecto Display poara acceder a los detalles de la pantalla
        Display display = getWindowManager().getDefaultDisplay();

        // Se carga la resolucion dentro de un objeto Point
        Point size = new Point();
        display.getSize(size);

        Bundle recibido = this.getIntent().getExtras();
        final int puntaje = recibido.getInt("puntaje");
        final int vidasJ = recibido.getInt("vidas");

        //Inicializa gameView y se establece como vista
        breakoutEngine = new BreakoutEngine(this, size.x, size.y);
        breakoutEngine.setSegundo(true);
        breakoutEngine.setLives(vidasJ);
        breakoutEngine.setScore(puntaje);
        breakoutEngine.setCantBricks(0);
        setContentView(breakoutEngine);
    }

    //Este metodo se ejecuta cuando el jugador inicia el juego
    protected void onResume(){
        super.onResume();

        //Le dice a gameView que reanude el metodo a ejecutar
        breakoutEngine.resume();

    }

    //Este metodo se ejecuta cuando el jugador sale del juego
    @Override
    protected void onPause() {
        super.onPause();

        breakoutEngine.pause();
    }
}