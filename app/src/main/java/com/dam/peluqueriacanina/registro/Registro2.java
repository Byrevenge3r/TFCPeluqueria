package com.dam.peluqueriacanina.registro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Registro2 extends AppCompatActivity implements View.OnClickListener {

    EditText etCorreo;
    EditText etConfCorreo;
    EditText etContra;
    EditText etdireccion;
    EditText etConfContra;
    Button btnAtrasRegDos, btnSiguienteRegDos;
    Intent i;
    FirebaseAuth fAuth;

    DatabaseReference dbRef;
    FirebaseDatabase fb;

    ArrayList<User> listaUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        fb = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        dbRef = fb.getReference("usuarios");

        etCorreo = findViewById(R.id.etEmailRegDos);
        etConfCorreo = findViewById(R.id.etConfirmarEmailRegDos);
        etContra = findViewById(R.id.etContraRegDos);
        etConfContra = findViewById(R.id.etConfContraRegDos);
        etdireccion = findViewById(R.id.etDireccion);

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
        String direccion = etdireccion.getText().toString().trim();


        if (v.equals(btnSiguienteRegDos)) {
            Query q = dbRef.orderByChild("correo").equalTo(correo);
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        if (!TextUtils.isEmpty(correo)) {
                            if (!comprobarArroba(correo)) {
                                etCorreo.setError("El correo debe contener un @");
                            }
                        } else {
                            etCorreo.setError("Introduzca un correo");
                        }

                        if (TextUtils.isEmpty(confcorreo)) {
                            etConfCorreo.setError("Introduzca una confirmacion de correo");
                        } else if (TextUtils.isEmpty(contra)) {
                            Snackbar.make(v, R.string.contrasenia_vacia, Snackbar.LENGTH_LONG).show();
                        } else if (TextUtils.isEmpty(confcontra)) {
                            Snackbar.make(v, R.string.contrasenia_vacia, Snackbar.LENGTH_LONG).show();
                        } else if (contra.length() < 8) {
                            Snackbar.make(v, R.string.longitud_contra, Snackbar.LENGTH_LONG).show();
                        } else if (confcontra.length() < 8) {
                            Snackbar.make(v, R.string.longitud_contra, Snackbar.LENGTH_LONG).show();
                        } else if (!correo.equals(confcorreo)) {
                            Snackbar.make(v, R.string.correo_coincide, Snackbar.LENGTH_LONG).show();
                        } else if (!contra.equals(confcontra)) {
                            Snackbar.make(v, R.string.contra_coincide, Snackbar.LENGTH_LONG).show();
                        } else if (!hasUpperCase(contra)) {
                            Snackbar.make(v, R.string.contra_minuscula, Snackbar.LENGTH_LONG).show();
                        } else if (!hasSymbol(contra)) {
                            Snackbar.make(v, R.string.simbolo_contra, Snackbar.LENGTH_LONG).show();
                        } else if (TextUtils.isEmpty(direccion)) {
                            Snackbar.make(v, R.string.direccion_vacia, Snackbar.LENGTH_LONG).show();
                        } else {
                            ((MiApplication) getApplicationContext()).setCorreo(correo);
                            ((MiApplication) getApplicationContext()).setContrasenia(contra);
                            ((MiApplication) getApplicationContext()).setDireccion(direccion);

                            i = new Intent(Registro2.this, Registro3.class);
                            startActivity(i);

                            overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
                        }
                    } else {
                        etCorreo.setError("Este correo ya existe");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else if (v.equals(btnAtrasRegDos)) {
            i = new Intent(this, Registro1.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
        }


    }

    private void buscarUsuario(String correo) {


    }

    private boolean comprobarArroba(String correo) {
        for (int i = 0; i < correo.length(); i++) {
            if (correo.charAt(i) == 64) {
                return true;
            }
        }
        return false;
    }

    private boolean hasUpperCase(CharSequence data) {
        String contra = etContra.getText().toString().trim();
        contra = String.valueOf((data));
        return !contra.equals(contra.toLowerCase());
    }

    private boolean hasSymbol(CharSequence data) {
        String contra = etContra.getText().toString().trim();
        contra = String.valueOf((data));

        for (int i = 0; i < contra.length(); i++) {

            if (Character.isDigit(contra.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro1.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
    }
}