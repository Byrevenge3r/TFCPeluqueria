package com.dam.peluqueriacanina.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimalReservaPelu implements Parcelable {

    private String nombreA;
    private String fecha;
    private String hora;
    private String ruta;

    public AnimalReservaPelu(String nombreA, String fecha, String hora, String ruta) {
        this.nombreA = nombreA;
        this.fecha = fecha;
        this.hora = hora;
        this.ruta = ruta;
    }

    public String getNombreA() {
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    protected AnimalReservaPelu(Parcel in) {
        nombreA = in.readString();
        fecha = in.readString();
        hora = in.readString();
        ruta = in.readString();
    }

    public static final Creator<AnimalReservaPelu> CREATOR = new Creator<AnimalReservaPelu>() {
        @Override
        public AnimalReservaPelu createFromParcel(Parcel in) {
            return new AnimalReservaPelu(in);
        }

        @Override
        public AnimalReservaPelu[] newArray(int size) {
            return new AnimalReservaPelu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombreA);
        parcel.writeString(fecha);
        parcel.writeString(hora);
        parcel.writeString(ruta);
    }
}
