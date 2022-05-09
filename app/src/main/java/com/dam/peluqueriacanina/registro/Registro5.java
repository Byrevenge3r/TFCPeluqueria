package com.dam.peluqueriacanina.registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.peluqueriacanina.LoginActivity;
import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro5 extends AppCompatActivity implements View.OnClickListener {

    Button btnFinalizar;
    Button btnFInalizar;
    Intent i;
    User user;
    FirebaseDatabase fb;
    DatabaseReference dbRef;


    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro5);

        fb = FirebaseDatabase.getInstance();
        dbRef = fb.getReference("usuarios");

        user = new User();
        btnFinalizar = findViewById(R.id.btnSiguienteRegCua);
        btnFinalizar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
            registrar();
    }


    private void registrar() {
        user = new User(((MiApplication) getApplicationContext()).getNombre(),
                ((MiApplication) getApplicationContext()).getApellidos(),
                ((MiApplication) getApplicationContext()).getUsuario(),
                ((MiApplication) getApplicationContext()).getCorreo(),
                ((MiApplication) getApplicationContext()).getContrasenia(),
                ((MiApplication) getApplicationContext()).getTelefono());

        HashMap<String,Object> usuario = new HashMap<>();

        usuario.put("nombre",user.getNombre());
        usuario.put("apellidos",user.getApellidos());
        usuario.put("usuario",user.getUsuario());
        usuario.put("correo",user.getCorreo());
        usuario.put("contrasenia",user.getContrasenia());
        usuario.put("telefono",user.getTelefono());

        dbRef.push().updateChildren(usuario);

        //TODO: Hacer que se pase los datos al login y que se setee automaticamente los datos (el email y la contraseña)
        //TODO: Hacer que se registre en el authentification user de firebase
        //TODO: Hacer las comprobaciones del que el email sea igual a su confirmacion igual que al contraseña
        // ademas de hacer que la contraseña tenga que tener una mayuscula y sea 8 caracteres de largo
        i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro4.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
    }


}
