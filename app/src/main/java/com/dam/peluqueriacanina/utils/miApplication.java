package com.dam.peluqueriacanina.utils;

import android.app.Application;

public class miApplication extends Application {

    private String nombre;
    private String apellidos;
    private String usuario;
    private String correo;
    private String contrasenia;
    private String telefono;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() { return apellidos; }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasenia() { return contrasenia; }

    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public miApplication(String nombre, String apellidos, String usuario, String correo, String contrasenia, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
    }

    public miApplication() {
    }
}
