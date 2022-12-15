package com.vocesdelolimpo.triogb.Ordenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Ordenamiento4Activity extends AppCompatActivity {
    MediaPlayer player;
    int minutes;
    int seconds;
    TextView textVidas;
    TextView timerTextView;

    TextView mostrarptj;
    int puntaje;
    int vidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenamiento4);
        //=====================================================
        textVidas=(TextView)findViewById(R.id.textovidas);
        timerTextView=(TextView) findViewById(R.id.crono);
        //=====================================================
        try{
            Bundle recibido = this.getIntent().getExtras();
            Bundle b = this.getIntent().getExtras();
            Bundle lives = this.getIntent().getExtras();
            vidas = lives.getInt("vidas");
            final String mensaje = recibido.getString("mensaje");
            minutes = b.getInt("creonometro_minuto");
            seconds = b.getInt("creonometro_segundo");
            puntaje = b.getInt("puntaje_1");
            vidas = b.getInt("vidas");
        }
        catch (Exception e)
        {}

        textVidas.setText("Vidas:"+ vidas);
        Timer myTimer= new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                TimerMethod();
            }

        }, 0,1000);
        timerTextView=(TextView) findViewById(R.id.crono);
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
        Button validar4 =(Button)findViewById(R.id.btValidar4);
        Button completar = (Button) findViewById(R.id.btCompletar4);

        validar4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido4(texto, numeros);}
        });



        completar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(numeros,Collections.reverseOrder());
                for (Object numeros: numeros){

                    texto.setText(texto.getText().toString()+(int)numeros+" ");
//                    texto.setVisibility(View.INVISIBLE);
                    completar.setVisibility(View.INVISIBLE);
                    puntaje = 0;
                    botonesinvisibles();


                }

            }
        });
    }


    public void botonesinvisibles(){
        View boton1 = findViewById(R.id.but1);
        boton1.setVisibility(View.INVISIBLE);
        View boton2 = findViewById(R.id.but2);
        boton2.setVisibility(View.INVISIBLE);
        View boton3 = findViewById(R.id.but3);
        boton3.setVisibility(View.INVISIBLE);
        View boton4 = findViewById(R.id.but4);
        boton4.setVisibility(View.INVISIBLE);
        View boton5 = findViewById(R.id.but5);
        boton5.setVisibility(View.INVISIBLE);
        View boton6 = findViewById(R.id.but6);
        boton6.setVisibility(View.INVISIBLE);
        View boton7 = findViewById(R.id.but7);
        boton7.setVisibility(View.INVISIBLE);
        View boton8 = findViewById(R.id.but8);
        boton8.setVisibility(View.INVISIBLE);
        View boton9 = findViewById(R.id.but9);
        boton9.setVisibility(View.INVISIBLE);
        View boton10 = findViewById(R.id.but10);
        boton10.setVisibility(View.INVISIBLE);
        View boton11 = findViewById(R.id.but11);
        boton11.setVisibility(View.INVISIBLE);
        View boton12 = findViewById(R.id.but12);
        boton12.setVisibility(View.INVISIBLE);
        View boton13 = findViewById(R.id.but13);
        boton13.setVisibility(View.INVISIBLE);
        View boton14 = findViewById(R.id.but14);
        boton14.setVisibility(View.INVISIBLE);
        View boton15 = findViewById(R.id.but15);
        boton15.setVisibility(View.INVISIBLE);
        View boton16 = findViewById(R.id.but16);
        boton16.setVisibility(View.INVISIBLE);
        View boton17 = findViewById(R.id.but17);
        boton17.setVisibility(View.INVISIBLE);
        View boton18 = findViewById(R.id.but18);
        boton18.setVisibility(View.INVISIBLE);
        View boton19 = findViewById(R.id.but19);
        boton19.setVisibility(View.INVISIBLE);
        View boton20 = findViewById(R.id.but20);
        boton20.setVisibility(View.INVISIBLE);
        View boton21 = findViewById(R.id.but21);
        boton21.setVisibility(View.INVISIBLE);
        View boton22 = findViewById(R.id.but22);
        boton22.setVisibility(View.INVISIBLE);
        View boton23 = findViewById(R.id.but23);
        boton23.setVisibility(View.INVISIBLE);
        View boton24 = findViewById(R.id.but24);
        boton24.setVisibility(View.INVISIBLE);



    }

    public void validarContenido4(TextView texto, ArrayList numeros){
        Collections.sort(numeros,Collections.reverseOrder());
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ","");
        String mensaje;

        if(cadena.equals(cadena2)){
            mensaje= "Ok";
            Intent in = new Intent(this,Decision.class);
            Bundle b = new Bundle();
            b.putString("mensaje",mensaje);
            b.putInt("creonometro_minuto",minutes);
            b.putInt("creonometro_segundo",seconds);
            b.putInt("puntaje_4",puntaje);
            b.putInt("vidas",vidas);
            in.putExtras(b);
            startActivity(in);

        } else {
            if (vidas > 0){
                Bundle lives = new Bundle();
                vidas -= 1;
                lives.putInt("vidas", vidas);
                Intent in = new Intent(getIntent());
                in.putExtra("vidas",vidas);
                startActivity(in);
                finish();
            }else{
                Intent fin = new Intent(this, MainActivity.class);
                startActivity(fin);
                finish();
            }

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


            if(seconds==2 && minutes==0){
                puntaje -= 3;
            }if(seconds==6 && minutes==0){
                puntaje -= 3;
            }if(seconds==10 && minutes==0){
                puntaje -= 3;
            }if(seconds== 14 && minutes==0){
                puntaje -= 3;
            }if(seconds==18 && minutes==0){
                puntaje -= 3;
            }if(seconds==22 && minutes==0){
                puntaje -= 3;
            }if(seconds==26 && minutes==0){
                puntaje -= 3;
            }if(seconds==30 && minutes==0){
                puntaje -= 3;
            }if(seconds==34 && minutes==0){
                puntaje -= 3;
            }if(seconds==38 && minutes==0){
                puntaje -= 3;
            }if(seconds==44 && minutes==0){
                puntaje -= 3;
            }if(seconds==46 && minutes==0){
                puntaje -= 3;
            }if(seconds==50 && minutes==0){
                puntaje -= 3;
            }if(seconds==59 && minutes==0){
                puntaje -= 3;
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