package com.vocesdelolimpo.triogb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private TextView mTextViewAlias;
    private TextView mTextViewEdad;
    private TextView mTextViewPais;
    private Button mButtonSignout;
    private Button mButtonBack;
    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonSignout = (Button) findViewById(R.id.btnSignout);
        mButtonBack = (Button) findViewById(R.id.btnBack);
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewAlias = (TextView) findViewById(R.id.textViewAlias);
        mTextViewEdad = (TextView) findViewById(R.id.textViewEdad);
        mTextViewPais = (TextView) findViewById(R.id.textViewPais);
        mTextViewEmail = (TextView) findViewById(R.id.textViewEmail);

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        mButtonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });
        getUserInfo();
    }




    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String alias = snapshot.child("alias").getValue().toString();
                    String edad = snapshot.child("edad").getValue().toString();
                    String pais = snapshot.child("pais").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();

                    mTextViewName.setText("Nombre: "+name);
                    mTextViewAlias.setText("Alias: "+alias);
                    mTextViewEdad.setText("Edad: "+edad);
                    mTextViewPais.setText("Pais: "+pais);
                    mTextViewEmail.setText("Email: "+email);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}