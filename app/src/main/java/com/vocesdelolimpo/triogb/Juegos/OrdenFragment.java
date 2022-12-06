package com.vocesdelolimpo.triogb.Juegos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.vocesdelolimpo.triogb.Ordenamiento.OrdenamientoActivity;
import com.vocesdelolimpo.triogb.R;
import com.vocesdelolimpo.triogb.Ranking.Ranking;

public class OrdenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orden, container, false);

        Button btnJugar = (Button) v.findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), OrdenamientoActivity.class);
                startActivity(in);
            }
        });

        Button btnPuntajes = (Button) v.findViewById(R.id.btnPuntajes);
        btnPuntajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle;
                int juego = 1;
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