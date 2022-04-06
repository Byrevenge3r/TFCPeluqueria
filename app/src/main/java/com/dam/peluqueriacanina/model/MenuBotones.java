package com.dam.peluqueriacanina.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class MenuBotones {

private String nombreDepartamento;
private int imagen;
private int colorFondo;

    public MenuBotones(String nombreDepartamento, int imagen, int colorFondo) {
        this.nombreDepartamento = nombreDepartamento;
        this.imagen = imagen;
        this.colorFondo = colorFondo;
    }



    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(int colorFondo) {
        this.colorFondo = colorFondo;
    }


}
