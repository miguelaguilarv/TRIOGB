package com.vocesdelolimpo.triogb.Gato;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

public class PuntajeGato extends AppCompatActivity {
    MediaPlayer player;
    int sr;
    private ImageView primerLugar;
    private ImageView empate2;
    private TextView TextViewGanador;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView puntejeTV;
    private TextView puntejeTV2;
    public int puntuacion_jugador_22=0;
    public int puntuacion_jugador_11=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_gato);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextViewGanador = (TextView) findViewById(R.id.inf_1);
        puntejeTV = (TextView) findViewById (R.id.puntos_ganador1);
        puntejeTV2 = (TextView) findViewById (R.id.puntos_ganador2);
        primerLugar = (ImageView) findViewById(R.id.imageView3);
        empate2 = (ImageView) findViewById(R.id.empate);
        Bundle a = this.getIntent().getExtras();
        Bundle b = this.getIntent().getExtras();
        Bundle c = this.getIntent().getExtras();
        final int ganador = getIntent().getExtras().getInt("mensaje1");
        final int punteje1 = getIntent().getExtras().getInt("puntaje_1");
        final int punteje2 = getIntent().getExtras().getInt("puntaje_2");
        if (ganador == 1) {
            puntejeTV.setTextColor(GREEN);
            puntejeTV.setText(punteje1+" PUNTOS.");
            puntejeTV2.setTextColor(BLUE);
            puntejeTV2.setText(punteje2+" PUNTOS.");
            puntuacion_jugador_11=punteje1;
            puntuacion_jugador_22=punteje2;
            getUserInfo();
            musica();
        } else if (ganador == 2) {
            puntejeTV.setTextColor(BLUE);
            TextViewGanador.setTextColor(BLUE);
            TextViewGanador.setText("GANO EL INVITADO");
            puntejeTV.setText(punteje2+" PUNTOS.");
            puntejeTV2.setTextColor(GREEN);
            puntejeTV2.setText(punteje1+" PUNTOS.");
            puntuacion_jugador_11=punteje1;
            puntuacion_jugador_22=punteje2;
            musica();
        } else {
            TextViewGanador.setText("EMPATE");
            puntejeTV.setTextColor(GREEN);
            puntejeTV.setText(punteje1+" PUNTOS.");
            puntejeTV2.setTextColor(BLUE);
            puntejeTV2.setTextSize(34);
            puntejeTV2.setText(punteje2+" PUNTOS.");
            primerLugar.setVisibility(View.INVISIBLE);
            empate2.setVisibility(View.VISIBLE);
            puntuacion_jugador_11=punteje1;
            puntuacion_jugador_22=punteje2;
            musica_empate();
        }
    }
    public void rein(View v) {
        puntajefinal();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
//        Intent rei = new Intent(this, GatoActivity2.class);
//        startActivity(rei);
    }
    public void puntajes(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
        Intent pun = new Intent(this, TablaPuntajes.class);
        startActivity(pun);
        mp.release();
    }

    public void salir_home(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
        Intent sal = new Intent(this, MainActivity.class);
        startActivity(sal);
        player.release();
    }
    public void musica_empate() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.aw);
        }
        player.start();
    }
    public void musica() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.happy);
        }
        player.start();
    }
    protected void onPause(){
        super.onPause();
        player.release();
    }
        private void getUserInfo () {
            String id = mAuth.getCurrentUser().getUid();
            mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        TextViewGanador.setTextColor(GREEN);
                        String alias = snapshot.child("alias").getValue().toString();
                        TextViewGanador.setText("GANO:"+alias);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    protected void puntajefinal(){

        int p1 = puntuacion_jugador_11;
        int p2 = puntuacion_jugador_22;
        Bundle a = new Bundle();
        a.putInt("puntaje_11",puntuacion_jugador_11);
        a.putInt("puntaje_22",puntuacion_jugador_22);
        Intent in = new Intent(this, GatoActivity2.class);
        in.putExtra("puntaje_11",p1);
        in.putExtra("puntaje_22",p2);
        startActivity(in);
        finish();
    }
    }

