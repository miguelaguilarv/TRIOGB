package com.vocesdelolimpo.triogb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vocesdelolimpo.triogb.Gato.GatoActivity;

public class PuntajeGato extends AppCompatActivity {
    SoundPool sp2;
    int sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_gato);

    }
    public void rein(View v){
        MediaPlayer mp = MediaPlayer.create(this,R.raw.click);
        mp.start();
        Intent rei = new Intent(this, GatoActivity.class);
        startActivity(rei);

    }
    public void salir_home(View v){
        MediaPlayer mp = MediaPlayer.create(this,R.raw.click);
        mp.start();
        Intent sal = new Intent(this, MainActivity.class);
        startActivity(sal);
    }
   // public void musica(View view){
   //
   //      sp2 = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
   //      so= sp2.load (this ,R.raw.happy,1);
   //      sp2.play(so, 1, 1, 1, 0, 1);
   //
   //     }

}
