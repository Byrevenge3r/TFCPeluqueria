package com.dam.peluqueriacanina.mainActivity.veterinaria.fragmentosVet;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.notificacion.Recordatorio;
import com.dam.peluqueriacanina.utils.CitasAnimalesFotoAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CitasAnimalFragmentVet extends DialogFragment {

    AnimalesDao daoAnimal;
    AnimalesDB dbAnimal;

    TusCitasDao daoTusCitas;
    TusCitasDB dbTusCitas;

    Animal animal;
    CitasAnimalesFotoAdapter adapter;
    RecyclerView rv;
    LinearLayoutManager llm;
    public String citaFecha = "";
    public String citaHora = "";
    String mesN = "";
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    LocalDateTime now;
    String fechaActual = "";
    String keyB;
    public String nom;
    Calendar calendar = Calendar.getInstance();
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    ArrayList<AlarmManager> listaAlarmas;


    public String getCitaHora() {
        return citaHora;
    }

    public String getNom() {
        return nom;
    }

    public CitasAnimalFragmentVet() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_citas_animal, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_citas_animal, null);
        builder.setView(v);
        listaAlarmas = new ArrayList<>();

        getParentFragmentManager().setFragmentResultListener("Key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                nom = bundle.getString("nom");
                citaFecha = bundle.getString("citaFecha");
                citaHora = bundle.getString("citaHora");
                mesN = bundle.getString("mesN");
                keyB = bundle.getString("KeyB");

                adapter = new CitasAnimalesFotoAdapter((ArrayList<Animal>) daoAnimal.sacarAnimalKey(keyB));

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        animal = (daoAnimal.sacarAnimalKey(keyB)).get(rv.getChildAdapterPosition(v));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            now = LocalDateTime.now();
                            fechaActual = now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear();
                            //Subida a el apartado general de las veterinarias
                            dbr = fdb.getReference("veterinaria/veterinarioRes/" + nom + "/" + mesN);
                            String key = dbr.push().getKey();
                            HashMap<String, Object> listaCitaVet = new HashMap<>();

                            listaCitaVet.put("fecha", citaFecha);
                            listaCitaVet.put("hora", citaHora);
                            dbr.child(key).setValue(listaCitaVet);

                            listaCitaVet.clear();

                            //Subida al apartado independiente de las veterinarias
                            dbr = fdb.getReference("usuarios/" + keyB + "/reservasVet");
                            listaCitaVet.put("nom", nom);
                            listaCitaVet.put("citaFecha", citaFecha);
                            listaCitaVet.put("citaHora", citaHora);
                            listaCitaVet.put("keyEV", key);
                            key = dbr.push().getKey();
                            listaCitaVet.put("keyE", key);
                            dbr.child(key).setValue(listaCitaVet);


                            String[] diaMesAnio = citaFecha.split("/");
                            String[] horaSolo = citaHora.split(":");

                            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaMesAnio[0]));
                            calendar.set(Calendar.MONTH, Integer.parseInt(diaMesAnio[1]) - 1);
                            calendar.set(Calendar.YEAR, Integer.parseInt(diaMesAnio[2]));

                            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaSolo[0]));
                            calendar.set(Calendar.MINUTE, Integer.parseInt(horaSolo[1]));

                            Intent i = new Intent(getContext(), Recordatorio.class);
                            //Solo una larma
                            int valor = (int) System.currentTimeMillis();
                            pendingIntent = PendingIntent.getBroadcast(getContext(), valor, i, 0);
                            alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                            Long alertTime = calendar.getTimeInMillis();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CharSequence name = "Hey estas ahi?, tienes una cita";
                                String descripcion = "En una hora tienes una cita en la veterinaria: " + nom + "\n A las: " + citaHora;
                                int importancia = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel channel = new NotificationChannel("hola", name, importancia);
                                channel.setDescription(descripcion);

                                NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
                                notificationManager.createNotificationChannel(channel);

                            }

                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alertTime, pendingIntent);
                            dismiss();
                        }

                    }
                });
                rv.setAdapter(adapter);
            }
        });

        dbAnimal = AnimalesDB.getDatabase(getContext());
        daoAnimal = dbAnimal.animalDao();

        dbTusCitas = TusCitasDB.getDatabase(getContext());
        daoTusCitas = dbTusCitas.citaDao();

        rv = v.findViewById(R.id.rvCitasDatosFotoAnimal);
        llm = new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);


        dbr = fdb.getReference();

        return builder.create();
    }

}