package com.dam.peluqueriacanina.mainActivity.peluqueria;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RegistrarAnimal extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistrarAnimal;
    EditText etNomAnimal, etRazaAnimal;
    ShapeableImageView ivFotoAnimal;
    TextView tvPonerMascota;
    String ruta;
    String nombre;
    String raza;
    Intent i;
    StorageReference mStorage;
    FirebaseDatabase fb;
    DatabaseReference dbRef;
    FirebaseAuth fAuth;
    Uri uri;

    ActivityResultLauncher<Intent> sForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        ivFotoAnimal.setImageURI(uri);
                        tvPonerMascota.setVisibility(View.INVISIBLE);

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_animal);

        fb = FirebaseDatabase.getInstance();
        dbRef = fb.getReference();
        fAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference("fotos/"+((MiApplication) getApplicationContext()).getKey()+"/");

        btnRegistrarAnimal = findViewById(R.id.btnRegistrarAnimal);
        etNomAnimal = findViewById(R.id.etNomAnimal);
        etRazaAnimal = findViewById(R.id.etRazaAnimal);
        ivFotoAnimal = findViewById(R.id.ivFotoAnimal);
        tvPonerMascota = findViewById(R.id.tvPonerMascota);

        ivFotoAnimal.setClickable(true);
        btnRegistrarAnimal.setOnClickListener(this);
        ivFotoAnimal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(ivFotoAnimal)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);

            } else {
                Intent data = new Intent(Intent.ACTION_PICK);
                data.setType("image/*");
                data = Intent.createChooser(data, "Choose File");
                sForResult.launch(data);
            }

        } else {
            nombre = etNomAnimal.getText().toString().trim();
            raza = etRazaAnimal.getText().toString().trim();

            if (nombre.isEmpty() || raza.isEmpty()) {
                Toast.makeText(this, R.string.error_registrar_animal_vacio, Toast.LENGTH_SHORT).show();
            } else {
                String uid = fAuth.getUid();
                String keyF = dbRef.push().getKey();
                StorageReference filePath = mStorage.child(keyF+".jpg");

                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        HashMap<String,Object> animal = new HashMap<>();

                        animal.put("key",keyF);
                        animal.put("nombre",nombre);
                        animal.put("raza",raza);

                        dbRef.child("usuarios/"+((MiApplication) getApplicationContext()).getKey()+"/animales/"+keyF).updateChildren(animal);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        }
    }
}