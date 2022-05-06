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

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.CitasDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.CitasDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.utils.CitasAnimalesFotoAdapter;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class CitasAnimalFragment extends DialogFragment {

    AnimalesDao daoAnimal;
    AnimalesDB dbAnimal;
    CitasDao daoCitas;
    CitasDB dbCitas;
    CitasAnimalesFotoAdapter adapter;
    RecyclerView rv;
    LinearLayoutManager llm;
    ShapeableImageView imagenAnimal;


    public CitasAnimalFragment() {
        // Required empty public constructor
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_citas_animal,null);
        builder.setView(v);

        dbAnimal = AnimalesDB.getDatabase(getContext());
        daoAnimal = dbAnimal.animalDao();

        dbCitas = CitasDB.getDatabase(getContext());
        daoCitas = dbCitas.citaDao();

        rv = v.findViewById(R.id.rvCitasDatosFotoAnimal);
        llm = new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        adapter = new CitasAnimalesFotoAdapter((ArrayList<Animal>) daoAnimal.sacarTodo());
        rv.setAdapter(adapter);
        return builder.create();
    }
}