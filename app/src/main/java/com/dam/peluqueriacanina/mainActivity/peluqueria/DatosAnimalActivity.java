package com.dam.peluqueriacanina.mainActivity.peluqueria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.mainActivity.veterinaria.VeterinariaActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class DatosAnimalActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvDatosNomAnimal, tvDatosRazaAnimal;
    ShapeableImageView ivfotoDatosAnimal;
    Button btnDatosAnimalBorrar;
    AnimalesDao dao;
    AnimalesDB db;
    Animal animal;

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

        Picasso.get().load(animal.getUrlI()).resize(200, 200).centerCrop().into(ivfotoDatosAnimal);
        tvDatosNomAnimal.setText(String.format(getResources().getString(R.string.tv_datos_nom_animal), animal.getNombre()));
        tvDatosRazaAnimal.setText(String.format(getResources().getString(R.string.tv_datos_raza_animal), animal.getRaza()));

        btnDatosAnimalBorrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dao.delete(animal);
        setResult(RESULT_OK);
        finish();
    }
}