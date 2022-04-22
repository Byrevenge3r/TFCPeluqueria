package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.fragmentos.Citas;

public class Registro1 extends AppCompatActivity implements View.OnClickListener {

    Button btnSiguienteRegUno;
    Intent i;
    Citas citas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);

        btnSiguienteRegUno = findViewById(R.id.btnSiguienteRegUno);

        btnSiguienteRegUno.setOnClickListener(this);
        citas = new Citas();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSiguienteRegUno)) {
           // citas.show(getSupportFragmentManager(),"onCreateDialog");

            i = new Intent(this, Registro2.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_izquierda,R.anim.animacion_izquierda_izquierda);
        }
    }
}