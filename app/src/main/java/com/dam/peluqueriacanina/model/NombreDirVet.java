package com.dam.peluqueriacanina.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NombreDirVet {
    private String clínica;
    private String dirección;
    private String municipio;
    private String cp;

    public NombreDirVet(String clínica, String dirección, String municipio, String cp) {
        this.clínica = clínica;
        this.dirección = dirección;
        this.municipio = municipio;
        this.cp = cp;
    }

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
