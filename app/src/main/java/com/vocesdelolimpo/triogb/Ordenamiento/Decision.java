package com.vocesdelolimpo.triogb.Ordenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vocesdelolimpo.triogb.R;

public class Decision extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);

        Bundle recibido2 = this.getIntent().getExtras();
        final String mensaje2 = recibido2.getString("mensaje");


    }
}