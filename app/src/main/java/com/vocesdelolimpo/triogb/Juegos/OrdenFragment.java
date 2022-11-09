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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orden,container,false);
        new Random();
        ArrayList <Button> botones = new ArrayList<Button>();
        botones.add(v.findViewById(R.id.btn1));
        botones.add(v.findViewById(R.id.btn2));
        botones.add(v.findViewById(R.id.btn3));
        botones.add(v.findViewById(R.id.btn4));
        botones.add(v.findViewById(R.id.btn5));
        botones.add(v.findViewById(R.id.btn6));
        botones.add(v.findViewById(R.id.btn7));
        botones.add(v.findViewById(R.id.btn8));
        botones.add(v.findViewById(R.id.btn9));
        botones.add(v.findViewById(R.id.btn10));
        botones.add(v.findViewById(R.id.btn11));
        botones.add(v.findViewById(R.id.btn12));

        final TextView texto = (TextView)v.findViewById(R.id.texto);
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
        Button validar = (Button)v.findViewById(R.id.btnValidar);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarContenido(texto, numeros);
            }
        });

        return v;
    }
    private void validarContenido(TextView texto, ArrayList numeros){

        final TextView texto2 = (TextView)getView().findViewById(R.id.texto2);
        Collections.sort(numeros);
        String cadena="";
        for (Object num:numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ", "");
        String mensaje = null;

        if (cadena.equals(cadena2)){
            mensaje = "OK";
            texto2.setText("Los ordenaste bien");
        }else{
            texto2.setText("Nah que ver");
        }
    }

}