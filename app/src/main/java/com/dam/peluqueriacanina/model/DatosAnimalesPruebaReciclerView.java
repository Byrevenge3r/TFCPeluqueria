package com.dam.peluqueriacanina.model;

import java.util.ArrayList;

public class DatosAnimalesPruebaReciclerView {

    private ArrayList<AnimalReservaPelu> listaAnimales;

    public DatosAnimalesPruebaReciclerView() {
        listaAnimales = new ArrayList<>();

        listaAnimales.add(new AnimalReservaPelu("Mirko","26/03/22","16:00"));
        listaAnimales.add(new AnimalReservaPelu("Diva","27/03/22","15:00"));
        listaAnimales.add(new AnimalReservaPelu("Shiro","26/03/22","17:00"));
        listaAnimales.add(new AnimalReservaPelu("aaaaa","26/03/22","17:00"));
        listaAnimales.add(new AnimalReservaPelu("bbbbb","26/03/22","17:00"));
        listaAnimales.add(new AnimalReservaPelu("ccccc","26/03/22","17:00"));
        listaAnimales.add(new AnimalReservaPelu("ddddd","26/03/22","17:00"));
        listaAnimales.add(new AnimalReservaPelu("eeeee","26/03/22","17:00"));
        listaAnimales.add(new AnimalReservaPelu("fffff","26/03/22","17:00"));
    }

    public ArrayList<AnimalReservaPelu> getListaAnimales() {
        return listaAnimales;
    }
}
