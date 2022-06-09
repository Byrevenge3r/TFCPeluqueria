package com.dam.peluqueriacanina.mainActivity.perfil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.registro.LoginActivity;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AjustesActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cvCambiarContra, cvAcercaDe, cvPreguntasRespuestas, cvCerrarSesion;
    TextView tvUsuarioPerAjustes, tvCorreoPerAjustes;
    ImageView ivPerfilPelAjustes;
    Uri uri;
    StorageReference mStorage;
    FirebaseDatabase fb;
    DatabaseReference dbRef;
    FirebaseAuth fAuth;
    Intent i;


    ActivityResultLauncher<Intent> sForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        ivPerfilPelAjustes.setImageURI(uri);
                        StorageReference filePath = mStorage.child("fotosPerfil/" + ((MiApplication) getApplicationContext()).getKey() + "/fotoPerfil.jpg");
                        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mStorage.child("fotosPerfil/" + ((MiApplication) getApplicationContext()).getKey() + "/fotoPerfil.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        HashMap<String, Object> fotoPerfil = new HashMap<>();
                                        fotoPerfil.put("urlPerfil", uri.toString());
                                        ((MiApplication) getApplicationContext()).setUrlPerfil(uri.toString());
  //                                      dbRef.child("usuarios/" + ((MiApplication) getApplicationContext()).getKey()).updateChildren(fotoPerfil);

                                    }
                                });
                            }
                        });
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        mStorage = FirebaseStorage.getInstance().getReference();
        fb = FirebaseDatabase.getInstance();
        dbRef = fb.getReference();
        fAuth = FirebaseAuth.getInstance();

        cvCambiarContra = findViewById(R.id.cvCambiarContra);
        cvAcercaDe = findViewById(R.id.cvAcercaDe);
        cvPreguntasRespuestas = findViewById(R.id.cvPreguntasRespuestas);
        cvCerrarSesion = findViewById(R.id.cvCerrarSesion);

        tvUsuarioPerAjustes = findViewById(R.id.tvUsuarioPerAjustes);
        tvCorreoPerAjustes = findViewById(R.id.tvCorreoPerAjustes);

        tvUsuarioPerAjustes.setText(((MiApplication) getApplicationContext()).getUsuario());
        tvCorreoPerAjustes.setText(((MiApplication) getApplicationContext()).getCorreo());

        ivPerfilPelAjustes = findViewById(R.id.ivPerfilPelAjustes);
        if (!((MiApplication) getApplicationContext()).getUrlPerfil().isEmpty()) {
            Picasso.get().load(((MiApplication) getApplicationContext()).getUrlPerfil()).resize(153, 153).centerCrop().into(ivPerfilPelAjustes);

        }
        ivPerfilPelAjustes.setOnClickListener(this);
        cvCerrarSesion.setOnClickListener(this);
        cvPreguntasRespuestas.setOnClickListener(this);
        cvAcercaDe.setOnClickListener(this);
        cvCambiarContra.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(ivPerfilPelAjustes)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);

            } else {
                Intent data = new Intent(Intent.ACTION_PICK);
                data.setType("image/*");
                data = Intent.createChooser(data, "Choose File");
                sForResult.launch(data);
            }

        } else if (v.equals(cvCambiarContra)) {
            {
                Toast.makeText(this,
                        getString(R.string.msj_correço_enviado),
                        Toast.LENGTH_LONG).show();
                fAuth.sendPasswordResetEmail(fAuth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(AjustesActivity.this, R.string.tst_email_exist, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else if (v.equals(cvCerrarSesion)) {
            HashMap<String,Object> hmRecuerdame = new HashMap<>();
            hmRecuerdame.put("recuerdame",false);
            dbRef = fb.getReference("usuarios/"+((MiApplication)getApplicationContext()).getKey());
            dbRef.updateChildren(hmRecuerdame);
            logout();
            i = new Intent(this, LoginActivity.class);
            startActivity(i);
        } else if (v.equals(cvAcercaDe)) {
            i = new Intent(this, AcercaDeActivity.class);
            startActivity(i);
        } else if (v.equals(cvPreguntasRespuestas)) {
            i = new Intent(this, PreguntasRespuestasActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent data = new Intent(Intent.ACTION_PICK);
        data.setType("image/*");
        data = Intent.createChooser(data, "Choose File");
        sForResult.launch(data);
    }

    public void logout() {
        fAuth.signOut();
    }
}