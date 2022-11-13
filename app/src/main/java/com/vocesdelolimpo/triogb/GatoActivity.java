package com.vocesdelolimpo.triogb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GatoActivity extends AppCompatActivity {
    Button arreglo [][];
    int turno=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato);

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
                arreglo[fila][columna].setText("X");
                turno = 2;
                comprobar();
            } else {
                arreglo[fila][columna].setText("O");
                turno = 1;
                comprobar();
            }
        }
    }
    public void comprobar() {
        //int ganador=
        //horizontal X________________________________________________________________
        if (arreglo[0][0].getText().equals("X") && arreglo[0][1].getText().equals("X") && arreglo[0][2].getText().equals("X")) {
            ganador();
        }else if (arreglo[1][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[1][2].getText().equals("X")) {
            ganador();
        }else if (arreglo[2][0].getText().equals("X") && arreglo[2][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            ganador();

            //horizontal O________________________________________________________________
        }else if (arreglo[0][0].getText().equals("O") && arreglo[0][1].getText().equals("O") && arreglo[0][2].getText().equals("O")) {
            ganador();
        }else if (arreglo[1][0].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[1][2].getText().equals("O")) {
            ganador();
        }else if (arreglo[2][0].getText().equals("O") && arreglo[2][1].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            ganador();

            //Diagonal X________________________________________________________________
        }else if (arreglo[0][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            ganador();
        }else if (arreglo[0][2].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            ganador();

            //Diagonal O________________________________________________________________
        }else if (arreglo[0][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            ganador();
        }else if (arreglo[0][2].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
            ganador();

            //Vertical x________________________________________________________________
        }else if (arreglo[0][0].getText().equals("X") && arreglo[1][0].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            ganador();
        }else if (arreglo[0][1].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][1].getText().equals("X")) {
            ganador();
        }else if (arreglo[0][2].getText().equals("X") && arreglo[1][2].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            ganador();
        }

        //Vertical O________________________________________________________________
        else if (arreglo[0][0].getText().equals("O") && arreglo[1][0].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
            ganador();
        }else if (arreglo[0][1].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][1].getText().equals("O")) {
            ganador();
        }else if (arreglo[0][2].getText().equals("O") && arreglo[1][2].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            ganador();
        }
    }
    protected void ganador(){
        String mensaje;
        mensaje= "Ok";
        Intent in = new Intent(this,PuntajeGato.class);
        Bundle b = new Bundle();
        b.putString("mensaje",mensaje);
        in.putExtras(b);
        startActivity(in);
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
