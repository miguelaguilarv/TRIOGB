package com.vocesdelolimpo.triogb.Ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {

    LinearLayoutManager mLayaoutManager;
    RecyclerView recyclerViewUsuarios;
    Adaptador adaptador;
    List<Usuario> usuarioList;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private TextView juegoText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_break);

        Bundle recibido = this.getIntent().getExtras();
        final int juego = recibido.getInt("juego");
        juegoText = (TextView) findViewById(R.id.tituloJuego);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mLayaoutManager = new LinearLayoutManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);

        mLayaoutManager.setReverseLayout(true); //Ordena Z-A
        mLayaoutManager.setStackFromEnd(true); //Empiueza desde arriba sin tener desliz

        recyclerViewUsuarios.setHasFixedSize(true);
        recyclerViewUsuarios.setLayoutManager(mLayaoutManager);
        usuarioList = new ArrayList<>();

        if (juego == 1){

            juegoText.setText("Top Puntajes Ordenamiento");
            RankingOrden();

        }else if(juego == 2){
            juegoText.setText("Top Puntajes Gato");
            RankingGato();
        }else if(juego == 3){
            juegoText.setText("Top Puntajes Breakout");
            RankingBreakout();
        }

    }

    private void RankingBreakout() {


        DatabaseReference ref = mDatabase.child("Users");
        ref.orderByChild("breakscore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                usuarioList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){

                    Usuario usuario = ds.getValue(Usuario.class);

                    usuarioList.add(usuario);

                    adaptador = new Adaptador(Ranking.this, usuarioList, 3);


                    recyclerViewUsuarios.setAdapter(adaptador);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void RankingGato() {


        DatabaseReference ref = mDatabase.child("Users");
        ref.orderByChild("gatoscore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usuarioList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){

                    Usuario usuario = ds.getValue(Usuario.class);

                    usuarioList.add(usuario);

                    adaptador = new Adaptador(Ranking.this, usuarioList, 2);
                    recyclerViewUsuarios.setAdapter(adaptador);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void RankingOrden() {


        DatabaseReference ref = mDatabase.child("Users");
        ref.orderByChild("ordenscore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usuarioList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){

                    Usuario usuario = ds.getValue(Usuario.class);

                    usuarioList.add(usuario);

                    adaptador = new Adaptador(Ranking.this, usuarioList,1);
                    recyclerViewUsuarios.setAdapter(adaptador);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}