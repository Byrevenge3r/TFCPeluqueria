package com.dam.peluqueriacanina.peluqueria;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.model.datos.BotonTusCitasLista;
import com.dam.peluqueriacanina.utils.AnimalPeluAdapter;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.dam.peluqueriacanina.utils.MostrarDatosTusCitasAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    Date fechaHoy;
    View vista;
    Calendar cal;
    int mesActual = 0;
    BotonTusCitasLista boton;
    Intent i;
    LocalDateTime now;

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

        //Cambuiar por el otre
        adapter = new AnimalPeluAdapter((ArrayList<TusCitas>) daoTusCitas.sacarCitasKey(((MiApplication) getApplicationContext()).getKey()));
        adapterDetallesCita = new MostrarDatosTusCitasAdapter(boton.getBoton());
        rv.setAdapter(adapter);

        if (daoTusCitas.sacarTodo().isEmpty()) {
            tvNoHayCitasVTC.setVisibility(View.VISIBLE);
        }

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.llCitasVet:

                        if (vista != null && vista.findViewById(R.id.rlContainer) != null) {
                            rvVerCita = vista.findViewById(R.id.rlContainer).findViewById(R.id.rvVerTusCitasSegundaPantalla);
                            rvVerCita.removeAllViews();
                            rvVerCita.removeAllViewsInLayout();
                        }

                        rvVerCita = view.findViewById(R.id.rlContainer).findViewById(R.id.rvVerTusCitasSegundaPantalla);
                        rvVerCita.setLayoutManager(new LinearLayoutManager(view.findViewById(R.id.llCitasVet).getContext()));
                        rvVerCita.setAdapter(adapterDetallesCita);

                        adapterDetallesCita.setListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tusCitas = daoTusCitas.sacarCitasKey(((MiApplication) getApplicationContext()).getKey()).get(rv.getChildAdapterPosition(vista));
                                try {
                                    fecha = formatter.parse(tusCitas.getFecha());
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        now = LocalDateTime.now();
                                        fechaHoy = formatter.parse(now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear());
                                    }
                                   // if (fecha.equals(fechaHoy)) {
                                        i = new Intent(VerTusCitasActivity.this, VerDatosTusCitasActivity.class);
                                        i.putExtra("hora", tusCitas.getHora());

                                        startActivity(i);
                                   // } else {
                                   //     Toast.makeText(VerTusCitasActivity.this, R.string.fecha_distinta_hoy, Toast.LENGTH_SHORT).show();
                                    //}
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                        btnCancelarCita.setVisibility(View.VISIBLE);

                        vista = view;

                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        tusCitas = daoTusCitas.sacarCitasKey(((MiApplication) getApplicationContext()).getKey()).get(rv.getChildAdapterPosition(vista));
        try {
            fecha = formatter.parse(tusCitas.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();
        cal.setTime(fecha);
        mesActual = cal.get(Calendar.MONTH) + 1;

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

        dbr = fdb.getReference("coche/reservas/" + mesN);

        dbr.child(tusCitas.getKey()).removeValue();
        daoTusCitas.delete(tusCitas);

        adapter = new AnimalPeluAdapter((ArrayList<TusCitas>) daoTusCitas.sacarCitasKey(((MiApplication) getApplicationContext()).getKey()));

        if (daoTusCitas.sacarTodo().isEmpty()) {
            tvNoHayCitasVTC.setVisibility(View.VISIBLE);
        }

        dbr = fdb.getReference("coche/reservas/" + mesN);
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