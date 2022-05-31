package com.dam.peluqueriacanina.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DatosTienda implements Parcelable {
    private String nombre;
    private String cantidad;
    private String foto;
    private String detalle;
    private String tipo;
    private String precio;

    public DatosTienda(String nombre, String cantidad, String foto, String detalle, String tipo, String precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.foto = foto;
        this.detalle = detalle;
        this.tipo = tipo;
        this.precio = precio;
    }

    protected DatosTienda(Parcel in) {
        nombre = in.readString();
        cantidad = in.readString();
        foto = in.readString();
        detalle = in.readString();
        tipo = in.readString();
        precio = in.readString();
    }

    public static final Creator<DatosTienda> CREATOR = new Creator<DatosTienda>() {
        @Override
        public DatosTienda createFromParcel(Parcel in) {
            return new DatosTienda(in);
        }

        @Override
        public DatosTienda[] newArray(int size) {
            return new DatosTienda[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(cantidad);
        dest.writeString(foto);
        dest.writeString(detalle);
        dest.writeString(tipo);
        dest.writeString(precio);
    }
}
