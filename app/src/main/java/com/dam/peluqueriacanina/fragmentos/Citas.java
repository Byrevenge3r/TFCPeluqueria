package com.dam.peluqueriacanina.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.CitasReserva;
import com.dam.peluqueriacanina.model.DatosFecha;
import com.dam.peluqueriacanina.utils.CitasAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Citas extends DialogFragment {

    CalendarView calendarioCitas;
    RecyclerView rv;
    LinearLayoutManager llm;
    CitasAdapter adapter;
    DatosFecha datos;
    String citaHora;
    String citaFecha;
    CitasReserva cr;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    ArrayList<CitasReserva> listaCitas;


    public Citas() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_citas, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_citas,null);
        builder.setView(v);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();

        calendarioCitas = v.findViewById(R.id.calReserva);
        calendarioCitas.setDate(System.currentTimeMillis(),false,true);

        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv = v.findViewById(R.id.rvCitasPelu);
        rv.setLayoutManager(llm);

        datos = new DatosFecha();

        //TODO: Cargar las horas libres del dia actual
        adapter = new CitasAdapter(datos.getListaCitas());
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                citaHora = listaCitas.get(rv.getChildAdapterPosition(v)).getHora();
                citaFecha = String.valueOf(calendarioCitas.getDate());

                cr = new CitasReserva(citaFecha,citaHora);
                Map<String,Object> citaPel = new HashMap<>();

                citaPel.put("cita", citaFecha + "-" + citaHora);

                dbr.child("coche/reservas").push().updateChildren(citaPel);

            }
        });

        calendarioCitas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int anio, int mes, int dia) {
                datos = new DatosFecha();
                //TODO: Cargar los datos de firebase (esto esta incompleto)
                adapter = new CitasAdapter(datos.getListaCitas());
                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // citaHora = listaCitas.get(rv.getChildAdapterPosition(v)).getHora();
                        citaFecha = String.valueOf(calendarioCitas.getDate());

                        cr = new CitasReserva(citaFecha,citaHora);
                        Map<String,Object> citaPel = new HashMap<>();

                        citaPel.put("cita", citaFecha + "-" + citaHora);

                        dbr.child("coche/reservas").push().updateChildren(citaPel);

                    }
                });
                rv.setAdapter(adapter);
            }
        });


        return builder.create();
    }
}