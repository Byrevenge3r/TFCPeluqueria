package com.dam.peluqueriacanina.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NombreDirVet {
    @SerializedName("clinica")
    @Expose
    private String clínica;

    @SerializedName("dirección")
    @Expose
    private String dirección;

    @SerializedName("municipio")
    @Expose
    private String municipio;

    @SerializedName("cp")
    @Expose
    private String cp;

    public String getClínica() {
        return clínica;
    }

    public String getDirección() {
        return dirección;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getCp() {
        return cp;
    }


}
