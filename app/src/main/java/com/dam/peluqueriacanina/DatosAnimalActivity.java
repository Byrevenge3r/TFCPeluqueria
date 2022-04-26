package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;

public class DatosAnimalActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvDatosNomAnimal, tvDatosRazaAnimal;
    ShapeableImageView ivfotoDatosAnimal;
    Button btnDatosAnimalBorrar;
    AnimalesDao dao;
    AnimalesDB db;
    Animal animal;
    File foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_animal);

        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        //TODO: esto es una porqueria hay que filtrar por clases estoy cansado asi que ya mañana
        if (dao.sacarAnimal(getIntent().getStringExtra(PeluqueriaActivity.CLAVE_ANIMAL)) != null) {
            animal = dao.sacarAnimal(getIntent().getStringExtra(PeluqueriaActivity.CLAVE_ANIMAL));
        } else {
            animal = dao.sacarAnimal(getIntent().getStringExtra(VeterinariaActivity.CLAVE_ANIMAL));
        }

        tvDatosNomAnimal = findViewById(R.id.tvDatosNomAnimal);
        tvDatosRazaAnimal = findViewById(R.id.tvDatosRazaAnimal);
        ivfotoDatosAnimal = findViewById(R.id.ivfotoDatosAnimal);
        btnDatosAnimalBorrar = findViewById(R.id.btnDatosAnimalBorrar);

        ivfotoDatosAnimal.setImageBitmap(BitmapFactory.decodeFile(animal.getRuta()));
        tvDatosNomAnimal.setText(String.format(getResources().getString(R.string.tv_datos_nom_animal),animal.getNombre()));
        tvDatosRazaAnimal.setText(String.format(getResources().getString(R.string.tv_datos_raza_animal),animal.getRaza()));

        btnDatosAnimalBorrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dao.delete(animal);
        foto = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] fotos = foto.listFiles();

        for (int i = 0; i < fotos.length; i++) {
            if (fotos[i].getAbsolutePath().equals(animal.getRuta())) {
                fotos[i].delete();
                i = fotos.length;
            }
        }
        setResult(RESULT_OK);
        finish();
    }
}