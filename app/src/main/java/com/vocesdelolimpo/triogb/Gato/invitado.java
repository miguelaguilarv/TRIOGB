package com.vocesdelolimpo.triogb.Gato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vocesdelolimpo.triogb.Breakout.PuntajeBreakout;
import com.vocesdelolimpo.triogb.MainActivity;
import com.vocesdelolimpo.triogb.Ordenamiento.Ordenamiento3Activity;
import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.List;

public class invitado extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    private Button mButtonComenzar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase2;
    MediaPlayer player;
    TextView tv_valor;
    ImageView caution;
    TextView user1;
    String jugador_2;
    String jugador_1;
    TextView prohibido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado);
        tv_valor = (TextView) findViewById(R.id.tv_valor );
        prohibido=(TextView) findViewById(R.id.prohibido);
        final Spinner s1 = findViewById(R.id.j_invitado);
        mAuth = FirebaseAuth.getInstance();
        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        user1 = (TextView) findViewById(R.id.user1);
        caution=(ImageView)findViewById(R.id.caution);
        fondo();
        mButtonComenzar = (Button) findViewById(R.id.btnBackBO);
        mButtonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datos();
                finish();
            }
        });

        getUserInfo();
        mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference ref1=mDatabase.getReference("Users");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> areas= new ArrayList<>();
                for(DataSnapshot areaSnapshot : dataSnapshot.getChildren()){
                    String areaName = areaSnapshot.child("alias").getValue(String.class);

                      areas.add(areaName);

                }
                ArrayAdapter<String> areasAdapter= new ArrayAdapter<String>(invitado.this, android.R.layout.simple_spinner_item,areas);
                areasAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                s1.setAdapter(areasAdapter);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getSelectedItem().toString();
                        tv_valor.setText(item);
                        jugador_2=item;

                        String id = mAuth.getCurrentUser().getUid();
                        user1 = (TextView) findViewById(R.id.user1);
                        mDatabase2.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String alias = snapshot.child("alias").getValue().toString();
                                    jugador_1 = alias;

                                    if (jugador_1 != jugador_2){
                                        prohibido.setVisibility(View.INVISIBLE);
                                        caution.setVisibility(View.INVISIBLE);
                                        mButtonComenzar.setVisibility(View.VISIBLE);

                                    }else{
                                        mButtonComenzar.setVisibility(View.INVISIBLE);
                                        prohibido.setVisibility(View.VISIBLE);
                                        caution.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });


                       // DatabaseReference n1 = mDatabase.getReference();
                       // Query q2 = n1.child("Users").orderByChild("name").equalTo(item);
                       // q2.addValueEventListener(new ValueEventListener() {
                       //     @Override
                       //     public void onDataChange(@NonNull DataSnapshot snapshot) {
                       //         for(DataSnapshot childsnapshot:dataSnapshot.getChildren()){
                       //             String catekey = childsnapshot.getKey();
                       //             tv_valor.setText(catekey);
                       //         }
                       //     }
                       //
                       //     @Override
                       //     public void onCancelled(@NonNull DatabaseError error) {
                       //
                       //     }
                       // });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void datos(){
         Intent in = new Intent(this, GatoActivity.class);
         Bundle d = new Bundle();
         d.putString("jugador2", jugador_2);
         in.putExtras(d);
         startActivity(in);
     }
    private void getUserInfo () {
        String id = mAuth.getCurrentUser().getUid();
        user1 = (TextView) findViewById(R.id.user1);
        mDatabase2.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String alias = snapshot.child("alias").getValue().toString();
                    jugador_1 = alias;
                    user1.setText(alias);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void fondo() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.versus);
        }
        player.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.release();
    }
}