package com.dam.peluqueriacanina.fragmentos;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dam.peluqueriacanina.PeluqueriaActivity;
import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.comunicacion.Comunicacion;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.utils.CitasAnimalesFotoAdapter;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CitasAnimalFragment extends DialogFragment {

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
    String mes = "";
    String mesN = "";
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    String key = "";
    LocalDateTime now;
    String fechaActual = "";
    String numeroTelConduc = "";
    Bundle bundleCita;
    String keyB;
    Comunicacion listener;

    public CitasAnimalFragment() {
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
                citaFecha = bundle.getString("citaFecha");
                citaHora = bundle.getString("citaHora");
                mes = bundle.getString("mes");
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
                            dbr = fdb.getReference("coche/reservas/"+mesN);

                            SmsManager sms = SmsManager.getDefault(); //((MiApplication) getContext()).getTelefono()
                            //TODO:Poner que recoja el telefono del usuario cuando inicie sesion            JUSTO AQUI
                            sms.sendTextMessage("+34" + numeroTelConduc, null,  "671393685"+ "-" + citaFecha + "-" + citaHora + "-" + keyB, null, null);
                            listener.info(new TusCitas(animal.getRuta(),keyB,animal.getNombre(),citaFecha,citaHora));

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

        bundleCita = new Bundle();

        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECEIVE_SMS) +
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS))
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, 1001);
        }

        dbr.child("coche/tel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    numeroTelConduc = String.valueOf(snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dbr = fdb.getReference();


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Comunicacion) {
            listener = (Comunicacion) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnComunicationFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}