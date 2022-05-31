package com.dam.peluqueriacanina.model;

public class Animal {

    private String key;
    private String nombre;
    private String raza;

    public Animal(String key, String nombre, String raza) {
        this.key = key;
        this.nombre = nombre;
        this.raza = raza;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
}
