package com.vocesdelolimpo.triogb.Gato;

import static android.graphics.Color.RED;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import androidx.appcompat.app.AppCompatActivity;
import static android.graphics.Color.RED;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vocesdelolimpo.triogb.R;

public class GatoActivity extends AppCompatActivity {
    Button arreglo [][];
    public int mensaje;
    int turno=1;
    int contador=0;
    SoundPool sp;
    int sonido_de_Repoduccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato);
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonido_de_Repoduccion= sp.load (this ,R.raw.cat,1);

        arreglo = new Button[3][3];
        arreglo[0][0]=this.findViewById(R.id.button00);
        arreglo[0][1]=this.findViewById(R.id.button01);
        arreglo[0][2]=this.findViewById(R.id.button02);
        arreglo[1][0]=this.findViewById(R.id.button10);
        arreglo[1][1]=this.findViewById(R.id.button11);
        arreglo[1][2]=this.findViewById(R.id.button12);
        arreglo[2][0]=this.findViewById(R.id.button20);
        arreglo[2][1]=this.findViewById(R.id.button21);
        arreglo[2][2]=this.findViewById(R.id.button22);

    }

    public void darvalor(int fila, int columna) {
        if (arreglo[fila][columna].getText().equals("")) { //revisa si el boton esta vacio
            if (turno == 1) {
                arreglo[fila][columna].setTextColor(GREEN);
                sp.play(sonido_de_Repoduccion, 1, 1, 1, 0, 1);
                arreglo[fila][columna].setText("X");
                turno = 2;
                contador++;
                comprobar();
            } else {
                arreglo[fila][columna].setTextColor(BLUE);
                arreglo[fila][columna].setText("O");
                sp.play(sonido_de_Repoduccion, 1, 1, 1, 0, 1);
                turno = 1;
                contador++;
                comprobar();
            }
        }
    }
    public void comprobar() {

        //horizontal X________________________________________________________________
        if (arreglo[0][0].getText().equals("X") && arreglo[0][1].getText().equals("X") && arreglo[0][2].getText().equals("X")) {
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[0][1].setBackgroundColor(RED) ;
            arreglo[0][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();
        } else if (arreglo[1][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[1][2].getText().equals("X")) {
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();
        } else if (arreglo[2][0].getText().equals("X") && arreglo[2][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            arreglo[2][0].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

            //horizontal O________________________________________________________________
        } else if (arreglo[0][0].getText().equals("O") && arreglo[0][1].getText().equals("O") && arreglo[0][2].getText().equals("O")) {
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[0][1].setBackgroundColor(RED) ;
            arreglo[0][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[1][0].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[1][2].getText().equals("O")) {
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[2][0].getText().equals("O") && arreglo[2][1].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            arreglo[2][0].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();

            //Diagonal X________________________________________________________________
        } else if (arreglo[0][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

        } else if (arreglo[0][2].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            arreglo[0][2].setBackgroundColor(RED);
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

            //Diagonal O________________________________________________________________
        } else if (arreglo[0][0].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[0][2].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
            arreglo[0][2].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();

            //Vertical x________________________________________________________________
        } else if (arreglo[0][0].getText().equals("X") && arreglo[1][0].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();
        } else if (arreglo[0][1].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][1].getText().equals("X")) {
            arreglo[0][1].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();
        } else if (arreglo[0][2].getText().equals("X") && arreglo[1][2].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            arreglo[0][2].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

        }

        //Vertical O________________________________________________________________
        else if (arreglo[0][0].getText().equals("O") && arreglo[1][0].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[0][1].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][1].getText().equals("O")) {
            arreglo[0][1].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[0][2].getText().equals("O") && arreglo[1][2].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            arreglo[0][2].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        }if(contador==9){
            mensaje=3;
            ganador();

        }

    }
    protected void ganador(){
        Intent in = new Intent(this, PuntajeGato.class);
        Bundle b = new Bundle();
        b.putInt("mensaje",mensaje);
        in.putExtras(b);
        startActivity(in);
        finish();
    }
    //METODO que enlaza con el boton
    public void boton00(View view){
        darvalor(0 ,0);
    }
    public void boton01(View view){
        darvalor(0 ,1);
    }
    public void boton02(View view){
        darvalor(0 ,2);
    }
    public void boton10(View view){
        darvalor(1 ,0);
    }
    public void boton11(View view){
        darvalor(1 ,1);
    }
    public void boton12(View view){
        darvalor(1 ,2);
    }
    public void boton20(View view){
        darvalor(2 ,0);
    }
    public void boton21(View view){
        darvalor(2 ,1);
    }
    public void boton22(View view){
        darvalor(2 ,2);
    }
}
