package com.dam.peluqueriacanina.model;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.dam.peluqueriacanina.R;

import java.util.ArrayList;

public class DatosMenuBotonesReciclerView {

    private ArrayList<MenuBotones> listaDatosBotones;

    public DatosMenuBotonesReciclerView() {
        listaDatosBotones = new ArrayList<>();

        listaDatosBotones.add(new MenuBotones("Peluqueria", R.drawable.perro,R.drawable.fondo_card1));
        listaDatosBotones.add(new MenuBotones("Veterinaria",R.drawable.tiempo,R.drawable.fondo_card2));
        listaDatosBotones.add(new MenuBotones("Rodri",R.drawable.mona_lisa_foto_perfil,R.drawable.fondo_card2));
        listaDatosBotones.add(new MenuBotones("Chupala",R.drawable.perro_icono_vet_rv,R.drawable.fondo_card4));


    }

    public ArrayList<MenuBotones> getListaDatosBotones() {
        return listaDatosBotones;
    }
}
