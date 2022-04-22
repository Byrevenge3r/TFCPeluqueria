package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.peluqueriacanina.R;

public class Registro5 extends AppCompatActivity implements View.OnClickListener {

    Button btnSiguienteRegCuatro, btnMandarOtraVezRegCuatro;
    Intent i;
    EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro5);

        btnMandarOtraVezRegCuatro = findViewById(R.id.btnMandarOtraVezRegCuatro);
        btnSiguienteRegCuatro = findViewById(R.id.btnSiguienteRegCuatro);
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

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