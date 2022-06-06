package com.dam.peluqueriacanina.model;

public class PreguntaRespuesta {

    private String titulo;
    private String respuesta;

    public PreguntaRespuesta(String titulo, String respuesta) {
        this.titulo = titulo;
        this.respuesta = respuesta;
    }

    public PreguntaRespuesta() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
