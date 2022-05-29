package com.dam.peluqueriacanina.model;

public class VetCitas {

    private String nom;
    private String citaFecha;
    private String citaHora;

    public VetCitas() {
    }

    public VetCitas(String nom, String citaFecha, String citaHora) {
        this.nom = nom;
        this.citaFecha = citaFecha;
        this.citaHora = citaHora;
    }

    public String getNom() {
        return nom;
    }

    public String getCitaFecha() {
        return citaFecha;
    }

    public String getCitaHora() {
        return citaHora;
    }
}
