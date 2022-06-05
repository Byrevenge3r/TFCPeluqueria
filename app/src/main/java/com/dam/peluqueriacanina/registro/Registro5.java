package com.dam.peluqueriacanina.registro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro5 extends AppCompatActivity implements View.OnClickListener {

    public static final String CLAVE_USER = "USER";
    public static final String CLAVE_CONTRA = "CONTRA";
    Button btnFinalizar;

    Intent i;
    User user;
    FirebaseDatabase fb;
    FirebaseAuth fAuth;
    DatabaseReference dbRef;
    String key;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro5);

        fb = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        dbRef = fb.getReference();

        user = new User();
        btnFinalizar = findViewById(R.id.btnSiguienteRegCua);
        btnFinalizar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(btnFinalizar)) {
            registrar();
            registrarAuth(user.getCorreo(), ((MiApplication) getApplicationContext()).getContrasenia());
            i = new Intent(this, LoginActivity.class);
            startActivity(i);


        }
    }


    private void registrarAuth(String correo, String contra) {
        fAuth.createUserWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(btnFinalizar, "tusmuertos", Snackbar.LENGTH_LONG)
                                    .show();

                        } else {

                            Snackbar.make(btnFinalizar, R.string.tst_correo_exist, Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }


    private void registrar() {
        key = dbRef.push().getKey();
        ((MiApplication) getApplicationContext()).setKey(key);
        user = new User(((MiApplication) getApplicationContext()).getNombre(),
                ((MiApplication) getApplicationContext()).getApellidos(),
                ((MiApplication) getApplicationContext()).getUsuario(),
                ((MiApplication) getApplicationContext()).getCorreo(),
                ((MiApplication) getApplicationContext()).getTelefono(),
                ((MiApplication) getApplicationContext()).getDireccion(),
                ((MiApplication) getApplicationContext()).getKey(), "");

        HashMap<String, Object> usuario = new HashMap<>();

        usuario.put("nombre", user.getNombre());
        usuario.put("apellidos", user.getApellidos());
        usuario.put("usuario", user.getUsuario());
        usuario.put("correo", user.getCorreo());
        usuario.put("telefono", user.getTelefono());
        usuario.put("direccion", user.getDireccion());
        usuario.put("urlPerfil","");
        usuario.put("key", user.getKey());

        dbRef.child("usuarios").child(key).updateChildren(usuario);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }


}
