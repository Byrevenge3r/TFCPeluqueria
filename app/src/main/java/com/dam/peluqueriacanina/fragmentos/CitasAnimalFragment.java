package com.dam.peluqueriacanina.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.CitasDao;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.CitasDB;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.Cita;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.utils.CitasAnimalesFotoAdapter;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CitasAnimalFragment extends DialogFragment {

    AnimalesDao daoAnimal;
    AnimalesDB dbAnimal;
    CitasDao daoCitas;
    CitasDB dbCitas;
    TusCitasDao daoTusCitas;
    TusCitasDB dbTusCitas;
    Animal animal;
    CitasAnimalesFotoAdapter adapter;
    RecyclerView rv;
    LinearLayoutManager llm;
    ShapeableImageView imagenAnimal;
    String citaFecha = "";
    String citaHora = "";
    String mes = "";
    String mesN = "";
    String ruta = "";
    FirebaseDatabase fdb;
    DatabaseReference dbr;


    public CitasAnimalFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("Key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                citaFecha = bundle.getString("citaFecha");
                citaHora = bundle.getString("citaHora");
                mes = bundle.getString("mes");
                mesN = bundle.getString("mesN");
            }
        });

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
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_citas_animal,null);
        builder.setView(v);

        dbAnimal = AnimalesDB.getDatabase(getContext());
        daoAnimal = dbAnimal.animalDao();

        dbTusCitas = TusCitasDB.getDatabase(getContext());
        daoTusCitas = dbTusCitas.citaDao();

        dbCitas = CitasDB.getDatabase(getContext());
        daoCitas = dbCitas.citaDao();

        rv = v.findViewById(R.id.rvCitasDatosFotoAnimal);
        llm = new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        adapter = new CitasAnimalesFotoAdapter((ArrayList<Animal>) daoAnimal.sacarTodo());
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,Object> listaCitasPelu = new HashMap<>();

                listaCitasPelu.put("fecha",citaFecha);
                listaCitasPelu.put("hora",citaHora);

                dbr.child("coche/reservas/"+mesN).push().updateChildren(listaCitasPelu);

                animal = (daoAnimal.sacarTodo()).get(rv.getChildAdapterPosition(v));
                daoCitas.insert(new Cita(animal.getRuta(), citaFecha,citaHora));
                daoTusCitas.insert(new TusCitas(animal.getRuta(),animal.getNombre(),citaFecha,citaHora));

                dismiss();
            }
        });
        rv.setAdapter(adapter);

        return builder.create();
    }
}