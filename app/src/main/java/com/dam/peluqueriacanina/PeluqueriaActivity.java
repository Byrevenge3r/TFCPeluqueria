package com.dam.peluqueriacanina;

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

import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class PeluqueriaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CLAVE_ANIMAL = "ANIMAL_PEL";

    RecyclerView rv;
    LinearLayoutManager llm;
    MisAnimalesAdapter adapter;
    AnimalesDao dao;
    AnimalesDB db;
    Animal animalPel;
    ShapeableImageView imagenAnimal;
    Button btnAniadirMascotaPel;
    CardView cvUbicacionTiempoReal;
    Intent i;
    ArrayList<Animal> listaAnimalesPel;

    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarTodo());
                        rv.setAdapter(adapter);
                    }
                }
            }
    );
    //TODO: ns quiero que me deje updatear
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peluqueria);

        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        imagenAnimal = findViewById(R.id.siAnimal);
        btnAniadirMascotaPel = findViewById(R.id.btnAniadirMascotaPel);
        rv = findViewById(R.id.rvReservarPel);
        cvUbicacionTiempoReal = findViewById(R.id.cvUbicacionTiempoReal);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);

        if (!dao.sacarTodo().isEmpty()) {
            adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarTodo());

            adapter.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaAnimalesPel = (ArrayList<Animal>) dao.sacarTodo();
                    i = new Intent(PeluqueriaActivity.this, DatosAnimalActivity.class);
                    animalPel = listaAnimalesPel.get(rv.getChildAdapterPosition(v));
                    i.putExtra(CLAVE_ANIMAL, animalPel.getRuta());
                    arl.launch(i);
                }
            });
        }

        rv.setAdapter(adapter);

        btnAniadirMascotaPel.setOnClickListener(this);
        cvUbicacionTiempoReal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAniadirMascotaPel)) {
            i = new Intent(this,RegistrarAnimal.class);
            arl.launch(i);
        } else if (v.equals(cvUbicacionTiempoReal)) {
            i = new Intent(this,UbicacionTiempoRealActivity.class);
            startActivity(i);
        }
    }
}