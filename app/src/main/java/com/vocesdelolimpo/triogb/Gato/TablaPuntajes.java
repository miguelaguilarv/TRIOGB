package com.vocesdelolimpo.triogb.Gato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class TablaPuntajes extends AppCompatActivity {
    MediaPlayer player;
    int sr;
    private ImageView primerLugar;
    private ImageView empate2;
    private TextView TextViewGanador2;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_puntajes);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextViewGanador2 = (TextView) findViewById(R.id.inf_1);
        Bundle b = this.getIntent().getExtras();
        final int ganador = getIntent().getExtras().getInt("mensaje");
        if (ganador == 1) {
            getUserInfo();
            musica();
        } else if (ganador == 2) {
            TextViewGanador2.setText("GANADOR:INVITADO");
            musica();
        } else {
            TextViewGanador2.setText("EMPATE");
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
                    TextViewGanador2.setText("GANADOR:" + alias);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

