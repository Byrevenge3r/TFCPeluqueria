package com.dam.peluqueriacanina.model;

public class VetCitas {

    private String nom;
    private String citaFecha;
    private String citaHora;
    private String keyE;
    private String keyEV;


    public VetCitas() {
    }

    public VetCitas(String nom, String citaFecha, String citaHora, String keyE, String keyEV) {
        this.nom = nom;
        this.citaFecha = citaFecha;
        this.citaHora = citaHora;
        this.keyE = keyE;
        this.keyEV = keyEV;
    }

    //
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCitaFecha(String citaFecha) {
        this.citaFecha = citaFecha;
    }

    public void setCitaHora(String citaHora) {
        this.citaHora = citaHora;
    }

    public String getKeyE() {
        return keyE;
    }

    public void setKeyE(String keyE) {
        this.keyE = keyE;
    }

    public String getKeyEV() {
        return keyEV;
    }

    public void setKeyEV(String keyEV) {
        this.keyEV = keyEV;
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
