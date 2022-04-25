package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dam.peluqueriacanina.model.DatosMisAnimales;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.material.imageview.ShapeableImageView;

public class PeluqueriaActivity extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager llm;
    MisAnimalesAdapter adapter;
    ShapeableImageView imagenAnimal;
    DatosMisAnimales datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peluqueria);

        imagenAnimal = findViewById(R.id.siAnimal);

        rv = findViewById(R.id.rvReservarVet);


        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        datos = new DatosMisAnimales();

        adapter = new MisAnimalesAdapter(datos.getListaAnimales());

        rv.setAdapter(adapter);
    }
}