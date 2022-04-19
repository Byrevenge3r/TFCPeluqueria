package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cvPeluqueria, cvVeterinaria, cvTienda, cvNoticias, cvOpciones, cvCerrarSesion;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvPeluqueria = findViewById(R.id.cvPeluqueria);
        cvVeterinaria = findViewById(R.id.cvVeterinaria);
        cvTienda = findViewById(R.id.cvTienda);
        cvNoticias = findViewById(R.id.cvNoticias);
        cvOpciones = findViewById(R.id.cvOpciones);
        cvCerrarSesion = findViewById(R.id.cvCerrarSesion);

        cvPeluqueria.setOnClickListener(this);
        cvVeterinaria.setOnClickListener(this);
        cvTienda.setOnClickListener(this);
        cvNoticias.setOnClickListener(this);
        cvOpciones.setOnClickListener(this);
        cvCerrarSesion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //TODO transladar a los activities seleccionados
        if (v.equals(cvPeluqueria)) {
            i = new Intent(this, PeluqueriaActivity.class);
            startActivity(i);
        } else if (v.equals(cvVeterinaria)) {
            Toast.makeText(this,"Veterinaria",Toast.LENGTH_SHORT).show();
        } else if (v.equals(cvTienda)) {
            Toast.makeText(this,"Tienda",Toast.LENGTH_SHORT).show();
        } else if (v.equals(cvNoticias)) {
            Toast.makeText(this,"Noticias",Toast.LENGTH_SHORT).show();
        } else if (v.equals(cvOpciones)) {
            Toast.makeText(this,"Opciones",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Cerrar sesion",Toast.LENGTH_SHORT).show();
        }



    }
}