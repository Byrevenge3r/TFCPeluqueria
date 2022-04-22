package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.peluqueriacanina.R;

public class Registro3 extends AppCompatActivity implements View.OnClickListener{

    Button btnAtrasRegTres, btnSiguienteRegTres;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);

        btnAtrasRegTres = findViewById(R.id.btnAtrasRegTres);
        btnSiguienteRegTres = findViewById(R.id.btnSiguienteRegTres);

        btnAtrasRegTres.setOnClickListener(this);
        btnSiguienteRegTres.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSiguienteRegTres)) {
            i = new Intent(this, Registro5.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_izquierda,R.anim.animacion_izquierda_izquierda);
        } else if (v.equals(btnAtrasRegTres)) {
            i = new Intent(this, Registro2.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro2.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
    }
}