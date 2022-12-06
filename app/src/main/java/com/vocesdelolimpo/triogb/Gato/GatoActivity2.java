package com.vocesdelolimpo.triogb.Gato;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;
import java.text.DecimalFormat;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import java.text.NumberFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GatoActivity2 extends AppCompatActivity {
    Button arreglo [][];
    private TextView user1;
    MediaPlayer reloj;
    public int mensaje;
    CountDownTimer timer= null;
    public int puntuacion_jugador_1;
    public int puntuacion_jugador_2;
    public TextView player1;
    private TextView player2;
    private int puntaje1;
    private int puntaje2;
    private int puntajeF;
    MediaPlayer player;
    int turno=1;
    int contador=0;
    SoundPool sp;
    int sonido_de_Repoduccion;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato2);
        fondo();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bundle a = this.getIntent().getExtras();
        Bundle c = this.getIntent().getExtras();
        puntaje1 = a.getInt("puntaje_11");
        puntaje2 = a.getInt("puntaje_22");
        puntuacion_jugador_1=puntaje1;
        puntuacion_jugador_2=puntaje2;

        player1=(TextView)findViewById(R.id.player1);
        player2=(TextView)findViewById(R.id.player2);

        player1.setText("SCORE:" +puntuacion_jugador_1);
        player2.setText("SCORE:" +puntuacion_jugador_2);
        getUserInfo();
        crono();
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
                puntuacion_jugador_1=puntuacion_jugador_1+10;
                player1.setText("SCORE:" +puntuacion_jugador_1);
                sp.play(sonido_de_Repoduccion, 1, 1, 1, 0, 1);
                arreglo[fila][columna].setText("X");
                turno = 2;
                contador++;
                comprobar();
            } else {
                arreglo[fila][columna].setTextColor(BLUE);
                puntuacion_jugador_2=puntuacion_jugador_2+10;
                player2.setText("SCORE:" +puntuacion_jugador_2);
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
                 puntuacion_jugador_1=puntuacion_jugador_1+10;
                 arreglo[0][0].setBackgroundColor(RED) ;
                 arreglo[0][1].setBackgroundColor(RED) ;
                 arreglo[0][2].setBackgroundColor(RED) ;

                 ganador_final();
        } else if (arreglo[1][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[1][2].getText().equals("X")) {
                 puntuacion_jugador_1=puntuacion_jugador_1+10;
                 arreglo[1][0].setBackgroundColor(RED) ;
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[1][2].setBackgroundColor(RED) ;

                 ganador_final();
        } else if (arreglo[2][0].getText().equals("X") && arreglo[2][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
                 puntuacion_jugador_1=puntuacion_jugador_1+10;
                 arreglo[2][0].setBackgroundColor(RED) ;
                 arreglo[2][1].setBackgroundColor(RED) ;
                 arreglo[2][2].setBackgroundColor(RED) ;

                 ganador_final();

            //horizontal O________________________________________________________________
        } else if (arreglo[0][0].getText().equals("O") && arreglo[0][1].getText().equals("O") && arreglo[0][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][0].setBackgroundColor(RED) ;
                 arreglo[0][1].setBackgroundColor(RED) ;
                 arreglo[0][2].setBackgroundColor(RED) ;
                 ganador_final();
        } else if (arreglo[1][0].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[1][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[1][0].setBackgroundColor(RED) ;
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[1][2].setBackgroundColor(RED) ;
                 ganador_final();
        } else if (arreglo[2][0].getText().equals("O") && arreglo[2][1].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[2][0].setBackgroundColor(RED) ;
                 arreglo[2][1].setBackgroundColor(RED) ;
                 arreglo[2][2].setBackgroundColor(RED) ;
                 ganador_final();

            //Diagonal X________________________________________________________________
        } else if (arreglo[0][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][0].setBackgroundColor(RED) ;
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[2][2].setBackgroundColor(RED) ;
                 ganador_final();

        } else if (arreglo[0][2].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][2].setBackgroundColor(RED);
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[2][0].setBackgroundColor(RED) ;
                 ganador_final();

            //Diagonal O________________________________________________________________
        } else if (arreglo[0][0].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][0].setBackgroundColor(RED) ;
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[2][2].setBackgroundColor(RED) ;
                 ganador_final();
        } else if (arreglo[0][2].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][2].setBackgroundColor(RED) ;
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[2][0].setBackgroundColor(RED) ;
                 ganador_final();

            //Vertical x________________________________________________________________
        } else if (arreglo[0][0].getText().equals("X") && arreglo[1][0].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][0].setBackgroundColor(RED) ;
                 arreglo[1][0].setBackgroundColor(RED) ;
                 arreglo[2][0].setBackgroundColor(RED) ;
                 ganador_final();
        } else if (arreglo[0][1].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][1].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][1].setBackgroundColor(RED) ;
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[2][1].setBackgroundColor(RED) ;
                 ganador_final();
        } else if (arreglo[0][2].getText().equals("X") && arreglo[1][2].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
                 arreglo[0][2].setBackgroundColor(RED) ;
                 arreglo[1][2].setBackgroundColor(RED) ;
                 arreglo[2][2].setBackgroundColor(RED) ;
                 ganador_final();

        }
        //Vertical O________________________________________________________________
        else if (arreglo[0][0].getText().equals("O") && arreglo[1][0].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
                 puntuacion_jugador_2=puntuacion_jugador_2+10;
                 arreglo[0][0].setBackgroundColor(RED) ;
                 arreglo[1][0].setBackgroundColor(RED) ;
                 arreglo[2][0].setBackgroundColor(RED) ;
                 ganador_final();
        } else if (arreglo[0][1].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][1].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][1].setBackgroundColor(RED) ;
                 arreglo[1][1].setBackgroundColor(RED) ;
                 arreglo[2][1].setBackgroundColor(RED) ;
                 ganador_final();
        } else if (arreglo[0][2].getText().equals("O") && arreglo[1][2].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][2].setBackgroundColor(RED) ;
                 arreglo[1][2].setBackgroundColor(RED) ;
                 arreglo[2][2].setBackgroundColor(RED) ;
                 ganador_final();
        }if(contador==9){
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            ganador_final();
        }
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
    public void boton22(View view) { darvalor(2 ,2); }


    public void reloj_tiempo() {
        if (reloj == null) {
            reloj = MediaPlayer.create(this, R.raw.tictoc);
        }
        reloj.start();
    }
    public void crono(){
        TextView textView_cronometro;
        textView_cronometro = findViewById(R.id.cronomerto);
        timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long sec = (+(millisUntilFinished / 1000) % 60);
                textView_cronometro.setText(f.format(sec)+" SEGUNDOS");

                if (((int) millisUntilFinished / 1000) == 50) {
                    player1.setText("SCORE:" +(puntuacion_jugador_1-10));
                    player2.setText("SCORE:" +(puntuacion_jugador_2-10));

                }if(((int) millisUntilFinished / 1000) == 40) {
                    player1.setText("SCORE:" +(puntuacion_jugador_1-10));
                    player2.setText("SCORE:" +(puntuacion_jugador_2-10));
                    reloj_tiempo();

                } if(((int) millisUntilFinished / 1000) == 30) {
                    player1.setText("SCORE:" +(puntuacion_jugador_1-10));
                    player2.setText("SCORE:" +(puntuacion_jugador_2-10));
                    reloj_tiempo();
                    textView_cronometro.setTextColor(RED);
                } if(((int) millisUntilFinished / 1000) == 20) {
                    player1.setText("SCORE:" +(puntuacion_jugador_1-10));
                    player2.setText("SCORE:" +(puntuacion_jugador_2-10));
                    reloj_tiempo();
                    textView_cronometro.setTextColor(RED);
                } if(((int) millisUntilFinished / 1000) == 10) {
                    player1.setText("SCORE:" +(puntuacion_jugador_1-10));
                    player2.setText("SCORE:" +(puntuacion_jugador_2-10));
                    reloj_tiempo();
                    textView_cronometro.setTextColor(RED);
                }
            }
            public void onFinish() {
                mensaje=3;
                ganador_final();
                reloj.release();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reloj.release();
    }
    private void getUserInfo () {
        String id = mAuth.getCurrentUser().getUid();
        user1 = (TextView) findViewById(R.id.user1);
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String alias = snapshot.child("alias").getValue().toString();
                    user1.setText(alias+":");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void fondo() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.fondo2);
        }
        player.start();
    }
    protected void ganador_final(){
        Bundle c = new Bundle();

        Intent in = new Intent(this, TablaPuntajes.class);
        if(puntuacion_jugador_1>puntuacion_jugador_2){
            mensaje=1;
            c.putInt("mensaje",mensaje);
            c.putInt("puntajeF",puntuacion_jugador_1);
            in.putExtra("puntajeF",puntuacion_jugador_1);
            in.putExtra("mensaje",mensaje);
        }else if(puntuacion_jugador_2>puntuacion_jugador_1){
            mensaje=2;
            c.putInt("mensaje",mensaje);
            c.putInt("puntajeF",puntuacion_jugador_2);
            in.putExtra("puntajeF",puntuacion_jugador_2);
            in.putExtra("mensaje",mensaje);
        }else if(puntuacion_jugador_1==puntuacion_jugador_2){
            mensaje=3;
            c.putInt("mensaje",mensaje);
            c.putInt("puntajeF",puntuacion_jugador_2);
            in.putExtra("puntajeF",puntuacion_jugador_2);
            in.putExtra("mensaje",mensaje);
        }
        player.release();
        startActivity(in);
        timer.cancel();
        reloj.release();
        finish();

    }
}
