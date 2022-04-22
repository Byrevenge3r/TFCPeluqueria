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

    Button btnSiguienteRegCuatro, btnMandarOtraVezRegCuatro;
    Intent i;
    EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    TextWatcher t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro5);

        btnMandarOtraVezRegCuatro = findViewById(R.id.btnMandarOtraVezRegCuatro);
        btnSiguienteRegCuatro = findViewById(R.id.btnSiguienteRegCua);
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

        btnSiguienteRegCuatro.setOnClickListener(this);
        btnMandarOtraVezRegCuatro.setOnClickListener(this);

        listenerEditText(inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6);

    }

    private void listenerEditText(EditText inputCode1, EditText inputCode2, EditText inputCode3, EditText inputCode4, EditText inputCode5, EditText inputCode6) {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputCode5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputCode6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onClick(View v) {
    //TODO Hacer la ultima clase registrar

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro4.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha,R.anim.animacion_izquierda_derecha);
    }


}