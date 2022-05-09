package com.dam.peluqueriacanina.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "TUSCITAS", indices = {@Index(value={"ruta"},unique = true)})
public class TusCitas {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="ruta")
    public String ruta;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name="fecha")
    public String fecha;

    @ColumnInfo(name="hora")
    public String hora;

    public TusCitas() {
    }

    public TusCitas(String ruta, String nombre, String fecha, String hora) {
        this.ruta = ruta;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
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
}
