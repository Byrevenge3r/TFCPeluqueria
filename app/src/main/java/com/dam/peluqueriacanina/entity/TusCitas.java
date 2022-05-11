package com.dam.peluqueriacanina.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "TUSCITAS", indices = {@Index(value={"ruta"},unique = true)})
public class TusCitas implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="ruta")
    public String ruta;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name="fecha")
    public String fecha;

    @ColumnInfo(name="hora")
    public String hora;

    public TusCitas() {
    }

    public TusCitas(String ruta, String key, String nombre, String fecha, String hora) {
        this.ruta = ruta;
        this.key = key;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
    }

    protected TusCitas(Parcel in) {
        id = in.readInt();
        ruta = in.readString();
        key = in.readString();
        nombre = in.readString();
        fecha = in.readString();
        hora = in.readString();
    }

    public static final Creator<TusCitas> CREATOR = new Creator<TusCitas>() {
        @Override
        public TusCitas createFromParcel(Parcel in) {
            return new TusCitas(in);
        }

        @Override
        public TusCitas[] newArray(int size) {
            return new TusCitas[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(ruta);
        parcel.writeString(key);
        parcel.writeString(nombre);
        parcel.writeString(fecha);
        parcel.writeString(hora);
    }

    @Override
    public String toString() {
        return "TusCitas{" +
                "ruta='" + ruta + '\'' +
                ", key='" + key + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
