package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dam.peluqueriacanina.model.DatosTienda;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PushearProductos2 extends AppCompatActivity {

    String key;
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = fdb.getReference();
    ArrayList<DatosTienda> listaTienda = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushear_productos2);

        listaTienda.add(new DatosTienda("pienso perro", "1kg", "", "Pienso natural con ingredientes naturales","alimentacion"));


        HashMap<String, Object> usuario = new HashMap<>();

        for (int i = 0; i < listaTienda.size(); i++) {
            key = dbRef.push().getKey();
            usuario.put("nombre", listaTienda.get(i).getNombre());
            usuario.put("cantidad", listaTienda.get(i).getCantidad());
            usuario.put("foto", listaTienda.get(i).getFoto());
            usuario.put("detalle", listaTienda.get(i).getDetalle());
            usuario.put("tipo", listaTienda.get(i).getTipo());
            dbRef.child("productos tienda").child(key).setValue(usuario);
        }

    }

}