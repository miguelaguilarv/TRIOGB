package com.vocesdelolimpo.triogb.Ordenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Ordenamiento3Activity extends AppCompatActivity {
    MediaPlayer player;
    int minutes;
    int seconds;
    TextView timerTextView;
    TextView mostrarvidas;
    TextView mostrarptj;
    int puntaje;
    int vidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenamiento3);
        //=====================================================
        mostrarvidas=(TextView)findViewById(R.id.textovidas);
        mostrarvidas.setText("Vidas:"+ vidas);
        //=====================================================
        Bundle b = this.getIntent().getExtras();
        minutes = b.getInt("creonometro_minuto");
        seconds = b.getInt("creonometro_segundo");
        puntaje = b.getInt("puntaje_2");
        vidas = b.getInt("vidas_2");
        Timer myTimer= new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                TimerMethod();
            }

        }, 0,1000);
        timerTextView=(TextView) findViewById(R.id.crono);
        ArrayList<Button> listado = new ArrayList<Button>();

        listado.add((Button) findViewById(R.id.bt1));
        listado.add((Button) findViewById(R.id.bt2));
        listado.add((Button) findViewById(R.id.bt3));
        listado.add((Button) findViewById(R.id.bt4));
        listado.add((Button) findViewById(R.id.bt5));
        listado.add((Button) findViewById(R.id.bt6));
        listado.add((Button) findViewById(R.id.bt7));
        listado.add((Button) findViewById(R.id.bt8));
        listado.add((Button) findViewById(R.id.bt9));
        listado.add((Button) findViewById(R.id.bt10));
        listado.add((Button) findViewById(R.id.bt11));
        listado.add((Button) findViewById(R.id.bt12));

        final TextView texto = (TextView)findViewById(R.id.texto);

        final ArrayList numeros = new ArrayList();

        for (final Button bt:listado) {
            int num = (int) (Math.random() * 12) + 1;
            numeros.add(num);
            bt.setText(num + "");
            bt.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    musica();
                    texto.setText(texto.getText() + " " + bt.getText());
                    bt.setVisibility(View.INVISIBLE);
                }
            });
        }
        Button validar3 =(Button)findViewById(R.id.btValidar3);
        Button completar = (Button) findViewById(R.id.btCompletar3);

        validar3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido3(texto, numeros);}
        });


        completar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(numeros,Collections.reverseOrder());
                for (Object numeros: numeros){
                    texto.setText(texto.getText().toString()+(int)numeros+" ");
 //                   texto.setVisibility(View.INVISIBLE);
                    completar.setVisibility(View.INVISIBLE);
                    puntaje = 0;
                    botonesinvisibles();


                }

            }
        });
    }

    public void botonesinvisibles(){
        View boton1 = findViewById(R.id.bt1);
        boton1.setVisibility(View.INVISIBLE);
        View boton2 = findViewById(R.id.bt2);
        boton2.setVisibility(View.INVISIBLE);
        View boton3 = findViewById(R.id.bt3);
        boton3.setVisibility(View.INVISIBLE);
        View boton4 = findViewById(R.id.bt4);
        boton4.setVisibility(View.INVISIBLE);
        View boton5 = findViewById(R.id.bt5);
        boton5.setVisibility(View.INVISIBLE);
        View boton6 = findViewById(R.id.bt6);
        boton6.setVisibility(View.INVISIBLE);
        View boton7 = findViewById(R.id.bt7);
        boton7.setVisibility(View.INVISIBLE);
        View boton8 = findViewById(R.id.bt8);
        boton8.setVisibility(View.INVISIBLE);
        View boton9 = findViewById(R.id.bt9);
        boton9.setVisibility(View.INVISIBLE);
        View boton10 = findViewById(R.id.bt10);
        boton10.setVisibility(View.INVISIBLE);
        View boton11 = findViewById(R.id.bt11);
        boton11.setVisibility(View.INVISIBLE);
        View boton12 = findViewById(R.id.bt12);
        boton12.setVisibility(View.INVISIBLE);



    }



    public void validarContenido3(TextView texto, ArrayList numeros){
        Collections.sort(numeros,Collections.reverseOrder());
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";

        }

        String cadena2 = texto.getText().toString().replaceAll(" ","");
        String mensaje;


        if(cadena.equals(cadena2)){
            mensaje= "Ok";
            Intent in = new Intent(this,Ordenamiento4Activity.class);
            Bundle b = new Bundle();
            b.putString("mensaje",mensaje);
            b.putInt("creonometro_minuto",minutes);
            b.putInt("creonometro_segundo",seconds);
            b.putInt("creonometro_minuto",minutes);
            b.putInt("puntaje_3",puntaje);
            b.putInt("vidas_4",vidas);
            in.putExtras(b);
            startActivity(in);

        } else {
            mensaje = "fail";
            vidas--;
            finish();
            startActivity(getIntent());

        }
    }
    public void musica() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.buton23);
        }
        player.start();
    }
    private void TimerMethod() { this.runOnUiThread(Timer_Tick);}
    private Runnable Timer_Tick = new Runnable(){


        public void run() {
            seconds++;
            mostrarptj = (TextView)findViewById(R.id.textopuntaje);
            mostrarptj.setText("Puntaje: "+puntaje);
            if(seconds==0)
                timerTextView.setVisibility(View.VISIBLE);

            //==========================================================================================
            if(seconds==0)
                timerTextView.setVisibility(View.VISIBLE);


            if(seconds==9 && minutes==0){
                puntaje -= 25;
            }if(seconds==19 && minutes==0){
                puntaje -=25;


            }if(seconds==29 && minutes==0){
                puntaje -=25;

            }if(seconds==39 && minutes==0){
                puntaje -=25;

            }if(seconds==49 && minutes==0){
                puntaje -=25;

            }if(seconds==59 && minutes==0){
                puntaje -=25;
            }
//==========================================================================================


            if (seconds == 60) {

                minutes++;
                seconds=0;
            }
            timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        }






    };


}