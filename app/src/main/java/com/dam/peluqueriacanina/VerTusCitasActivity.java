package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.utils.AnimalPeluAdapter;

import java.util.ArrayList;

public class VerTusCitasActivity extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager llm;
    TusCitasDao daoTusCitas;
    TusCitasDB dbTusCitas;
    TextView tvNoHayCitasVTC;
    AnimalPeluAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tus_citas);

        dbTusCitas = TusCitasDB.getDatabase(this);
        daoTusCitas = dbTusCitas.citaDao();

        rv = findViewById(R.id.rvVerTusCitas);
        llm = new LinearLayoutManager(this);
        tvNoHayCitasVTC = findViewById(R.id.tvNoHayCitasVTC);

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);

        if (!daoTusCitas.sacarTodo().isEmpty()) {
            adapter = new AnimalPeluAdapter((ArrayList<TusCitas>) daoTusCitas.sacarTodo());
            adapter.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: borrar cita de las bases de datos dao y del firebase
                }
            });
        } else {
            tvNoHayCitasVTC.setVisibility(View.VISIBLE);
        }

        rv.setAdapter(adapter);



    }
}