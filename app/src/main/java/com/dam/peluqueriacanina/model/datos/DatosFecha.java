package com.dam.peluqueriacanina.model.datos;

import com.dam.peluqueriacanina.model.CitasReserva;

import java.util.ArrayList;

public class DatosFecha {

    private final ArrayList<CitasReserva> listaCitas;

    public DatosFecha() {
        listaCitas = new ArrayList<>();

        listaCitas.add(new CitasReserva("", "10:00"));
        listaCitas.add(new CitasReserva("", "11:00"));
        listaCitas.add(new CitasReserva("", "12:00"));
        listaCitas.add(new CitasReserva("", "13:00"));
        listaCitas.add(new CitasReserva("", "14:00"));
        listaCitas.add(new CitasReserva("", "15:00"));
        listaCitas.add(new CitasReserva("", "16:00"));
        listaCitas.add(new CitasReserva("", "23:30"));

    }

    public ArrayList<CitasReserva> getListaCitas() {
        return listaCitas;
    }
}
