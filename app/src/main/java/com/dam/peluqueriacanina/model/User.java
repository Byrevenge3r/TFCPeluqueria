package com.dam.peluqueriacanina.model;

public class User {
    private String nom;
    private String correo;
    private String contra;
    private int telefono;

    public User() {

    }

    public User(String nom, String contra, String correo, int telefono) {
        this.nom = nom;
        this.correo = correo;
        this.contra = contra;
        this.telefono = telefono;
    }


    public String getCorreo() {
        return correo;
    }

    public String getNom() {
        return nom;
    }

    public String getContra() {
        return contra;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
}
