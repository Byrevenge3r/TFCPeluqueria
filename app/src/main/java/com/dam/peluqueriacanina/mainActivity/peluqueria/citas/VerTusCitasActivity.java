package com.dam.peluqueriacanina.mainActivity.peluqueria.citas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.model.BotonTusCitas;
import com.dam.peluqueriacanina.model.datos.BotonTusCitasLista;
import com.dam.peluqueriacanina.utils.AnimalPeluAdapter;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.dam.peluqueriacanina.utils.MostrarDatosTusCitasAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
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

    ArrayList<TusCitas> listaCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tus_citas);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();

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

        listaCitas = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            try {
                fechaHoy = formatter.parse(now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        dbr.child("usuarios/" + ((MiApplication) getApplicationContext()).getKey() + "/reservasCoche").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                listaCitas.clear();
                for (DataSnapshot sp : task.getResult().getChildren()) {
                    listaCitas.add(new TusCitas(sp.getValue(TusCitas.class).getUrlI(),
                            sp.getValue(TusCitas.class).getKey(),
                            sp.getValue(TusCitas.class).getKeyE(),
                            sp.getValue(TusCitas.class).getKeyEC(),
                            sp.getValue(TusCitas.class).getNomAnimal(),
                            sp.getValue(TusCitas.class).getCitaFecha(),
                            sp.getValue(TusCitas.class).getCitaHora()));
                    try {
                        if (fechaHoy.after(formatter.parse(listaCitas.get(listaCitas.size() - 1).getCitaFecha()))) {
                            String mesN = "";
                            tusCitas = listaCitas.get(listaCitas.size() - 1);
                            String[] mesCita = tusCitas.getCitaFecha().split("/");
                            switch (Integer.parseInt(mesCita[1])) {
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

                            dbr = fdb.getReference("coche/reservas/" + mesN + "/" + ((MiApplication) getApplicationContext()).getKey() + "/");
                            dbr.child(tusCitas.getKeyEC()).removeValue();

                            dbr = fdb.getReference("usuarios/" + ((MiApplication) getApplicationContext()).getKey() + "/reservasCoche/");
                            dbr.child(tusCitas.getKeyE()).removeValue();

                            listaCitas.remove(tusCitas);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new AnimalPeluAdapter(listaCitas);
                adapterDetallesCita = new MostrarDatosTusCitasAdapter(boton.getBoton());
                rv.setAdapter(adapter);
                if (listaCitas.isEmpty()) {
                    tvNoHayCitasVTC.setVisibility(View.VISIBLE);
                }
                pasarAcitivtyDatos();
            }
        });
    }

    private void pasarAcitivtyDatos() {
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.llCitas:

                        rvVerCita = view.findViewById(R.id.rlContainer).findViewById(R.id.rvVerTusCitasSegundaPantalla);
                        rvVerCita.setLayoutManager(new LinearLayoutManager(view.findViewById(R.id.llCitas).getContext()));

                        rvVerCita.setAdapter(adapterDetallesCita);

                        adapterDetallesCita.setListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tusCitas = listaCitas.get(rv.getChildAdapterPosition(vista));
                                try {
                                    fecha = formatter.parse(tusCitas.getCitaFecha());

                                    // if (fecha.equals(fechaHoy)) {
                                    i = new Intent(VerTusCitasActivity.this, VerDatosTusCitasActivity.class);
                                    i.putExtra("hora", tusCitas.getCitaHora());

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
        tusCitas = listaCitas.get(rv.getChildAdapterPosition(vista));
        try {
            fecha = formatter.parse(tusCitas.getCitaFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();
        cal.setTime(fecha);
        mesActual = cal.get(Calendar.MONTH) + 1;
        String[] fechaA = tusCitas.getCitaFecha().split("/");
        switch (Integer.parseInt(fechaA[1])) {
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

        dbr = fdb.getReference("coche/reservas/" + mesN + "/");
        dbr.child(tusCitas.getKeyEC()).removeValue();

        dbr = fdb.getReference("usuarios/" + ((MiApplication) getApplicationContext()).getKey() + "/reservasCoche/");
        dbr.child(tusCitas.getKeyE()).removeValue();

        listaCitas.remove(tusCitas);
        adapter.setDatos(listaCitas);
        adapterDetallesCita = new MostrarDatosTusCitasAdapter(boton.getBoton());

        if (listaCitas.isEmpty()) {
            tvNoHayCitasVTC.setVisibility(View.VISIBLE);
            dbr = fdb.getReference("usuarios/" + ((MiApplication) getApplicationContext()).getKey());
            dbr.child("chat").removeValue();
        }

        dbr = fdb.getReference("coche/reservas/" + mesN);
        btnCancelarCita.setVisibility(View.INVISIBLE);
        rv.setAdapter(adapter);
        pasarAcitivtyDatos();

    }
}