package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.peluqueriacanina.R;

public class Registro2 extends AppCompatActivity implements View.OnClickListener {

    Button btnAtrasRegDos, btnSiguienteRegDos;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        btnAtrasRegDos = findViewById(R.id.btnAtrasRegDos);
        btnSiguienteRegDos = findViewById(R.id.btnSiguienteRegDos);

        btnAtrasRegDos.setOnClickListener(this);
        btnSiguienteRegDos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if (v.equals(btnSiguienteRegDos)) {
            i = new Intent(this, Registro3.class);
            startActivity(i);

           overridePendingTransition(R.anim.animacion_derecha_izquierda,R.anim.animacion_izquierda_izquierda);
        } else if (v.equals(btnAtrasRegDos)) {
           i = new Intent(this, Registro1.class);
           startActivity(i);

           overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
       }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro1.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
    }
}