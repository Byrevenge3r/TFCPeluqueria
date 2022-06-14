package com.dam.peluqueriacanina.model.datos;

import com.dam.peluqueriacanina.model.BotonTusCitas;

import java.util.ArrayList;

public class BotonTusCitasVetLista {
    private ArrayList<BotonTusCitas> boton;

    public BotonTusCitasVetLista() {
        boton = new ArrayList<>();

        boton.add(new BotonTusCitas("Ver detalles del recordatorio"));
    }

    public ArrayList<BotonTusCitas> getBoton() {
        return boton;
    }

    public void setBoton(ArrayList<BotonTusCitas> boton) {
        this.boton = boton;
    }
}
