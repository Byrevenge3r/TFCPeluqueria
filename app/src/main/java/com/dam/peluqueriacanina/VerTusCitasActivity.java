package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.model.BotonTusCitas;
import com.dam.peluqueriacanina.model.datos.BotonTusCitasLista;
import com.dam.peluqueriacanina.utils.AnimalPeluAdapter;
import com.dam.peluqueriacanina.utils.MostrarDatosTusCitasAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class VerTusCitasActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    RecyclerView rvVerCita;
    LinearLayoutManager llm;
    LinearLayoutManager llmDetalles;
    TusCitas tusCitas;
    String mesN = "";
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    TusCitasDao daoTusCitas;
    TusCitasDB dbTusCitas;
    TextView tvNoHayCitasVTC;
    Button btnCancelarCita;
    AnimalPeluAdapter adapter;
    MostrarDatosTusCitasAdapter adapterDetallesCita;
    SimpleDateFormat formatter;
    Date fecha;
    View vista;
    Calendar cal;
    int mesActual = 0;
    BotonTusCitasLista boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tus_citas);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();

        dbTusCitas = TusCitasDB.getDatabase(this);
        daoTusCitas = dbTusCitas.citaDao();

        boton = new BotonTusCitasLista();

        rv = findViewById(R.id.rvVerTusCitas);
        llmDetalles = new LinearLayoutManager(this);
        llm = new LinearLayoutManager(this);

        tvNoHayCitasVTC = findViewById(R.id.tvNoHayCitasVTC);
        btnCancelarCita = findViewById(R.id.btnCancelarCita);

        llmDetalles.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        fecha = new Date();

        btnCancelarCita.setOnClickListener(this);
        vista = new View(this);

        adapter = new AnimalPeluAdapter((ArrayList<TusCitas>) daoTusCitas.sacarTodo());
        adapterDetallesCita = new MostrarDatosTusCitasAdapter(boton.getBoton());
        rv.setAdapter(adapter);

        if (daoTusCitas.sacarTodo().isEmpty()) {
            tvNoHayCitasVTC.setVisibility(View.VISIBLE);
        }


        /*LinearLayoutManager llm = new LinearLayoutManager(
                rv.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        rv.setLayoutManager(llm);
        BotonTusCitasLista lista = new BotonTusCitasLista();
        MostrarDatosTusCitasAdapter datosCita = new MostrarDatosTusCitasAdapter(lista.getBoton());
        rv.setAdapter(datosCita);

        rv.getChildAdapterPosition(v);
        datosCita.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(v.getContext(), "Posicion"+datosCita.getItemId(rv.getChildAdapterPosition(v)),Toast.LENGTH_LONG).show();
            }
        });*/

        //TODO: Activar el segundo boton para ir al segundo activity
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //setContentView(R.layout.citas_recicler_view_vet);

                rvVerCita = findViewById(R.id.rvVerTusCitasSegundaPantalla);
                btnCancelarCita.setVisibility(View.VISIBLE);
                rvVerCita.setLayoutManager(llmDetalles);

                rvVerCita.setAdapter(adapterDetallesCita);

                vista = view;
            }
        });


    }

    @Override
    public void onClick(View view) {
        tusCitas = daoTusCitas.sacarTodo().get(rv.getChildAdapterPosition(vista));
        try {
            fecha = formatter.parse(tusCitas.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();
        cal.setTime(fecha);
        mesActual = cal.get(Calendar.MONTH)+1;

        switch (mesActual) {
            case 1:
                mesN = "enero";
                break;
            case 2:
                mesN = "febrero";
                break;
            case 3:
                mesN = "marzo";
                break;
            case 4:
                mesN = "abril";
                break;
            case 5:
                mesN = "mayo";
                break;
            case 6:
                mesN = "junio";
                break;
            case 7:
                mesN = "julio";
                break;
            case 8:
                mesN = "agosto";
                break;
            case 9:
                mesN = "septiembre";
                break;
            case 10:
                mesN = "octubre";
                break;
            case 11:
                mesN = "noviembre";
                break;
            case 12:
                mesN = "diciembre";
                break;
        }

        dbr = fdb.getReference("coche/reservas/"+mesN);

        dbr.child(tusCitas.getKey()).removeValue();
        daoTusCitas.delete(tusCitas);

        adapter = new AnimalPeluAdapter((ArrayList<TusCitas>) daoTusCitas.sacarTodo());

        if (daoTusCitas.sacarTodo().isEmpty()) {
            tvNoHayCitasVTC.setVisibility(View.VISIBLE);
        }

        dbr = fdb.getReference("coche/reservas/"+mesN);
        btnCancelarCita.setVisibility(View.INVISIBLE);
        rv.setAdapter(adapter);
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelarCita.setVisibility(View.VISIBLE);
                vista = v;
            }
        });
    }
}