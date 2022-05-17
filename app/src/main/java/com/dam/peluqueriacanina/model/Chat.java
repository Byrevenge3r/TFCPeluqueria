package com.dam.peluqueriacanina.model;

public class Chat {

    private String mensaje;
    private String codigoPer;

    public Chat(String mensaje, String codigoPer) {
        this.mensaje = mensaje;
        this.codigoPer = codigoPer;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigoPer() {
        return codigoPer;
    }

    public void setCodigoPer(String codigoPer) {
        this.codigoPer = codigoPer;
    }
}
