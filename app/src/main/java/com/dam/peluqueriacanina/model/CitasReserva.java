package com.dam.peluqueriacanina.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CitasReserva implements Parcelable {

    private String fecha;
    private String hora;

    public CitasReserva(String fecha, String hora) {
        this.fecha = fecha;
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "CitasReserva{" +
                "fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }

    public CitasReserva() {
    }

    public int getDia() {
        int dia = 0;

        dia = Integer.parseInt(fecha.substring(0,1));

        return dia;
    }

    protected CitasReserva(Parcel in) {
        fecha = in.readString();
        hora = in.readString();
    }

    public static final Creator<CitasReserva> CREATOR = new Creator<CitasReserva>() {
        @Override
        public CitasReserva createFromParcel(Parcel in) {
            return new CitasReserva(in);
        }

        @Override
        public CitasReserva[] newArray(int size) {
            return new CitasReserva[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fecha);
        parcel.writeString(hora);
    }
}
