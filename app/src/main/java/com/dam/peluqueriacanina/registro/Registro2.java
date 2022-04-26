package com.dam.peluqueriacanina.registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.peluqueriacanina.R;
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

        etCorreo = findViewById(R.id.etCorreo);
        etConfCorreo = findViewById(R.id.etConfirmarEmailRegDos);
        etContra = findViewById(R.id.etContrasenia);
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
            if (correo.isEmpty() || confcorreo.isEmpty() || contra.isEmpty() || confcontra.isEmpty()) {
                Snackbar.make(v, R.string.tst_fill, Snackbar.LENGTH_LONG).show();
            } else if (v.equals(btnSiguienteRegDos)) {
                i = new Intent(this, Registro3.class);
                startActivity(i);

                overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
            } else if (v.equals(btnAtrasRegDos)) {
                i = new Intent(this, Registro1.class);
                startActivity(i);

                overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
            }
        }
    }
    private void registrarse( String correo, String Confcorreo, String contra, String confContra) {
        if (check){
            if (contra.length()>=6){
                if (contra.equals(confContra) ){
                    fAuth.createUserWithEmailAndPassword(correo, contra)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dbRef.child("usuarios").child(nombre).setValue(new User(nombre, contra, correo));
                                        Intent data = new Intent();
                                        data.putExtra(CLAVE_USER, correo);
                                        data.putExtra(CLAVE_CONTRA, contra);
                                        setResult(RESULT_OK,data);
                                        finish();
                                    } else {
                                        Snackbar.make(btnRegistrar, R.string.tst_correo_exist, Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });
                }else{
                    Snackbar.make(btnRegistrar,R.string.tst_contra_coin,Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(btnRegistrar,R.string.tst_contra_leng,Snackbar.LENGTH_LONG).show();
            }
        }else{
            Snackbar.make(btnRegistrar,R.string.tst_user_exist,Snackbar.LENGTH_LONG).show();
        }
    }

        @Override
        public void onBackPressed(){
            super.onBackPressed();
            i = new Intent(this, Registro1.class);
            startActivity(i);

            overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
        }

}