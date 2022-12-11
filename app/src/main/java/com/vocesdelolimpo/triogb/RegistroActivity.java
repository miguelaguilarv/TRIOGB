package com.vocesdelolimpo.triogb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextAlias;
    private EditText mEditTextEdad;
    private EditText mEditTextPais;
    private EditText mEditTextPassword;
    private Button mButtonRegister;
    private Spinner paisesSpinner;

    private String name = "";
    private String email = "";
    private String password = "";
    private String alias = "";
    private String edad = "";
    private String pais = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextAlias = (EditText) findViewById(R.id.editTextAlias);
        mEditTextEdad = (EditText) findViewById(R.id.editTextEdad);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        paisesSpinner = (Spinner) findViewById(R.id.paises);

        ArrayAdapter<String> miAdaptador = new ArrayAdapter<String>(RegistroActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.paisesString));

        miAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paisesSpinner.setAdapter(miAdaptador);

        mButtonRegister = (Button) findViewById(R.id.btnRegister);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = mEditTextName.getText().toString();
                alias = mEditTextAlias.getText().toString();
                edad = mEditTextEdad.getText().toString();
                pais = mEditTextPais.getText().toString();
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !alias.isEmpty() && !edad.isEmpty() && !pais.isEmpty()){
                    if (password.length()>= 6){


                    }
                    else{

                        Toast.makeText(RegistroActivity.this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

                    }
                    registerUser();

                }
                else{

                    Toast.makeText(RegistroActivity.this, "Favor complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("alias",alias);
                    map.put("edad",edad);
                    map.put("pais",pais);
                    map.put("email",email);
                    map.put("password",password);
                    map.put("imagen","");



                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, ProfileActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(RegistroActivity.this, "No se creó el usuario", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else{
                    Toast.makeText(RegistroActivity.this, "No se pudo registrar usuario",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}