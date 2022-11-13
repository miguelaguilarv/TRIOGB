package com.vocesdelolimpo.triogb.Breakout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.vocesdelolimpo.triogb.R;

public class  BreakoutActivity extends AppCompatActivity {

    BreakoutEngine breakoutEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakout);

        // Obtener un ojecto Display poara acceder a los detalles de la pantalla
        Display display = getWindowManager().getDefaultDisplay();

        // Se carga la resolucion dentro de un objeto Point
        Point size = new Point();
        display.getSize(size);

        //Inicializa gameView y se establece como vista
        breakoutEngine = new BreakoutEngine(this, size.x, size.y);
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