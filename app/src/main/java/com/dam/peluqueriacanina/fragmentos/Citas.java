package com.dam.peluqueriacanina.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.comunicacion.Comunicacion;
import com.dam.peluqueriacanina.comunicacion.Comunicacion2;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.model.CitasReserva;
import com.dam.peluqueriacanina.model.DatosFecha;
import com.dam.peluqueriacanina.utils.CitasAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Citas extends DialogFragment{

    CalendarView calendarioCitas;
    RecyclerView rv;
    LinearLayoutManager llm;
    CitasAdapter adapter;
    DatosFecha datos;
    String citaHora;
    String citaFecha;
    String mes;
    CitasReserva cr;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    //Cotiene todas las horas
    CitasAnimalFragment citasAnimal;
    ArrayList<CitasReserva> listaCitas;
    ArrayList<CitasReserva> listaCitasMes;
    TextView tvNoHayCitas;
    Bundle bundle;
    SimpleDateFormat formatter;
    Date diaSeleccionado;
    Date diaActual;
    TusCitas tusCitas;
    ArrayList<String> listaMeses;

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

        tvNoHayCitas = v.findViewById(R.id.tvNoHayCitas);
        tvNoHayCitas.setVisibility(View.INVISIBLE);

        tusCitas = new TusCitas();

        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv = v.findViewById(R.id.rvCitasPelu);
        rv.setLayoutManager(llm);

        datos = new DatosFecha();
        listaCitas = new ArrayList<>();
        listaCitas = datos.getListaCitas();

        listaCitasMes = new ArrayList<>();
        listaMeses = new ArrayList<>();

        citasAnimal = new CitasAnimalFragment();
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        diaSeleccionado = new Date();
        diaActual = new Date();

        bundle = new Bundle();

        dbr = fdb.getReference();

        try {
            diaActual = formatter.parse(formatter.format(diaActual));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarioCitas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int anio, int mesD, int dia) {
                datos = new DatosFecha();

                switch (mesD+1) {
                    case 1:
                        mes = "enero";
                        break;
                    case 2:
                        mes = "febrero";
                        break;
                    case 3:
                        mes = "marzo";
                        break;
                    case 4:
                        mes = "abril";
                        break;
                    case 5:
                        mes = "mayo";
                        break;
                    case 6:
                        mes = "junio";
                        break;
                    case 7:
                        mes = "julio";
                        break;
                    case 8:
                        mes = "agosto";
                        break;
                    case 9:
                        mes = "septiembre";
                        break;
                    case 10:
                        mes = "octubre";
                        break;
                    case 11:
                        mes = "noviembre";
                        break;
                    case 12:
                        mes = "diciembre";
                        break;
                }

                try {
                    diaSeleccionado = formatter.parse(dia+"/"+(mesD+1)+"/"+anio);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (diaActual.after(diaSeleccionado)) {
                    tvNoHayCitas.setVisibility(View.VISIBLE);
                    listaCitas.clear();
                    adapter = new CitasAdapter(listaCitas);
                    rv.setAdapter(adapter);
                } else {
                    tvNoHayCitas.setVisibility(View.INVISIBLE);


                    dbr.child("coche/reservas/"+mes).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    cr = ds.getValue(CitasReserva.class);
                                    listaCitasMes.add(cr);
                                }

                                listaCitas = filtroLista(listaCitasMes,anio,(mesD+1),dia);
                                if (listaCitas.isEmpty()) {
                                    tvNoHayCitas.setVisibility(View.VISIBLE);
                                }
                                listaCitasMes.clear();

                                adapter = new CitasAdapter(listaCitas);
                                rv.setAdapter(adapter);

                                adapter.setListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        pasarCitaFragment(v, dia, mesD, anio);
                                    }
                                });
                            } else {
                                listaCitas = datos.getListaCitas();
                                adapter = new CitasAdapter(listaCitas);
                                rv.setAdapter(adapter);
                                adapter.setListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        pasarCitaFragment(v, dia, mesD, anio);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });
        return builder.create();
    }

    private void pasarCitaFragment(View v, int dia, int mesD, int anio) {
        citaFecha = dia+"/"+(mesD+1)+"/"+anio;
        citaHora = listaCitas.get(rv.getChildAdapterPosition(v)).getHora();

        bundle.putString("citaFecha", citaFecha);
        bundle.putString("citaHora", citaHora);
        bundle.putString("mesN",mes);
        bundle.putString("mes", String.valueOf(mesD+1));

        getParentFragmentManager().setFragmentResult("Key",bundle);

        citasAnimal.show(getParentFragmentManager(),"CitasAnimal");
        dismiss();
    }


    private ArrayList<CitasReserva> filtroLista(ArrayList<CitasReserva> listaCitasMes, int anio, int mesD, int dia){
       listaCitas = datos.getListaCitas();
        int posicion = 0;
        boolean existe = false;
         for (int i = 0; i < listaCitasMes.size();i++) {
            if (listaCitasMes.get(i).getFecha().equals(dia+"/"+mesD+"/"+anio)) {
                for (int x = 0; x < listaCitas.size(); x++) {
                    if (listaCitasMes.get(i).getHora().equals(listaCitas.get(x).getHora())) {
                        existe = true;
                        posicion = x;
                    }
                }
            }
            if (existe) {
                listaCitas.remove(posicion);
            }
            existe = false;
         }
        return listaCitas;
    }

}