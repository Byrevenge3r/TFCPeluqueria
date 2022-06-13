package com.dam.peluqueriacanina.mainActivity.peluqueria.fragmentosPel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.model.CitasReserva;
import com.dam.peluqueriacanina.model.datos.DatosFecha;
import com.dam.peluqueriacanina.utils.CitasAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class CitasPel extends DialogFragment {

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
    CitasAnimalFragmentPel citasAnimal;
    ArrayList<CitasReserva> listaCitas;
    ArrayList<CitasReserva> listaCitasMes;
    TextView tvNoHayCitas;
    Bundle bundle;
    SimpleDateFormat formatter;
    Date diaSeleccionado;
    Date diaActual;
    TusCitas tusCitas;
    ArrayList<String> listaMeses;
    String horaActual;
    LocalDateTime now;
    ArrayList<CitasReserva> listaCitasHoy;
    Date horaActualD;
    Date horaBbdd;
    SimpleDateFormat formatterH;
    String key;
    String tel;
    boolean continuar = true;
    int i = 0;

    public CitasPel() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                key = bundle.getString("keyB");
                tel = bundle.getString("tel");
            }
        });
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
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_citas, null);
        builder.setView(v);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();

        calendarioCitas = v.findViewById(R.id.calReserva);
        calendarioCitas.setDate(System.currentTimeMillis(), false, true);

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
        listaCitasHoy = new ArrayList<>();

        citasAnimal = new CitasAnimalFragmentPel();
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatterH = new SimpleDateFormat("HH:mm");
        diaSeleccionado = new Date();
        diaActual = new Date();
        horaActualD = new Date();
        horaBbdd = new Date();

        bundle = new Bundle();

        dbr = fdb.getReference();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
            horaActual = time.getHour() + ":" + time.getMinute();
            try {
                horaActualD = formatterH.parse(horaActual);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        try {
            diaActual = formatter.parse(formatter.format(diaActual));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarioCitas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int anio, int mesD, int dia) {
                datos = new DatosFecha();

                switch (mesD + 1) {
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
                    diaSeleccionado = formatter.parse(dia + "/" + (mesD + 1) + "/" + anio);

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

                    dbr.child("coche/reservas/" + mes).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.getResult().exists()) {
                                for (DataSnapshot ds : task.getResult().getChildren()) {
                                    cr = ds.getValue(CitasReserva.class);
                                    listaCitasMes.add(cr);
                                }

                                listaCitas = filtroLista(listaCitasMes, anio, (mesD + 1), dia);

                                filtrarDia();

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
                    });
                }
            }
        });
        return builder.create();
    }

    private void filtrarDia() {
        if (diaActual.equals(diaSeleccionado)) {
            while (continuar) {
                try {
                    horaBbdd = formatterH.parse(listaCitas.get(i).getHora());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (horaActualD.after(horaBbdd)) {
                    listaCitas.remove(i);
                    i = 0;
                } else {
                    i++;
                }
                if (listaCitas.size() == i) {
                    continuar = false;
                    i = 0;
                }
            }
            continuar = true;
        }
    }

    private void pasarCitaFragment(View v, int dia, int mesD, int anio) {
        citaFecha = dia + "/" + (mesD + 1) + "/" + anio;
        citaHora = listaCitas.get(rv.getChildAdapterPosition(v)).getHora();

        bundle.putString("citaFecha", citaFecha);
        bundle.putString("citaHora", citaHora);
        bundle.putString("mesN", mes);
        bundle.putString("KeyB", key);
        bundle.putString("tel", tel);
        getParentFragmentManager().setFragmentResult("Key", bundle);

        citasAnimal.show(getParentFragmentManager(), "CitasAnimal");
        dismiss();
    }


    private ArrayList<CitasReserva> filtroLista(ArrayList<CitasReserva> listaCitasMes, int anio, int mesD, int dia) {
        listaCitas = datos.getListaCitas();
        Date diaSeleccionado = new Date();
        Date diaHoy = new Date();

        int posicion = 0;
        boolean existe = false;

        for (int i = 0; i < listaCitasMes.size(); i++) {
            try {
                diaHoy = formatter.parse(dia + "/" + mesD + "/" + anio);
                diaSeleccionado = formatter.parse(listaCitasMes.get(i).getFecha());
                if (diaSeleccionado.equals(diaHoy)) {
                    for (int x = 0; x < listaCitas.size(); x++) {
                        if (listaCitasMes.get(i).getHora().equals(listaCitas.get(x).getHora())) {
                            existe = true;
                            posicion = x;
                            x = listaCitas.size();
                        }
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (existe) {
                listaCitas.remove(posicion);
            }
            existe = false;
        }

        return listaCitas;
    }

}