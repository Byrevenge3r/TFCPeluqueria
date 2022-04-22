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
import com.dam.peluqueriacanina.utils.CitasAdapter;

public class Citas extends DialogFragment {

    CalendarView calendarioCitas;
    Button btnCita;
    RecyclerView rv;
    LinearLayoutManager llm;
    CitasAdapter adapter;


    public Citas() {
        // Required empty public constructor
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

        calendarioCitas = v.findViewById(R.id.calReserva);
        calendarioCitas.setDate(System.currentTimeMillis(),false,true);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);


        calendarioCitas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int anio, int mes, int dia) {

            }
        });


        return builder.create();
    }
}