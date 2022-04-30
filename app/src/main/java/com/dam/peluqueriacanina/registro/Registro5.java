package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.peluqueriacanina.R;

public class Registro5 extends AppCompatActivity implements View.OnClickListener {

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro5);

    }



    @Override
    public void onClick(View v) {
    //TODO Hacer la ultima clase registrar y comprobar que el codigo de verificacion mandado es correcto

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro4.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
    }


}