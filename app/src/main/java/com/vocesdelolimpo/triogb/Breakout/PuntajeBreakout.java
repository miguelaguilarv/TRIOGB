package com.vocesdelolimpo.triogb.Breakout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.ProfileActivity;
import com.vocesdelolimpo.triogb.R;
import com.vocesdelolimpo.triogb.RegistroActivity;

import java.util.HashMap;
import java.util.Map;

public class PuntajeBreakout extends AppCompatActivity {

    MediaPlayer player;
    private TextView mTextViewScore;
    private TextView mTextViewRecord;
    private TextView mTextViewRes;
    private TextView mTextViewName;
    private Button mButtonBackBO;
    private final static String CHANNEL_ID ="NOTIFICACION";
    private final static int NOTIFICATION_ID= 0;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int puntos;
    private int puntaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_breakout);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle recibido = this.getIntent().getExtras();
        puntaje = recibido.getInt("puntaje");
        final int vidasJ = recibido.getInt("vidas");
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        try {
//            Bundle recibido = this.getIntent().getExtras();
//            puntaje = recibido.getString("puntaje");
//        } catch (Exception e) {
//        }

        mTextViewRecord = (TextView) findViewById(R.id.textViewRecord);
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewScore = (TextView) findViewById(R.id.textViewScore);
        mTextViewRes = (TextView) findViewById(R.id.textViewRes);

        if(vidasJ != 0){
            if (player == null){
                player = MediaPlayer.create(this, R.raw.watc);
            }
            player.start();
            getUserName();
            mTextViewRes.setText("Bien, terminaste el Juego!, te ganaste un aplauso");
            mTextViewScore.setText("Tu puntaje fue: " + puntaje);
            v.vibrate(800);

        }else{
            if (player == null){
                player = MediaPlayer.create(this, R.raw.nggyu);
            }
            player.start();
            getUserName();
            mTextViewRes.setText("Perdiste Amigo!");
            mTextViewScore.setText("Tu puntaje fue: " + puntaje);
            v.vibrate(2000);

        }

        //Aqui se realiza una consulta a la BD del puntaje guardado en el perfil del usuario---------------

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    //Se guarda el puntaje que esta en la BD en una variable "puntos", previamente declarada como int.
                    puntos = Integer.parseInt(snapshot.child("breakscore").getValue().toString());


                    //Se pregunta si el puntaje obtenido en el juego es mayor al de la BD.
                    if (puntaje > puntos){
                        //Si se cumple se llama al metodo que sube el nuevo puntaje.
                        notificacion();
                        createNotificacionChannel();
                        subirNuevoScore();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //---------------------------------------------------------------------------------------------------------


        mButtonBackBO = (Button) findViewById(R.id.btnBackBO);

        mButtonBackBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player != null){
                    player.release();
                    player = null;
                }
                startActivity(new Intent(PuntajeBreakout.this, MainActivity.class));
                finish();
            }
        });


    }

    private void getUserName(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mTextViewName.setText("Hola "+name+"!");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void subirNuevoScore(){

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    mTextViewRecord.setVisibility(View.VISIBLE);//HAce visible un textview que dice "NUEVO RECORD PERSONAL"
                    Map<String, Object> scoreMap = new HashMap<>();
                    scoreMap.put("breakscore", puntaje);//Se crear un Map y se le agrega el nuevo puntaje.

                    mDatabase.child("Users").child(id).updateChildren(scoreMap);//En esta linea se actualiza la info en la BD

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
        player.release();
    }
    public void notificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.first);
        builder.setContentText("SUPERASTE TU PUNTAJE EN: BREAKOUT ");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    private void createNotificacionChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}