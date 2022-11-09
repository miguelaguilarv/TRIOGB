package com.vocesdelolimpo.triogb.Juegos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OrdenFragment extends Fragment {

    private ArrayList<Button> botones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        botones = new ArrayList<Button>();
        botones.add((Button) container.findViewById(R.id.btn2));
        botones.add((Button)container.findViewById(R.id.btn2));
        botones.add((Button)container.findViewById(R.id.btn3));
        botones.add((Button)container.findViewById(R.id.btn4));
        botones.add((Button)container.findViewById(R.id.btn5));
        botones.add((Button)container.findViewById(R.id.btn6));
        botones.add((Button)container.findViewById(R.id.btn7));
        botones.add((Button)container.findViewById(R.id.btn8));
        botones.add((Button)container.findViewById(R.id.btn9));
        botones.add((Button)container.findViewById(R.id.btn10));
        botones.add((Button)container.findViewById(R.id.btn11));
        botones.add((Button)container.findViewById(R.id.btn12));
        final TextView texto = (TextView)container.findViewById(R.id.texto);

        final ArrayList numeros = new ArrayList();

        for(final Button btn:botones){
            int num = (int) Math.floor(Math.random()*20+1);
            numeros.add(num);
            btn.setText(num+"");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    texto.setText(texto.getText()+" "+btn.getText());
                    btn.setVisibility(View.INVISIBLE);

                }
            });

        }
        Button validar = (Button)container.findViewById(R.id.btnValidar);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarContenido(texto, numeros);
            }
        });

        return inflater.inflate(R.layout.fragment_orden, container, false);
    }

    private void validarContenido(TextView texto, ArrayList numeros){
        //final TextView texto2 = (TextView) container.findViewById(R.id.texto2);
        Collections.sort(numeros);
        String cadena = "";
        for (Object num : numeros) {
            cadena += (int) num + "";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ", "");

        if (cadena.equals(cadena2)){
            //texto2.setText("Los ordenaste bien");
        }else{
            //texto2.setText("Nah que ver");
        }
    }
}