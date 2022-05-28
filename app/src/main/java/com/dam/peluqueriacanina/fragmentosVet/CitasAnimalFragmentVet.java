package com.dam.peluqueriacanina.fragmentosVet;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.comunicacion.Comunicacion;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.notificacion.Recordatorio;
import com.dam.peluqueriacanina.utils.CitasAnimalesFotoAdapter;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class CitasAnimalFragmentVet extends DialogFragment {

    AnimalesDao daoAnimal;
    AnimalesDB dbAnimal;

    TusCitasDao daoTusCitas;
    TusCitasDB dbTusCitas;

    Animal animal;
    CitasAnimalesFotoAdapter adapter;
    RecyclerView rv;
    LinearLayoutManager llm;
    String citaFecha = "";
    String citaHora = "";
    String mesN = "";
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    LocalDateTime now;
    String fechaActual = "";
    String keyB;
    String nom;
    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();

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
                            dbr = fdb.getReference("veterinaria/veterinaroRes/"+nom+"/"+mesN);
                            String key = dbr.push().getKey();
                            HashMap<String,Object> listaCitaVet = new HashMap<>();

                            listaCitaVet.put("fecha",citaFecha);
                            listaCitaVet.put("hora",citaHora);

                            dbr.child(key).setValue(listaCitaVet);
                            String[] diaMesAnio = citaFecha.split("/");
                            String tag = generateKey();
                            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaMesAnio[0]));
                            calendar.set(Calendar.MONTH, Integer.parseInt(diaMesAnio[1]));
                            calendar.set(Calendar.YEAR, Integer.parseInt(diaMesAnio[2]));

                            Long alertTime = calendar.getTimeInMillis() - System.currentTimeMillis();
                            int random = (int) (Math.random() * 50 +1);

                            Data data = guardarData("Hey estas ahi?, tienes una cita" ,"Tienes una cita en la veterinaria: " + nom + "\n a las: " + citaHora,random);

                            Recordatorio.guardarNoti(alertTime,data,"tagNoti");

                        }
                        dismiss();
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


    private String generateKey () {
        return UUID.randomUUID().toString();
    }

    private Data guardarData (String titulo, String detalle, int idNoti) {
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("id_noti",idNoti).build();
    }

}