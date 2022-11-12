package com.vocesdelolimpo.triogb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSendToRegister;
    private String email = "";
    private String password = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonLogin = (Button) findViewById(R.id.btnLogin);
        mButtonSendToRegister = (Button) findViewById(R.id.btnSendToRegister);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonSendToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });
    }

    private void loginUser(){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Datos erroneos, o talvez no estas registrado! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }



}