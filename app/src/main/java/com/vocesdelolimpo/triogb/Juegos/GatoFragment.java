package com.vocesdelolimpo.triogb.Juegos;

import android.content.Intent;
import android.media.MediaPlayer;
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
import com.vocesdelolimpo.triogb.Gato.GatoActivity;
import com.vocesdelolimpo.triogb.Gato.GatoCpu;

import com.vocesdelolimpo.triogb.R;
import com.vocesdelolimpo.triogb.Ranking.Ranking;

public class GatoFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gato, container, false);


        //getTopScore();
        Button btnJugar = (Button) v.findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getActivity(), GatoActivity.class);
                startActivity(in);
            }
        });

        Button btnPuntajes = (Button) v.findViewById(R.id.btnPuntajes);
        btnPuntajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle;
                int juego = 2;
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