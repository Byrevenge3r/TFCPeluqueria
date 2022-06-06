package com.dam.peluqueriacanina.model;

import java.util.ArrayList;

public class BotonBorrarProducto {
    private ArrayList<BotonTusCitas> boton;

    public BotonBorrarProducto() {
        boton = new ArrayList<>();

        boton.add(new BotonTusCitas("Borrar"));
    }

    public ArrayList<BotonTusCitas> getBoton() {
        return boton;
    }

    public void setBoton(ArrayList<BotonTusCitas> boton) {
        this.boton = boton;
    }
}
