package com.dam.peluqueriacanina.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ANIMALES")
public class Animal {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "keyU")
    public String keyU;

    @ColumnInfo(name = "urlI")
    public String urlI;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "raza")
    public String raza;

    public Animal(String key, String keyU, String urlI, String nombre, String raza) {
        this.key = key;
        this.keyU = keyU;
        this.urlI = urlI;
        this.nombre = nombre;
        this.raza = raza;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyU() {
        return keyU;
    }

    public void setKeyU(String keyU) {
        this.keyU = keyU;
    }

    public void setUrlI(String urlI) {
        this.urlI = urlI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getUrlI() {
        return urlI;
    }

    public String getKey() {
        return key;
    }

    public Animal () {}

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }
}
