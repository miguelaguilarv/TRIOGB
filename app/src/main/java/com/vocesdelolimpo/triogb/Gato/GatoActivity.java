package com.vocesdelolimpo.triogb.Gato;

import static android.graphics.Color.RED;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.R;

public class GatoActivity extends AppCompatActivity {
    Button arreglo [][];
    public int mensaje;
    public Button Vibrar;
    public int puntuacion_jugador_1=0;
    public int puntuacion_jugador_2=0;
    private TextView user1;
    public TextView player1;
    private TextView player2;
    private TextView invitado;
    int turno=1;
    int contador=0;
    MediaPlayer player;
    SoundPool sp;
    int sonido_de_Repoduccion;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public String jugador_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato);
        invitado=(TextView)findViewById(R.id.invitado);
        Bundle d = this.getIntent().getExtras();
        jugador_2 = d.getString("jugador2");
        invitado.setText(jugador_2);
        fondo();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        getUserInfo();
    }

    public void darvalor(int fila, int columna) {

        player1=(TextView)findViewById(R.id.player1);
        player2=(TextView)findViewById(R.id.player2);
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
            mensaje=1;
            ganador();
        } else if (arreglo[1][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[1][2].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();
        } else if (arreglo[2][0].getText().equals("X") && arreglo[2][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[2][0].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

            //horizontal O________________________________________________________________
        } else if (arreglo[0][0].getText().equals("O") && arreglo[0][1].getText().equals("O") && arreglo[0][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[0][1].setBackgroundColor(RED) ;
            arreglo[0][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[1][0].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[1][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[2][0].getText().equals("O") && arreglo[2][1].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[2][0].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();

            //Diagonal X________________________________________________________________
        } else if (arreglo[0][0].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

        } else if (arreglo[0][2].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][2].setBackgroundColor(RED);
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

            //Diagonal O________________________________________________________________
        } else if (arreglo[0][0].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[0][2].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][2].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();

            //Vertical x________________________________________________________________
        } else if (arreglo[0][0].getText().equals("X") && arreglo[1][0].getText().equals("X") && arreglo[2][0].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();
        } else if (arreglo[0][1].getText().equals("X") && arreglo[1][1].getText().equals("X") && arreglo[2][1].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][1].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();
        } else if (arreglo[0][2].getText().equals("X") && arreglo[1][2].getText().equals("X") && arreglo[2][2].getText().equals("X")) {
            puntuacion_jugador_1=puntuacion_jugador_1+10;
            arreglo[0][2].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=1;
            ganador();

        }
        //Vertical O________________________________________________________________
        else if (arreglo[0][0].getText().equals("O") && arreglo[1][0].getText().equals("O") && arreglo[2][0].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][0].setBackgroundColor(RED) ;
            arreglo[1][0].setBackgroundColor(RED) ;
            arreglo[2][0].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[0][1].getText().equals("O") && arreglo[1][1].getText().equals("O") && arreglo[2][1].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][1].setBackgroundColor(RED) ;
            arreglo[1][1].setBackgroundColor(RED) ;
            arreglo[2][1].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        } else if (arreglo[0][2].getText().equals("O") && arreglo[1][2].getText().equals("O") && arreglo[2][2].getText().equals("O")) {
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            arreglo[0][2].setBackgroundColor(RED) ;
            arreglo[1][2].setBackgroundColor(RED) ;
            arreglo[2][2].setBackgroundColor(RED) ;
            mensaje=2;
            ganador();
        }if(contador==9){
            puntuacion_jugador_2=puntuacion_jugador_2+10;
            mensaje=3;
            ganador();

        }

    }
    public void fondo() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.fondo);
        }
        player.start();
    }
    protected void ganador(){
        Intent in = new Intent(this, PuntajeGato.class);
        Bundle a = new Bundle();
        Bundle b = new Bundle();
        Bundle c = new Bundle();
        Bundle d = new Bundle();
        a.putInt("puntaje_1",puntuacion_jugador_1);
        b.putInt("mensaje1",mensaje);
        c.putInt("puntaje_2",puntuacion_jugador_2);
        d.putString("jugador2",jugador_2);
        in.putExtras(a);
        in.putExtras(b);
        in.putExtras(c);
        in.putExtras(d);
        player.release();
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

    @Override
    protected void onPause() {
        super.onPause();
        player.release();
    }
}
