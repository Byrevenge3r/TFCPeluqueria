package com.dam.peluqueriacanina.model;

import com.dam.peluqueriacanina.R;

import java.util.ArrayList;

public class DatosMisAnimales {

    private final ArrayList<MisAnimales> listaAnimales;

    public DatosMisAnimales() {
        listaAnimales = new ArrayList<>();

        listaAnimales.add(new MisAnimales(R.drawable.perro));
        listaAnimales.add(new MisAnimales(R.drawable.tiempo));
        listaAnimales.add(new MisAnimales(R.drawable.mona_lisa_foto_perfil));
        listaAnimales.add(new MisAnimales(R.drawable.perro_icono_vet_rv));
    }

    public ArrayList<MisAnimales> getListaAnimales() {
        return listaAnimales;
    }
}
