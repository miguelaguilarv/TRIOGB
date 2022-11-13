package com.vocesdelolimpo.triogb.Juegos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vocesdelolimpo.triogb.Gato.GatoActivity;
import com.vocesdelolimpo.triogb.R;

public class GatoFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gato, container, false);

        Button btnJugar = (Button) v.findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), GatoActivity.class);
                //in.putExtra("algo", "Cosas");
                startActivity(in);
            }
        });

        return v;
    }
}