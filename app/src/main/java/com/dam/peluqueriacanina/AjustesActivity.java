package com.dam.peluqueriacanina;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AjustesActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cvCambiarContra,cvAcercaDe,cvPreguntasRespuestas,cvCerrarSesion;
    TextView tvUsuarioPerAjustes,tvCorreoPerAjustes;
    ImageView ivPerfilPelAjustes;
    Uri uri;
    StorageReference mStorage;
    FirebaseDatabase fb;
    DatabaseReference dbRef;

    ActivityResultLauncher<Intent> sForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        ivPerfilPelAjustes.setImageURI(uri);
                        StorageReference filePath = mStorage.child("fotosPerfil/"+((MiApplication) getApplicationContext()).getKey()+"/fotoPerfil.jpg");
                        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 mStorage.child("fotosPerfil/"+((MiApplication) getApplicationContext()).getKey()+"/fotoPerfil.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                     @Override
                                     public void onSuccess(Uri uri) {
                                         HashMap<String,Object> fotoPerfil = new HashMap<>();
                                         fotoPerfil.put("urlPerfil",uri.toString());

                                         dbRef.child("usuarios/"+((MiApplication) getApplicationContext()).getKey()+"/urlPerfil").updateChildren(fotoPerfil);

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

        cvCambiarContra = findViewById(R.id.cvCambiarContra);
        cvAcercaDe = findViewById(R.id.cvAcercaDe);
        cvPreguntasRespuestas = findViewById(R.id.cvPreguntasRespuestas);
        cvCerrarSesion = findViewById(R.id.cvCerrarSesion);

        tvUsuarioPerAjustes = findViewById(R.id.tvUsuarioPerAjustes);
        tvCorreoPerAjustes = findViewById(R.id.tvCorreoPerAjustes);

        tvUsuarioPerAjustes.setText(((MiApplication) getApplicationContext()).getUsuario());
        tvCorreoPerAjustes.setText(((MiApplication) getApplicationContext()).getCorreo());

        ivPerfilPelAjustes = findViewById(R.id.ivPerfilPelAjustes);
        Picasso.get().load(((MiApplication) getApplicationContext()).getUrlPerfil()).resize(153,153).centerCrop().into(ivPerfilPelAjustes);

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
}