package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.mainActivity.peluqueria.fragmentosPel.CitasPel;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro1 extends AppCompatActivity implements View.OnClickListener {

    EditText etNombre;
    EditText etApellido;
    EditText etUsuario;


    Button btnRegistrar;
    Button btnVolver;
    FirebaseAuth fAuth;

    DatabaseReference dbRef;
    FirebaseDatabase fb;
    boolean check = false;

    Button btnSiguienteRegUno;
    Intent i;
    CitasPel citasPel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);
        fb = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        dbRef = fb.getReference();

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etUsuario = findViewById(R.id.etUsuario);


        btnSiguienteRegUno = findViewById(R.id.btnSiguienteRegUno);

        btnSiguienteRegUno.setOnClickListener(this);
        citasPel = new CitasPel();
    }

    @Override
    public void onClick(View v) {
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellido.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();


        if (v.equals(btnSiguienteRegUno)) {
            if (TextUtils.isEmpty(nombre)) {
                etNombre.setError("Introduzca un nombre");
            } else if (TextUtils.isEmpty(apellidos)) {
                etApellido.setError("Introduzca los apellidos");
            } else if (TextUtils.isEmpty(usuario)) {
                etUsuario.setError("Introduzca una contraseña");
            } else {
                // citas.show(getSupportFragmentManager(),"onCreateDialog");

                ((MiApplication)getApplicationContext()).setNombre(nombre);
                ((MiApplication)getApplicationContext()).setApellidos(apellidos);
                ((MiApplication)getApplicationContext()).setUsuario(usuario);
                i = new Intent(this, Registro2.class);
                startActivity(i);

                overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
            }
        }
    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
        i = new Intent(this, LoginActivity.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
    }


}