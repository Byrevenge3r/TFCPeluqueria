package com.dam.peluqueriacanina.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "TUSCITAS")
public class TusCitas {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="urlI")
    public String urlI;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "keyE")
    public String keyE;

    @ColumnInfo(name = "keyEC")
    public String keyEC;

    @ColumnInfo(name = "nomAnimal")
    public String nomAnimal;

    @ColumnInfo(name="citaFecha")
    public String citaFecha;

    @ColumnInfo(name="citaHora")
    public String citaHora;

    public TusCitas() {
    }

    public TusCitas(String urlI, String key, String keyE, String keyEC, String nomAnimal, String citaFecha, String citaHora) {
        this.urlI = urlI;
        this.key = key;
        this.keyE = keyE;
        this.keyEC = keyEC;
        this.nomAnimal = nomAnimal;
        this.citaFecha = citaFecha;
        this.citaHora = citaHora;
    }

    public String getKeyEC() {
        return keyEC;
    }

    public void setKeyEC(String keyEC) {
        this.keyEC = keyEC;
    }

    public String getKeyE() {
        return keyE;
    }

    public void setKeyE(String keyE) {
        this.keyE = keyE;
    }

    public String getNomAnimal() {
        return nomAnimal;
    }

    public void setNomAnimal(String nomAnimal) {
        this.nomAnimal = nomAnimal;
    }

    public String getCitaFecha() {
        return citaFecha;
    }

    public void setCitaFecha(String citaFecha) {
        this.citaFecha = citaFecha;
    }

    public String getUrlI() {
        return urlI;
    }

    public void setUrlI(String urlI) {
        this.urlI = urlI;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCitaHora() {
        return citaHora;
    }

    public void setCitaHora(String citaHora) {
        this.citaHora = citaHora;
    }
}
