package com.vocesdelolimpo.triogb.Ordenamiento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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
import com.vocesdelolimpo.triogb.Breakout.PuntajeBreakout;
import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Decision extends AppCompatActivity {
    MediaPlayer player;
    private TextView ptos;
    int minutes;
    int seconds;
    private TextView TextViewGanador2;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button salir;
    TextView record;
    private String ordenScore;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextViewGanador2 = (TextView) findViewById(R.id.inf_1);
        salir = (Button) findViewById(R.id.buttonAtras);
        ptos = (TextView) findViewById(R.id.ptos);
        Bundle b = this.getIntent().getExtras();
        minutes = b.getInt("creonometro_minuto");
        seconds = b.getInt("creonometro_segundo");
        ptos.setText(minutes+ ":" +seconds);
        record = (TextView) findViewById(R.id.record);
        ordenScore = String.format("%d:%d", minutes, seconds);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        record.setText(ordenScore);

        getUserInfo();

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                startActivity(new Intent(Decision.this, MainActivity.class));
                finish();
                player.release();
            }
        });



    }


    private void getUserInfo () {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String alias = snapshot.child("alias").getValue().toString();
                    TextViewGanador2.setText(alias);
                    subirNuevoScore();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void subirNuevoScore() {

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    //mTextViewRecord.setVisibility(View.VISIBLE);//HAce visible un textview que dice "NUEVO RECORD PERSONAL"
                    Map<String, Object> scoreMap = new HashMap<>();
                    scoreMap.put("ordenscore", ordenScore);//Se crear un Map y se le agrega el nuevo puntaje.

                    mDatabase.child("Users").child(id).updateChildren(scoreMap);//En esta linea se actualiza la info en la BD

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
