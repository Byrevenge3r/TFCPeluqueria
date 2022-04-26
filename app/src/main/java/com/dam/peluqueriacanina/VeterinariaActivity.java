package com.dam.peluqueriacanina;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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

public class VeterinariaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    LinearLayoutManager llm;
    MisAnimalesAdapter adapter;
    AnimalesDao dao;
    AnimalesDB db;
    ShapeableImageView imagenAnimal;
    Button btnAniadirMascotaVet;
    Intent i;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinaria);

        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        imagenAnimal = findViewById(R.id.siAnimal);
        btnAniadirMascotaVet = findViewById(R.id.btnAniadirMascotaVet);
        rv = findViewById(R.id.rvReservarVet);


        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);

        if (!dao.sacarTodo().isEmpty()) {
            adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarTodo());
        }

        rv.setAdapter(adapter);

        btnAniadirMascotaVet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAniadirMascotaVet)) {
            i = new Intent(this,RegistrarAnimal.class);
            arl.launch(i);
        }
    }
}