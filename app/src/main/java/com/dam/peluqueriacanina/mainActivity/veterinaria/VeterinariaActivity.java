package com.dam.peluqueriacanina.mainActivity.veterinaria;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.mainActivity.peluqueria.DatosAnimalActivity;
import com.dam.peluqueriacanina.mainActivity.peluqueria.RegistrarAnimal;
import com.dam.peluqueriacanina.mainActivity.veterinaria.citas.VerCitasVetActivity;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class VeterinariaActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String CLAVE_ANIMAL = "ANIMAL_VET";

    RecyclerView rv;
    LinearLayoutManager llm;
    MisAnimalesAdapter adapter;
    AnimalesDao dao;
    AnimalesDB db;
    Animal animalVet;
    ShapeableImageView imagenAnimal;
    Button btnAniadirMascotaVet;
    ArrayList<Animal> listaAnimalesVet;
    Intent i;
    TusCitas tusCitas;
    CardView cvVeterinaria,cvTusCitasVet;

    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey()));
                        listenerRv();
                        rv.setAdapter(adapter);
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinaria);

        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        imagenAnimal = findViewById(R.id.siAnimal);
        btnAniadirMascotaVet = findViewById(R.id.btnAniadirMascotaVet);
        rv = findViewById(R.id.rvReservarVet);
        cvVeterinaria = findViewById(R.id.cvVeterinaria);
        cvTusCitasVet = findViewById(R.id.cvTusCitasVet);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);

        if (!dao.sacarTodo().isEmpty()) {
            adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarTodo());
            listenerRv();
        }

        rv.setAdapter(adapter);

        btnAniadirMascotaVet.setOnClickListener(this);
        cvVeterinaria.setOnClickListener(this);
        cvTusCitasVet.setOnClickListener(this);
    }

    private void listenerRv() {
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaAnimalesVet = (ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey());
                i = new Intent(VeterinariaActivity.this, DatosAnimalActivity.class);
                animalVet = listaAnimalesVet.get(rv.getChildAdapterPosition(v));
                //i.putExtra(CLAVE_ANIMAL, animalVet.getRuta());
                arl.launch(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAniadirMascotaVet)) {
            i = new Intent(this, RegistrarAnimal.class);
            arl.launch(i);
        } else if (v.equals(cvVeterinaria)) {
            i = new Intent(this, MapaVeterinariasActivity.class);
            startActivity(i);
        } else if (v.equals(cvTusCitasVet)) {
            i = new Intent(this, VerCitasVetActivity.class);
            startActivity(i);
        }
    }
}