package com.vocesdelolimpo.triogb.Juegos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vocesdelolimpo.triogb.Breakout.BreakoutActivity;
import com.vocesdelolimpo.triogb.Ranking.Ranking;
import com.vocesdelolimpo.triogb.R;


public class BreakOutFragment extends Fragment {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_break_out, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Button btnJugar = (Button) v.findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), BreakoutActivity.class);
                startActivity(in);
            }
        });

        Button btnPuntajes = (Button) v.findViewById(R.id.btnPuntajes);
        btnPuntajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle;
                int juego = 3;
                bundle = new Bundle();
                bundle.putInt("juego", juego);
                Intent in = new Intent(getActivity(), Ranking.class);
                in.putExtra("juego", juego);
                startActivity(in);
            }
        });

        return v;
    }


}