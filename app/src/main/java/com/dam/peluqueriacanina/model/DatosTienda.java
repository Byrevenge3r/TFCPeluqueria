package com.dam.peluqueriacanina.model;

public class DatosTienda {
    private String nombre;
    private String cantidad;
    private String foto;
    private String tipo;

    public DatosTienda(String nombre, String cantidad, String tipo) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
