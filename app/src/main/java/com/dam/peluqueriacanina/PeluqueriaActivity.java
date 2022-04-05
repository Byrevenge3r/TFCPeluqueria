package com.dam.peluqueriacanina;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.model.DatosAnimalesPruebaReciclerView;
import com.dam.peluqueriacanina.utils.AnimalPeluAdapter;

public class PeluqueriaActivity extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager llm;
    AnimalPeluAdapter adapter;
    TextView tvTamCitas;
    DatosAnimalesPruebaReciclerView datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peluqueria);

        tvTamCitas = findViewById(R.id.tvTamCitas);

        rv = findViewById(R.id.rvReservarVet);


        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        datos = new DatosAnimalesPruebaReciclerView();

        adapter = new AnimalPeluAdapter(datos.getListaAnimales());
        if (datos.getListaAnimales().isEmpty()) {
            tvTamCitas.setText(getResources().getString(R.string.tv_tam_citas));
        } else {
            rv.setAdapter(adapter);
        }
    }
}