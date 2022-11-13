package com.vocesdelolimpo.triogb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vocesdelolimpo.triogb.Gato.GatoActivity;

public class PuntajeGato extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_gato);
    }
    public void rein(View v){
        Intent rei = new Intent(this, GatoActivity.class);
        startActivity(rei);
    }
}