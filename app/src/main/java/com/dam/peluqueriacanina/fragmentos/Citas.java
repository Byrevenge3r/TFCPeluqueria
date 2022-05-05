package com.dam.peluqueriacanina.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.CitasReserva;
import com.dam.peluqueriacanina.model.DatosFecha;
import com.dam.peluqueriacanina.utils.CitasAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Citas extends DialogFragment {

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
    ArrayList<CitasReserva> listaCitas;

    ArrayList<CitasReserva> listaCitasMes;

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
        listaCitas = new ArrayList<>();
        listaCitas = datos.getListaCitas();

        listaCitasMes = new ArrayList<>();
        //TODO: Cargar las horas libres del dia actual

        calendarioCitas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int anio, int mesD, int dia) {
                datos = new DatosFecha();
                //TODO: Cargar los datos de firebase (esto esta incompleto)
                //Hacer la consulta aqui (Hacer un if para diferenciar)

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

                dbr.child("coche/reservas/"+mes).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.exists()) {
                           for (DataSnapshot ds : snapshot.getChildren()) {
                               cr = ds.getValue(CitasReserva.class);
                               listaCitasMes.add(cr);
                           }
                           listaCitas = filtroLista(listaCitasMes,anio,(mesD+1),dia);
                           listaCitasMes.clear();

                           adapter = new CitasAdapter(listaCitas);
                           rv.setAdapter(adapter);

                           adapter.setListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {

                                   citaFecha = dia+"/"+(mesD+1)+"/"+anio;
                                   citaHora = listaCitas.get(rv.getChildAdapterPosition(v)).getHora();

                                   HashMap<String,Object> listaCitasPelu = new HashMap<>();

                                   listaCitasPelu.put("fecha", citaFecha);
                                   listaCitasPelu.put("hora",citaHora);

                                   dbr.child("coche/reservas/"+mes).push().updateChildren(listaCitasPelu);

                                   dismiss();
                               }
                           });
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return builder.create();
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