package com.dam.peluqueriacanina.model.datos;

import com.dam.peluqueriacanina.model.BotonTusCitas;

import java.util.ArrayList;

public class BotonTusCitasLista {

    private ArrayList<BotonTusCitas> boton;

    public BotonTusCitasLista() {
        boton = new ArrayList<>();

        boton.add(new BotonTusCitas("Ver detalles de la cita"));
    }

    public ArrayList<BotonTusCitas> getBoton() {
        return boton;
    }
}
