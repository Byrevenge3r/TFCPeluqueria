package com.dam.peluqueriacanina.model;

public class User {
    private String nom;
    private String correo;
    private String contra;
    public User(){

    }

    public User(String nom, String contra, String correo) {
        this.nom = nom;
        this.correo = correo;
        this.contra = contra;
    }


    public String getCorreo(){
        return correo;
    }

    public String getNom() {
        return nom;
    }
    public String getContra(){ return contra ; }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
