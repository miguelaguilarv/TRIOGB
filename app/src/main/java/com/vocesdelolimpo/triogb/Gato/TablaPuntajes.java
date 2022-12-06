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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class TablaPuntajes extends AppCompatActivity {
    MediaPlayer player;
    int sr;
    private TextView empatetext;
    private TextView ptos;
    private ImageView primerLugar;
    private ImageView empate2;
    private TextView user1;
    private int puntajeF;
    private int puntos;
    private TextView mTextViewRecord;
    private TextView TextViewGanador2;
    private TextView TextViewPuntajeF;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_puntajes);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ptos = (TextView) findViewById(R.id.ptos);
        TextViewGanador2 = (TextView) findViewById(R.id.inf_1);
        mTextViewRecord = (TextView) findViewById(R.id.textViewRecord);
        TextViewPuntajeF = (TextView) findViewById(R.id.puntaje);
        empatetext=(TextView)findViewById(R.id.inf_2);
        Bundle c = this.getIntent().getExtras();
        puntajeF = c.getInt("puntajeF");
        final int ganador = getIntent().getExtras().getInt("mensaje");

        if (ganador == 1) {
            getUserInfo();
            TextViewPuntajeF.setText("Puntaje Final:"+puntajeF);
            musica();

        } else if (ganador == 2) {
            TextViewGanador2.setText("INVITADO");
            TextViewPuntajeF.setText("Puntaje Final:"+puntajeF);
            musica();
        } else if (ganador == 3) {
            TextViewGanador2.setText("EMPATE");
            empatetext.setText("¡¡¡GAME OVER!!!");
            TextViewPuntajeF.setText("Puntaje:"+puntajeF);
            musica();
        }
        //Aqui se realiza una consulta a la BD del puntaje guardado en el perfil del usuario---------------

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //Se guarda el puntaje que esta en la BD en una variable "puntos", previamente declarada como int.
                    puntos = Integer.parseInt(snapshot.child("gatoscore").getValue().toString());
                    //Se pregunta si el puntaje obtenido en el juego es mayor al de la BD.

                    if (puntajeF > puntos){
                        //Si se cumple se llama al metodo que sube el nuevo puntaje.
                        subirNuevoScore();
                        ptos.setText(puntos+"");
                    }
                    ptos.setText(puntos+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void salir_home(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
        Intent sal = new Intent(this, MainActivity.class);
        startActivity(sal);
        player.release();
    }

    public void musica() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.fin);
        }
        player.start();
    }
    protected void onPause(){
        super.onPause();
        player.release();
    }
    private void getUserInfo () {
        String id = mAuth.getCurrentUser().getUid();
        user1 = (TextView) findViewById(R.id.user1);
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String alias = snapshot.child("alias").getValue().toString();
                    TextViewGanador2.setText(alias);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void subirNuevoScore(){

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                   mTextViewRecord.setVisibility(View.VISIBLE);//HAce visible un textview que dice "NUEVO RECORD PERSONAL"
                    Map<String, Object> scoreMap = new HashMap<>();
                    scoreMap.put("gatoscore", puntajeF);//Se crear un Map y se le agrega el nuevo puntaje.

                    mDatabase.child("Users").child(id).updateChildren(scoreMap);//En esta linea se actualiza la info en la BD

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

