package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.peluqueriacanina.R;

public class Registro4 extends AppCompatActivity implements View.OnClickListener {

    Button btnAtrasRegCua, btnSiguienteRegCua;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro4);

        btnAtrasRegCua = findViewById(R.id.btnAtrasRegCua);
        btnSiguienteRegCua= findViewById(R.id.btnSiguienteRegCua);

        btnAtrasRegCua.setOnClickListener(this);
        btnSiguienteRegCua.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSiguienteRegCua)) {
            i = new Intent(this, Registro5.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_izquierda,R.anim.animacion_izquierda_izquierda);
        } else if (v.equals(btnAtrasRegCua)) {
            i = new Intent(this, Registro3.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro3.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
    }
}