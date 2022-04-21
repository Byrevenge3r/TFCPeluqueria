package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.peluqueriacanina.R;

public class Registro4 extends AppCompatActivity implements View.OnClickListener {

    Button btnSiguienteRegCuatro, btnMandarOtraVezRegCuatro;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro4);

        btnMandarOtraVezRegCuatro = findViewById(R.id.btnMandarOtraVezRegCuatro);
        btnSiguienteRegCuatro = findViewById(R.id.btnSiguienteRegCuatro);

        btnSiguienteRegCuatro.setOnClickListener(this);
        btnMandarOtraVezRegCuatro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSiguienteRegCuatro)) {
            //TODO Hacer la ultima clase del registrar
            /*i = new Intent(this, Registrar5.class);
            startActivity(i);*/

            overridePendingTransition(R.anim.animacion_derecha_izquierda,R.anim.animacion_izquierda_izquierda);
        } else if (v.equals(btnMandarOtraVezRegCuatro)) {
            //TODO que mande otra vez el sms
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