package com.dam.peluqueriacanina.registro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.material.snackbar.Snackbar;

public class Registro3 extends AppCompatActivity implements View.OnClickListener {

    Button btnAtrasRegTres, btnSiguienteRegTres;
    EditText etTelefonoReg;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);

        btnAtrasRegTres = findViewById(R.id.btnAtrasRegTres);
        btnSiguienteRegTres = findViewById(R.id.btnSiguienteRegTres);
        etTelefonoReg = findViewById(R.id.etTelefonoRegTres);

        btnAtrasRegTres.setOnClickListener(this);
        btnSiguienteRegTres.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String telefono = etTelefonoReg.getText().toString().trim();
        if (v.equals(btnSiguienteRegTres)) {
            if (telefono.isEmpty()) {
                Snackbar.make(v, R.string.tst_fill, Snackbar.LENGTH_LONG).show();

            } else if (v.equals(btnSiguienteRegTres)) {

                ((MiApplication) getApplicationContext()).setTelefono(telefono);

                i = new Intent(this, Registro4.class);
                startActivity(i);
                overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
            }


        } else if (v.equals(btnAtrasRegTres)) {
            i = new Intent(this, Registro2.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro2.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
    }


}