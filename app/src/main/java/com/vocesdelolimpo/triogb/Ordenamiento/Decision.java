package com.vocesdelolimpo.triogb.Ordenamiento;

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
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ptos = (TextView) findViewById(R.id.ptos);
        Bundle b = this.getIntent().getExtras();
        minutes = b.getInt("creonometro_minuto");
        seconds = b.getInt("creonometro_segundo");
        ptos.setText(minutes+ ":" +seconds);

    }

    public void salir_home(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
        Intent sal = new Intent(this, MainActivity.class);
        startActivity(sal);
        player.release();
    }
    private void getUserInfo () {
        String id = mAuth.getCurrentUser().getUid();
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
}
