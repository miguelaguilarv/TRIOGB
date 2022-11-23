package com.vocesdelolimpo.triogb.Gato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.Gato.GatoActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_gato);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextViewGanador = (TextView) findViewById(R.id.inf_ganador);
        primerLugar = (ImageView) findViewById(R.id.imageView3);
        empate2=(ImageView) findViewById(R.id.empate);

        Bundle b = this.getIntent().getExtras();
        final int ganador = getIntent().getExtras().getInt("mensaje");
        if (ganador == 1) {
            getUserInfo();
            musica();
        } else if (ganador == 2) {
            TextViewGanador.setText("GANO EL INVITADO");
            musica();
        } else {
            TextViewGanador.setText("EMPATE");
            primerLugar.setVisibility(View.INVISIBLE);
            empate2.setVisibility(View.VISIBLE);
            musica_empate();
        }
    }

    public void rein(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
        Intent rei = new Intent(this, GatoActivity2.class);
        startActivity(rei);

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
                        String alias = snapshot.child("alias").getValue().toString();
                        TextViewGanador.setText("GANÓ " + alias);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

