package com.vocesdelolimpo.triogb.Ordenamiento;

import
        androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Handler;

import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
import java.util.Collections;

public class OrdenamientoActivity extends AppCompatActivity {

    MediaPlayer player;
    TextView timerTextView;
    TextView textVidas;
    int minutes=0;
    int seconds=0;
    int vidas = 3;
    TextView mostrarptj;
    int puntaje = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenamiento);

        timerTextView =(TextView)findViewById(R.id.crono);
        textVidas = (TextView) findViewById(R.id.textovidas);
        Timer myTimer= new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                TimerMethod();
            }

        }, 0,1000);

        try {
            Bundle lives = this.getIntent().getExtras();
            vidas = lives.getInt("vidas");
        }catch(Exception e){

        }

        textVidas.setText("Vidas: "+vidas);
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

        final TextView texto = (TextView) findViewById(R.id.texto);

        final ArrayList numeros = new ArrayList();

        for (final Button bt : listado) {
            int num = (int) (Math.random() * 12) + 1;
            numeros.add(num);
            bt.setText(num + "");
            bt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    musica();
                    texto.setText(texto.getText() + " " + bt.getText());
                    bt.setVisibility(View.INVISIBLE);

                }
            });
        }
        Button validar = (Button) findViewById(R.id.btValidar);
        Button completar = (Button) findViewById(R.id.btCompletar);



        validar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido(texto, numeros);
            }
        });


        completar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(numeros);

                for (Object numeros: numeros){
                    texto.setText(texto.getText().toString()+(int)numeros+" ");
  //                  texto.setVisibility(View.INVISIBLE);
                    completar.setVisibility(View.INVISIBLE);
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




    public void validarContenido(TextView texto, ArrayList numeros){
        Collections.sort(numeros);
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ","");
        String mensaje;

        if(cadena.equals(cadena2)){
            mensaje= "Ok";
            Intent in = new Intent(this,Ordenamiento2Activity.class);
            Bundle b = new Bundle();
            b.putString("mensaje",mensaje);
            b.putInt("creonometro_minuto",minutes);
            b.putInt("creonometro_segundo",seconds);
            b.putInt("puntaje_1",puntaje);
            in.putExtras(b);
            startActivity(in);
        } else {
            if (vidas > 0){

                vidas -= 1;
                Bundle lives = new Bundle();
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