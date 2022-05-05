package com.dam.peluqueriacanina.model;

import java.util.ArrayList;

public class DatosFecha {

    private final ArrayList<CitasReserva> listaCitas;

    public DatosFecha(){
        listaCitas = new ArrayList<>();

        listaCitas.add(new CitasReserva(null,"10:00"));
        listaCitas.add(new CitasReserva(null,"11:00"));
        listaCitas.add(new CitasReserva(null,"12:00"));
        listaCitas.add(new CitasReserva(null,"13:00"));
        listaCitas.add(new CitasReserva(null,"14:00"));
        listaCitas.add(new CitasReserva(null,"15:00"));
        listaCitas.add(new CitasReserva(null,"16:00"));
        listaCitas.add(new CitasReserva(null,"17:00"));
    }

    public ArrayList<CitasReserva> getListaCitas() {
        return listaCitas;
    }
}
