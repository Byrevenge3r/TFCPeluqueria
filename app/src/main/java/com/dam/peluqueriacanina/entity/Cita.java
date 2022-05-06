package com.dam.peluqueriacanina.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "CITAS", indices = {@Index(value={"ruta"},unique = true)})
public class Cita {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="ruta")
    public String ruta;

    @ColumnInfo(name="fecha")
    public String fecha;

    @ColumnInfo(name="hora")
    public String hora;

    public Cita(String ruta, String fecha, String hora) {
        this.ruta = ruta;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cita() {}

    public String getRuta() {
        return ruta;
    }
}
