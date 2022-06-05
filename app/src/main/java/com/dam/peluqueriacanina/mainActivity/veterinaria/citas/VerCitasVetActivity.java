package com.dam.peluqueriacanina.mainActivity.veterinaria.citas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.VetCitas;
import com.dam.peluqueriacanina.model.datos.BotonTusCitasLista;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.dam.peluqueriacanina.utils.MostrarDatosTusCitasAdapter;
import com.dam.peluqueriacanina.utils.VetCitasAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class VerCitasVetActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    RecyclerView  rvVerCita;

    TextView tvNoHayCitasVet;
    Button btnCancelarCitaVet;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    LinearLayoutManager llm;
    VetCitasAdapter adapter;
    MostrarDatosTusCitasAdapter adapterDetallesCita;
    BotonTusCitasLista boton;
    ArrayList<VetCitas> listaCitasVet;
    VetCitas cita;
    View vista;
    SimpleDateFormat formatter;

    //Fechas
    Date fecha;
    Date fechaHoy;
    LocalDateTime now;
    //Fin Fechas

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_ver_citas_vet);
        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();

        listaCitasVet = new ArrayList<>();

        dbr.child("usuarios/"+((MiApplication) getApplicationContext()).getKey()+"/reservasVet").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()) {
                    for (DataSnapshot sp:task.getResult().getChildren()) {
                        listaCitasVet.add(sp.getValue(VetCitas.class));
                    }
                    adapter = new VetCitasAdapter(listaCitasVet);
                    rv.setAdapter(adapter);
                    listenerAdapter();
                } else {
                    tvNoHayCitasVet.setVisibility(View.VISIBLE);
                }
            }
        });

        //Fechas
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        fecha = new Date();

        //RecyclerView inferior
        boton = new BotonTusCitasLista();
        adapterDetallesCita = new MostrarDatosTusCitasAdapter(boton.getBoton());
        vista = new View(this);
        //Se sigue con el resto


        rv = findViewById(R.id.rvVerTusCitasVet);
        tvNoHayCitasVet = findViewById(R.id.tvNoHayCitasVTCVet);
        btnCancelarCitaVet = findViewById(R.id.btnCancelarCitaVet);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        btnCancelarCitaVet.setOnClickListener(this);
        rv.setLayoutManager(llm);
        //Puede ser null por que este vacio arriba (probar)

        listenerAdapter();


    }

    private void listenerAdapter() {
        if (!listaCitasVet.isEmpty()) {
            adapter.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.llCitasVet:

                            rvVerCita = v.findViewById(R.id.rlContainerVet).findViewById(R.id.rvVerTusCitasSegundaPantallaVet);
                            rvVerCita.setLayoutManager(new LinearLayoutManager(v.findViewById(R.id.llCitasVet).getContext()));

                            rvVerCita.setAdapter(adapterDetallesCita);

                            adapterDetallesCita.setListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    cita = listaCitasVet.get(rv.getChildAdapterPosition(vista));
                                    try {
                                        fecha = formatter.parse(cita.getCitaFecha());
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            now = LocalDateTime.now();
                                            fechaHoy = formatter.parse(now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear());
                                        }
                                        // if (fecha.equals(fechaHoy)) {
                                        i = new Intent(VerCitasVetActivity.this, VerDatosCitasVetActivity.class);
                                        i.putExtra("hora", cita.getCitaHora());

                                        i.putExtra("nom",cita.getNom());
                                        startActivity(i);
                                        // } else {
                                        //     Toast.makeText(VerTusCitasActivity.this, R.string.fecha_distinta_hoy, Toast.LENGTH_SHORT).show();
                                        //}
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });

                            btnCancelarCitaVet.setVisibility(View.VISIBLE);

                            vista = v;

                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        String mesN = "";
        String[] mesCita = cita.getCitaFecha().split("/");
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
        cita = listaCitasVet.get(rv.getChildAdapterPosition(vista));

        dbr = fdb.getReference("veterinaria/veterinarioRes/"+cita.getNom()+"/"+mesN+"/");
        dbr.child(cita.getKeyEV()).removeValue();

        dbr = fdb.getReference("usuarios/"+((MiApplication)getApplicationContext()).getKey()+"/reservasVet/");
        dbr.child(cita.getKeyE()).removeValue();

        listaCitasVet.remove(cita);
        adapter.setDatos(listaCitasVet);
    }
}