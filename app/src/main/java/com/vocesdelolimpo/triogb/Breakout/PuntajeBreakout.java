package com.vocesdelolimpo.triogb.Breakout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

public class PuntajeBreakout extends AppCompatActivity {

    MediaPlayer player;
    private TextView mTextViewScore;
    private TextView mTextViewRes;
    private TextView mTextViewName;
    private Button mButtonBackBO;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_breakout);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle recibido = this.getIntent().getExtras();
        final String puntaje = recibido.getString("puntaje");

        Bundle vidasJugador = this.getIntent().getExtras();
        final int vidasJ = getIntent().getExtras().getInt("vidas");

//        try {
//            Bundle recibido = this.getIntent().getExtras();
//            puntaje = recibido.getString("puntaje");
//        } catch (Exception e) {
//        }

        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewScore = (TextView) findViewById(R.id.textViewScore);
        mTextViewRes = (TextView) findViewById(R.id.textViewRes);

        if(vidasJ != 0){
            if (player == null){
                player = MediaPlayer.create(this, R.raw.watc);
            }
            player.start();
            mTextViewRes.setText("Ganaste!");
            mTextViewScore.setText("Tu puntaje fue: " + puntaje);

        }else{
            if (player == null){
                player = MediaPlayer.create(this, R.raw.nggyu);
            }
            player.start();
            mTextViewRes.setText("Perdiste!");
            mTextViewScore.setText("Tu puntaje fue: " + puntaje);

        }



        mButtonBackBO = (Button) findViewById(R.id.btnBackBO);

        mButtonBackBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player != null){
                    player.release();
                    player = null;
                }
                startActivity(new Intent(PuntajeBreakout.this, MainActivity.class));
                finish();
            }
        });

        getUserInfo();

    }
    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();

                    mTextViewName.setText("Hola " + name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
        player.release();
    }
}