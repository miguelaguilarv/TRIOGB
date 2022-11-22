package com.vocesdelolimpo.triogb.Juegos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.Breakout.Breakout2Activity;
import com.vocesdelolimpo.triogb.Breakout.BreakoutActivity;
import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

import java.util.HashMap;
import java.util.Map;


public class BreakOutFragment extends Fragment {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView textPuntaje;
    private TextView textPuntajeBO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_break_out, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        textPuntaje = v.findViewById(R.id.nombrePuntaje);
        textPuntajeBO = v.findViewById(R.id.valorPuntaje);

        getTopScore();

        Button btnJugar = (Button) v.findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), BreakoutActivity.class);
                //in.putExtra("algo", "Cosas");
                startActivity(in);
            }
        });

        return v;
    }
    private void getTopScore(){

        mDatabase.child("Scores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("breakname").getValue().toString();
                    String puntos = snapshot.child("breakscore").getValue().toString();

                    textPuntaje.setText(name);
                    textPuntajeBO.setText(puntos+ " Puntos");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}