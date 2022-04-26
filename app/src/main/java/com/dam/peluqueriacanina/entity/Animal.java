package com.dam.peluqueriacanina.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ANIMALES", indices = {@Index(value={"ruta"},unique = true)})
public class Animal {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ruta")
    public String ruta;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "raza")
    public String raza;

    public Animal(String ruta, String nombre, String raza) {
        this.ruta = ruta;
        this.nombre = nombre;
        this.raza = raza;
    }

    public Animal () {}

    public int getId() {
        return id;
    }

    public String getRuta() {
        return ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }
}
