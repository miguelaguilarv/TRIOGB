package com.vocesdelolimpo.triogb.Ordenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

public class perderActivity extends AppCompatActivity {
    private Button salir2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salir2 = (Button) findViewById(R.id.buttonAtras2);

        setContentView(R.layout.activity_perder);


        salir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(perderActivity.this, MainActivity.class));
                finish();

            }
        });


    }
}