package com.vocesdelolimpo.triogb.Ordenamiento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.Breakout.PuntajeBreakout;
import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Decision extends AppCompatActivity {
    MediaPlayer player;
    private TextView tiempo;
    private TextView puntajeTV;
    int minutes;
    int seconds;
    private TextView TextViewGanador2;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView nuevo_Record;
    private Button salir;
    TextView record;
    private String ordenScore;
    TextView timerTextView;
    TextView mostrarptj;
    private int puntosBD;
    int puntaje;
    private final static String CHANNEL_ID ="NOTIFICACION";
    private final static int NOTIFICATION_ID= 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nuevo_Record = (TextView) findViewById(R.id.nuevo_Record);
        TextViewGanador2 = (TextView) findViewById(R.id.inf_1);
        salir = (Button) findViewById(R.id.buttonAtras);
        tiempo = (TextView) findViewById(R.id.tiempo);
        puntajeTV = (TextView) findViewById(R.id.puntaje);
        Bundle b = this.getIntent().getExtras();
        minutes = b.getInt("creonometro_minuto");
        seconds = b.getInt("creonometro_segundo");
        puntaje = b.getInt("puntaje_4");
        record = (TextView) findViewById(R.id.record);
        ordenScore = String.format("%02d:%02d", minutes, seconds);
        tiempo.setText("Tu tiempo fue: "+ordenScore);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);

        //record.setText(ordenScore);

        getUserInfo();

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                startActivity(new Intent(Decision.this, MainActivity.class));
                finish();
                mp.release();
            }
        });

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //Se guarda el puntaje que esta en la BD en una variable "puntos", previamente declarada como int.
                    puntosBD = Integer.parseInt(snapshot.child("ordenscore").getValue().toString());
                    //Se pregunta si el puntaje obtenido en el juego es mayor al de la BD.

                    if (puntaje > puntosBD){
                        //Si se cumple se llama al metodo que sube el nuevo puntaje.
                        notificacion();
                        nuevo_Record.setVisibility(View.VISIBLE);
                        createNotificacionChannel();
                        subirNuevoScore();
                        record.setText(""+puntaje);
                        puntajeTV.setVisibility(View.INVISIBLE);

                    }
                    puntajeTV.setText("Tu puntaje fue: "+puntaje);
                    //record.setText(puntosBD+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void getUserInfo () {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String alias = snapshot.child("alias").getValue().toString();
                    TextViewGanador2.setText(alias);
                    //subirNuevoScore();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void subirNuevoScore() {

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    //mTextViewRecord.setVisibility(View.VISIBLE);//HAce visible un textview que dice "NUEVO RECORD PERSONAL"
                    Map<String, Object> scoreMap = new HashMap<>();
                    scoreMap.put("ordenscore", puntaje);//Se crear un Map y se le agrega el nuevo puntaje.

                    mDatabase.child("Users").child(id).updateChildren(scoreMap);//En esta linea se actualiza la info en la BD

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void notificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.first);
        builder.setContentText("SUPERASTE TU PUNTAJE EN: ORDENAMIENTO ");
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
