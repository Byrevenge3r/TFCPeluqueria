package com.dam.peluqueriacanina.registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro2 extends AppCompatActivity implements View.OnClickListener {

    EditText etCorreo;
    EditText etConfCorreo;
    EditText etContra;
    EditText etConfContra;
    Button btnAtrasRegDos, btnSiguienteRegDos;
    Intent i;
    FirebaseAuth fAuth;

    DatabaseReference dbRef;
    FirebaseDatabase fb;
    boolean check = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        fb = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        dbRef = fb.getReference();

        etCorreo = findViewById(R.id.etEmailRegDos);
        etConfCorreo = findViewById(R.id.etConfirmarEmailRegDos);
        etContra = findViewById(R.id.etContraRegDos);
        etConfContra = findViewById(R.id.etConfContraRegDos);

        btnAtrasRegDos = findViewById(R.id.btnAtrasRegDos);
        btnSiguienteRegDos = findViewById(R.id.btnSiguienteRegDos);

        btnAtrasRegDos.setOnClickListener(this);
        btnSiguienteRegDos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String correo = etCorreo.getText().toString().trim();
        String confcorreo = etConfCorreo.getText().toString().trim();
        String contra = etContra.getText().toString().trim();
        String confcontra = etConfContra.getText().toString().trim();

        if (v.equals(btnSiguienteRegDos)) {

            /*if (TextUtils.isEmpty(correo)) {
                etCorreo.setError("Introduzca un correo");
            } else if (TextUtils.isEmpty(confcorreo)) {
                etConfCorreo.setError("Introduzca una confirmacion de correo");
            } else if (TextUtils.isEmpty(contra)) {
                etContra.setError("Introduzca una contraseña");
            } else if (TextUtils.isEmpty(correo)) {
                etConfContra.setError("Introduzca una confirmacion de contraseña");
            } else if (etContra.length() < 8) {
                etContra.setError("La contraseña debe tener al menos 8 caracteres");
            } else if (etConfContra.length() < 8) {
                etConfContra.setError("La contraseña debe tener al menos 8 caracteres");
            } else if (!etCorreo.equals(etConfCorreo )) {
                Snackbar.make(v, R.string.correo_coincide, Snackbar.LENGTH_LONG).show();
            } else if (!etContra.equals(etConfContra)) {
                Snackbar.make(v, R.string.contra_coincide, Snackbar.LENGTH_LONG).show();
                //arreglar error
            } else  {*/
                ((MiApplication) getApplicationContext()).setCorreo(correo);
                ((MiApplication) getApplicationContext()).setContrasenia(contra);

                i = new Intent(this, Registro3.class);
                startActivity(i);

                overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
            //}
        } else if (v.equals(btnAtrasRegDos)) {
            i = new Intent(this, Registro1.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro1.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
    }
}