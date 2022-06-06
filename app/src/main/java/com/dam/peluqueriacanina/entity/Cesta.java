package com.dam.peluqueriacanina.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "CESTA", indices = {@Index(value = {"id"}, unique = true)})
public class Cesta implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "cantidad")
    public int cantidad;

    @ColumnInfo(name = "precio")
    public int precio;

    public Cesta(String nombre, int cantidad, int precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    protected Cesta(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        cantidad = in.readInt();
        precio = in.readInt();
    }

    public static final Creator<Cesta> CREATOR = new Creator<Cesta>() {
        @Override
        public Cesta createFromParcel(Parcel in) {
            return new Cesta(in);
        }

        @Override
        public Cesta[] newArray(int size) {
            return new Cesta[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeInt(cantidad);
        dest.writeInt(precio);
    }
}
