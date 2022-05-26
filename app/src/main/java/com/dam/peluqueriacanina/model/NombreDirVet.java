package com.dam.peluqueriacanina.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NombreDirVet {
    private String clinica;
    private String lat;
    private String lon;

    public NombreDirVet(String clinica, String lat, String lon) {
        this.clinica = clinica;
        this.lat = lat;
        this.lon = lon;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
