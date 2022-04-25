package com.dam.peluqueriacanina.model;

import java.util.ArrayList;

public class DatosFecha {

    private final ArrayList<CitasReserva> listaCitas;

    public DatosFecha(){
        listaCitas = new ArrayList<>();

        listaCitas.add(new CitasReserva("02/09/22","15:40"));
        listaCitas.add(new CitasReserva("18/10/22","17:40"));
        listaCitas.add(new CitasReserva("20/03/22","20:40"));
        listaCitas.add(new CitasReserva("14/05/22","01:40"));
        listaCitas.add(new CitasReserva("08/06/22","17:40"));
        listaCitas.add(new CitasReserva("09/03/22","21:40"));
    }

    public ArrayList<CitasReserva> getListaCitas() {
        return listaCitas;
    }
}
