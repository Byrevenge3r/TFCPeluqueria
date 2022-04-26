package com.dam.peluqueriacanina.registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.fragmentos.Citas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
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
    Citas citas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);
        fb = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        dbRef = fb.getReference();

        etNombre = findViewById(R.id.etUsuario);
        etApellido = findViewById(R.id.etApellido);
        etUsuario = findViewById(R.id.etUsuario);


        btnSiguienteRegUno = findViewById(R.id.btnSiguienteRegUno);

        btnSiguienteRegUno.setOnClickListener(this);
        citas = new Citas();
    }

    @Override
    public void onClick(View v) {
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellido.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();

        if (v.equals(btnSiguienteRegUno)) {
            if (nombre.isEmpty() || apellidos.isEmpty() || usuario.isEmpty()) {
                Snackbar.make(v, R.string.tst_fill, Snackbar.LENGTH_LONG).show();
            } else {
                // citas.show(getSupportFragmentManager(),"onCreateDialog");

                i = new Intent(this, Registro2.class);
                startActivity(i);

                overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
            }
        }
    }

    private void registrarse(String nombre, String apellido, String usuario) {
        if (check) {

            Snackbar.make(btnSiguienteRegUno, R.string.tst_contra_leng, Snackbar.LENGTH_LONG).show();
        }
    }else

    {
        Snackbar.make(btnSiguienteRegUno, R.string.tst_user_exist, Snackbar.LENGTH_LONG).show();
    }
}
}