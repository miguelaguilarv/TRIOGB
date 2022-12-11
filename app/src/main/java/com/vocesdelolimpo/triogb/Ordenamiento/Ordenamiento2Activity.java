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

public class Ordenamiento2Activity extends AppCompatActivity {
    MediaPlayer player;

    TextView timerTextView;
    int minutes;
    int seconds;
    TextView mostrarptj;
    int puntaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenamiento2);

        timerTextView=(TextView) findViewById(R.id.crono);

        try{
            Bundle recibido = this.getIntent().getExtras();
            Bundle b = this.getIntent().getExtras();
            final String mensaje = recibido.getString("mensaje");
            minutes = b.getInt("creonometro_minuto");
            seconds = b.getInt("creonometro_segundo");
            puntaje = b.getInt("puntaje_1");
        }
        catch (Exception e)
        {}
        Timer myTimer= new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                TimerMethod();
            }

        }, 0,1000);

        ArrayList<Button> listado = new ArrayList<Button>();

        listado.add((Button) findViewById(R.id.but1));
        listado.add((Button) findViewById(R.id.but2));
        listado.add((Button) findViewById(R.id.but3));
        listado.add((Button) findViewById(R.id.but4));
        listado.add((Button) findViewById(R.id.but5));
        listado.add((Button) findViewById(R.id.but6));
        listado.add((Button) findViewById(R.id.but7));
        listado.add((Button) findViewById(R.id.but8));
        listado.add((Button) findViewById(R.id.but9));
        listado.add((Button) findViewById(R.id.but10));
        listado.add((Button) findViewById(R.id.but11));
        listado.add((Button) findViewById(R.id.but12));
        listado.add((Button) findViewById(R.id.but13));
        listado.add((Button) findViewById(R.id.but14));
        listado.add((Button) findViewById(R.id.but15));
        listado.add((Button) findViewById(R.id.but16));
        listado.add((Button) findViewById(R.id.but17));
        listado.add((Button) findViewById(R.id.but18));
        listado.add((Button) findViewById(R.id.but19));
        listado.add((Button) findViewById(R.id.but20));
        listado.add((Button) findViewById(R.id.but21));
        listado.add((Button) findViewById(R.id.but22));
        listado.add((Button) findViewById(R.id.but23));
        listado.add((Button) findViewById(R.id.but24));

        final TextView texto = (TextView)findViewById(R.id.texto);

        final ArrayList numeros = new ArrayList();

        for (final Button bt:listado) {
            int num = (int) (Math.random() * 24) + 1;
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
        Button validar2 =(Button)findViewById(R.id.btValidar2);
        Button completar = (Button) findViewById(R.id.btCompletar2);

        validar2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido2(texto, numeros);}
        });


        completar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(numeros);
                for (Object numeros: numeros){
                    texto.setText(texto.getText().toString()+(int)numeros+" ");

 //                   texto.setVisibility(View.INVISIBLE);
                    completar.setVisibility(View.INVISIBLE);
                    botonesinvisibles();


                }

            }
        });

    }

    public void botonesinvisibles(){

        View boton1 = findViewById(R.id.but1);
        View boton2 = findViewById(R.id.but2);
        View boton3 = findViewById(R.id.but3);
        View boton4 = findViewById(R.id.but4);
        View boton5 = findViewById(R.id.but5);
        View boton6 = findViewById(R.id.but6);
        View boton7 = findViewById(R.id.but7);
        View boton8 = findViewById(R.id.but8);
        View boton9 = findViewById(R.id.but9);
        View boton10 = findViewById(R.id.but10);
        View boton11 = findViewById(R.id.but11);
        View boton12 = findViewById(R.id.but12);
        View boton13 = findViewById(R.id.but13);
        View boton14 = findViewById(R.id.but14);
        View boton15 = findViewById(R.id.but15);
        View boton16 = findViewById(R.id.but16);
        View boton17 = findViewById(R.id.but17);
        View boton18 = findViewById(R.id.but18);
        View boton19 = findViewById(R.id.but19);
        View boton20 = findViewById(R.id.but20);
        View boton21 = findViewById(R.id.but21);
        View boton22 = findViewById(R.id.but22);
        View boton23 = findViewById(R.id.but23);
        View boton24 = findViewById(R.id.but24);
        boton1.setVisibility(View.INVISIBLE);
        boton2.setVisibility(View.INVISIBLE);
        boton3.setVisibility(View.INVISIBLE);
        boton4.setVisibility(View.INVISIBLE);
        boton5.setVisibility(View.INVISIBLE);
        boton6.setVisibility(View.INVISIBLE);
        boton7.setVisibility(View.INVISIBLE);
        boton8.setVisibility(View.INVISIBLE);
        boton9.setVisibility(View.INVISIBLE);
        boton10.setVisibility(View.INVISIBLE);
        boton11.setVisibility(View.INVISIBLE);
        boton12.setVisibility(View.INVISIBLE);
        boton13.setVisibility(View.INVISIBLE);
        boton14.setVisibility(View.INVISIBLE);
        boton15.setVisibility(View.INVISIBLE);
        boton16.setVisibility(View.INVISIBLE);
        boton17.setVisibility(View.INVISIBLE);
        boton18.setVisibility(View.INVISIBLE);
        boton19.setVisibility(View.INVISIBLE);
        boton20.setVisibility(View.INVISIBLE);
        boton21.setVisibility(View.INVISIBLE);
        boton22.setVisibility(View.INVISIBLE);
        boton23.setVisibility(View.INVISIBLE);
        boton24.setVisibility(View.INVISIBLE);




    }


    public void validarContenido2(TextView texto, ArrayList numeros){
        Collections.sort(numeros);
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ","");
        String mensaje;

        if(cadena.equals(cadena2)){
            mensaje= "Ok";
            Intent in = new Intent(this,Ordenamiento3Activity.class);
            Bundle b = new Bundle();
            b.putInt("creonometro_minuto",minutes);
            b.putInt("creonometro_segundo",seconds);
            b.putString("mensaje",mensaje);
            b.putInt("puntaje_2",puntaje);
            in.putExtras(b);
            startActivity(in);
        } else {

            mensaje = "fail";
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



            if(seconds==0)
                timerTextView.setVisibility(View.VISIBLE);

            if (seconds == 60) {

                minutes++;
                seconds=0;
            }
            timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        }






    };
}