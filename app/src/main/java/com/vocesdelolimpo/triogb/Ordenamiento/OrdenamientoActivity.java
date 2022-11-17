package com.vocesdelolimpo.triogb.Ordenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.Collections;

public class OrdenamientoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenamiento);

        ArrayList<Button> listado = new ArrayList<Button>();

        listado.add((Button) findViewById(R.id.bt1));
        listado.add((Button) findViewById(R.id.bt2));
        listado.add((Button) findViewById(R.id.bt3));
        listado.add((Button) findViewById(R.id.bt4));
        listado.add((Button) findViewById(R.id.bt5));
        listado.add((Button) findViewById(R.id.bt6));
        listado.add((Button) findViewById(R.id.bt7));
        listado.add((Button) findViewById(R.id.bt8));
        listado.add((Button) findViewById(R.id.bt9));
        listado.add((Button) findViewById(R.id.bt10));
        listado.add((Button) findViewById(R.id.bt11));
        listado.add((Button) findViewById(R.id.bt12));

        final TextView texto = (TextView)findViewById(R.id.texto);

        final ArrayList numeros = new ArrayList();

        for (final Button bt:listado) {
            int num = (int) (Math.random() * 12) + 1;
            numeros.add(num);
            bt.setText(num + "");
            bt.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    texto.setText(texto.getText() + " " + bt.getText());
                    bt.setVisibility(View.INVISIBLE);
                }
            });
        }
        Button validar =(Button)findViewById(R.id.btValidar);

        validar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido(texto, numeros);}
        });
    }

    public void validarContenido(TextView texto, ArrayList numeros){
        Collections.sort(numeros);
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ","");
        String mensaje;

        if(cadena.equals(cadena2)){
            mensaje= "Ok";
            Intent in = new Intent(this,Ordenamiento2Activity.class);
            Bundle b = new Bundle();
            b.putString("mensaje",mensaje);
            in.putExtras(b);
            startActivity(in);
        } else {

            mensaje = "fail";

            finish();
            startActivity(getIntent());
        }
    }
}