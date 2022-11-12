package com.vocesdelolimpo.triogb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PuntajeBreakout extends AppCompatActivity {
    private TextView mTextViewScore;
    private Button mButtonBackBO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_breakout);

        Bundle recibido = this.getIntent().getExtras();
        final String puntaje = recibido.getString("puntaje");

//        try {
//            Bundle recibido = this.getIntent().getExtras();
//            puntaje = recibido.getString("puntaje");
//        } catch (Exception e) {
//        }
        mTextViewScore = (TextView) findViewById(R.id.textViewScore);
        mTextViewScore.setText("Tu puntaje fue: " + puntaje);


        mButtonBackBO = (Button) findViewById(R.id.btnBackBO);

        mButtonBackBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PuntajeBreakout.this, MainActivity.class));
            }
        });


    }
}