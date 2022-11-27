package com.vocesdelolimpo.triogb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private TextView mTextViewAlias;
    private TextView mTextViewEdad;
    private TextView mTextViewPais;
    private Button mButtonSignout;
    private ImageButton mButtonImage;
    private Button mButtonBack;
    private Button mEdit;
    AppCompatImageView imagenPerfil;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference mDatabase;
    private StorageReference ReferenciaAlmacenamiento;
    private String RutaAlmacenamiento = "FotoPerfil/*";

    private static final int CODIGO_SOLICITUD_ALMACENAMIENTO = 200;
    private static final int CODIGO_SELECCION_IMAGEN= 300;

    private String [] PermisosAlmacenamiento;
    private Uri imagen_uri;
    private String perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        mButtonSignout = (Button) findViewById(R.id.btnSignout);
        mButtonBack = (Button) findViewById(R.id.btnBack);
        mEdit = (Button) findViewById(R.id.btnEdit);
        mButtonImage = (ImageButton) findViewById(R.id.btnImg);
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewAlias = (TextView) findViewById(R.id.textViewAlias);
        mTextViewEdad = (TextView) findViewById(R.id.textViewEdad);
        mTextViewPais = (TextView) findViewById(R.id.textViewPais);
        mTextViewEmail = (TextView) findViewById(R.id.textViewEmail);
        imagenPerfil = findViewById(R.id.imagenProfile);


        ReferenciaAlmacenamiento = FirebaseStorage.getInstance().getReference();
        PermisosAlmacenamiento = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        getUserInfo();


        //BOTONES

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



        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarProfile();
            }
        });

        mButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfil = "imagen";
                ActualizarFotoPerfil();
            }
        });




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
                    String imagen = snapshot.child("imagen").getValue().toString();

                    mTextViewName.setText("Nombre: "+name);
                    mTextViewAlias.setText("Alias: "+alias);
                    mTextViewEdad.setText("Edad: "+edad +" años");
                    mTextViewPais.setText("Pais: "+pais);
                    mTextViewEmail.setText("Correo: "+email);

                    try {
                        Picasso.get().load(imagen).into(imagenPerfil);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.userdef).into(imagenPerfil);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void EditarProfile(){
        String[] Opciones = {"Nombre","Alias","Edad","Pais","Clave"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(Opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    ActualizarNombre();
                }
                if(i == 1){
                    ActualizarAlias();
                }
                if(i == 2){
                    ActualizarEdad();
                }
                if(i == 3){
                    ActualizarPais();
                }
                if(i == 4){
                    ActualizarClave();
                }
            }
        });
        builder.create().show();

    }

    private void ActualizarFotoPerfil() {
            String[] opciones = {"Galeria"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seleccionar imagen desde: ");
            builder.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(i == 0){
                        if(!ComprobarAlmacenamiento()){
                            SolicitarPermisoAlm();
                        }else{
                           ElegirImagenDeGaleria();
                        }
                    }
                }
            });
            builder.create().show();
    }

    private boolean ComprobarAlmacenamiento() {
        boolean resultado = ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);
        return resultado;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case CODIGO_SOLICITUD_ALMACENAMIENTO:{
                if(grantResults.length > 0){
                    boolean EscrituraAlmacenamientoAceptado = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(EscrituraAlmacenamientoAceptado){
                        ElegirImagenDeGaleria();
                    }else {
                        Toast.makeText(this, "Habilita el permiso de la galeria, man", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if (requestCode == CODIGO_SELECCION_IMAGEN){
                imagen_uri = data.getData();
                SubirFoto(imagen_uri);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SubirFoto(Uri imagen_uri) {
        String RutaArchivoNombre = RutaAlmacenamiento + "" +perfil+"_"+user.getUid();
        StorageReference storageReference = ReferenciaAlmacenamiento.child(RutaArchivoNombre);
        storageReference.putFile(imagen_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloaduri = uriTask.getResult();

                            if (uriTask.isSuccessful()){
                                HashMap<String,Object> resultado = new HashMap<>();
                                resultado.put(perfil,downloaduri.toString());
                                mDatabase.child("Users").child(user.getUid()).updateChildren(resultado)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(ProfileActivity.this, "Se subio la imagen",Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener((e)->{
                                            Toast.makeText(ProfileActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                                });
                            }else{
                                Toast.makeText(ProfileActivity.this,"Algo malio sal" ,Toast.LENGTH_SHORT).show();
                            }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Algo Malio Sal", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void ElegirImagenDeGaleria() {
        Intent IntentGaleria = new Intent(Intent.ACTION_PICK);
        IntentGaleria.setType("image/*");
        startActivityForResult(IntentGaleria, CODIGO_SELECCION_IMAGEN);

    }

    private void SolicitarPermisoAlm() {
        requestPermissions(PermisosAlmacenamiento, CODIGO_SOLICITUD_ALMACENAMIENTO);
    }


    private void ActualizarNombre() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar Nombre");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        EditText editText = new EditText(this);
        editText.setHint("Ingresa Nombre");
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String valor = editText.getText().toString().trim();
                HashMap<String, Object> result = new HashMap<>();
                result.put("name", valor);
                mDatabase.child("Users").child(user.getUid()).updateChildren(result)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this,"Info Actualizada", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ProfileActivity.this,"Cancela3", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void ActualizarAlias() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar Alias");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        EditText editText = new EditText(this);
        editText.setHint("Ingresa Alias");
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String valor = editText.getText().toString().trim();
                HashMap<String, Object> result = new HashMap<>();
                result.put("alias", valor);
                mDatabase.child("Users").child(user.getUid()).updateChildren(result)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this,"Info Actualizada", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ProfileActivity.this,"Cancela3", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }
    private void ActualizarPais() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar Pais");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        EditText editText = new EditText(this);
        editText.setHint("Ingresa Pais");
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String valor = editText.getText().toString().trim();
                HashMap<String, Object> result = new HashMap<>();
                result.put("pais", valor);
                mDatabase.child("Users").child(user.getUid()).updateChildren(result)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this,"Info Actualizada", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ProfileActivity.this,"Cancela3", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }
    private void ActualizarEdad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar Edad");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        EditText editText = new EditText(this);
        editText.setHint("Ingresa Edad");
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String valor = editText.getText().toString().trim();
                HashMap<String, Object> result = new HashMap<>();
                result.put("edad", valor);
                mDatabase.child("Users").child(user.getUid()).updateChildren(result)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this,"Info Actualizada", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ProfileActivity.this,"Cancela3", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }
    private void ActualizarClave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar Contraseña");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        EditText editText = new EditText(this);
        editText.setHint("Ingresa Contraseña");
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String valor = editText.getText().toString().trim();
                HashMap<String, Object> result = new HashMap<>();
                result.put("password", valor);
                mDatabase.child("Users").child(user.getUid()).updateChildren(result)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this,"Info Actualizada", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ProfileActivity.this,"Cancela3", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }



}