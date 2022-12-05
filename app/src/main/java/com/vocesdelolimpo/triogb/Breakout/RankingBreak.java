package com.vocesdelolimpo.triogb.Breakout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.List;

public class RankingBreak extends AppCompatActivity {

    LinearLayoutManager mLayaoutManager;
    RecyclerView recyclerViewUsuarios;
    Adaptador adaptador;
    List<Usuario> usuarioList;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_break);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mLayaoutManager = new LinearLayoutManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);

        mLayaoutManager.setReverseLayout(true); //Ordena Z-A
        mLayaoutManager.setStackFromEnd(true); //Empiueza desde arriba sin tener desliz

        recyclerViewUsuarios.setHasFixedSize(true);
        recyclerViewUsuarios.setLayoutManager(mLayaoutManager);
        usuarioList = new ArrayList<>();

        ObtenerTodosLosUsuarios();
    }

    private void ObtenerTodosLosUsuarios() {


        DatabaseReference ref = mDatabase.child("Users");
        ref.orderByChild("breakscore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usuarioList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){

                    Usuario usuario = ds.getValue(Usuario.class);

                    usuarioList.add(usuario);

                    adaptador = new Adaptador(RankingBreak.this, usuarioList);
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