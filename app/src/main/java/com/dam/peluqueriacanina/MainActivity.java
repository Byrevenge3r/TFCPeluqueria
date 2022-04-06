package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dam.peluqueriacanina.model.DatosMenuBotonesReciclerView;
import com.dam.peluqueriacanina.utils.MenuBotonesAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager llm;
    MenuBotonesAdapter adapter;
    DatosMenuBotonesReciclerView datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvBotonesMenu);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);

        datos = new DatosMenuBotonesReciclerView();

        adapter = new MenuBotonesAdapter(datos.getListaDatosBotones());
        rv.smoothScrollToPosition(datos.getListaDatosBotones().size()-1);
        rv.setAdapter(adapter);
    }
}