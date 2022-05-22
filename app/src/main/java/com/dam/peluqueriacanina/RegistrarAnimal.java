package com.dam.peluqueriacanina;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
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

import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RegistrarAnimal extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistrarAnimal;
    EditText etNomAnimal, etRazaAnimal;
    ShapeableImageView ivFotoAnimal;
    TextView tvPonerMascota;
    String ruta;
    String nombre;
    String raza;
    Intent i;
    AnimalesDao dao;
    AnimalesDB db;
    //CharSequence[] opcion = {"Tomar foto", "Elegir de galeria", "Cancelar"};

    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Bitmap imgBitmap = BitmapFactory.decodeFile(ruta);
                        tvPonerMascota.setVisibility(View.INVISIBLE);
                        ivFotoAnimal.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(), R.color.color_principal)));
                        ivFotoAnimal.bringToFront();
                        ivFotoAnimal.setImageBitmap(imgBitmap);

                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_animal);

        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

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
                //TODO: Arreglar error si se rechaza mas de dos veces no sale mas el mensaje
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    File foto = null;
                    try {
                        foto = GuardarImagen();
                    } catch (IOException ex) {
                        Log.e("error", ex.toString());
                    }

                    if (foto != null) {
                        Uri uri = FileProvider.getUriForFile(this, "com.peluqueriacanina.mycamera.fileprovider", foto);
                        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        arl.launch(i);
                    }
                }
            }
        } else {
            nombre = etNomAnimal.getText().toString();
            raza = etRazaAnimal.getText().toString();

            if (nombre.isEmpty() || raza.isEmpty()) {
                Toast.makeText(this, R.string.error_registrar_animal_vacio, Toast.LENGTH_SHORT).show();
            } else {
                String key = ((MiApplication) getApplicationContext()).getKey();
                dao.insert(new Animal(key,ruta, nombre, raza));
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null) {
                File foto = null;
                try {
                    foto = GuardarImagen();
                } catch (IOException ex) {
                    Log.e("error", ex.toString());
                }

                if (foto != null) {
                    Uri uri = FileProvider.getUriForFile(this, "com.peluqueriacanina.mycamera.fileprovider", foto);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    arl.launch(i);
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Se debe dar permisos", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //TODO: Arreglar que si se pulsa muchas veces se sigue guardando, hay que hacer que se guarde solo en el registrar
    //TODO: Mira la pagina que esta en marcadores en el android studio

    private File GuardarImagen() throws IOException {
        String nombreFoto = "Foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File foto = File.createTempFile(nombreFoto, ".jpg", directorio);
        ruta = foto.getAbsolutePath();
        return foto;
    }

}