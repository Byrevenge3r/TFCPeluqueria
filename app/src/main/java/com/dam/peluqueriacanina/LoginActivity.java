package com.dam.peluqueriacanina;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.registro.Registro1;
import com.dam.peluqueriacanina.registro.Registro2;
import com.dam.peluqueriacanina.utils.miApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ERROR";
    public static String COD_EMAIL = "EMAIL";


    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase fdb;
    DatabaseReference dbRef;
    EditText etCorreo, etContrasenia;
    Button btnInicSes;
    Button btnReg;
    Intent i;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        etCorreo.setText(result.getData().getStringExtra(Registro2.CLAVE_USER));
                        etContrasenia.setText(result.getData().getStringExtra(Registro2.CLAVE_CONTRA));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        fdb = FirebaseDatabase.getInstance();
        dbRef = fdb.getReference("usuarios");

        etCorreo = findViewById(R.id.etCorreo);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnReg = findViewById(R.id.btnRegistro);
        btnInicSes= findViewById(R.id.btnIniciar);


        btnInicSes.setOnClickListener(this);
        btnReg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnInicSes)) {
            acceder();
        } else if (v.equals(btnReg)) {
            registrar();
        }
    }


    private void registrar() {
        Intent i = new Intent(this, Registro1.class);
        startForResult.launch(i);
    }


    private void acceder() {
        String email = etCorreo.getText().toString();
        String contra = etContrasenia.getText().toString();
        if (email.isEmpty() || contra.isEmpty()) {
            Toast.makeText(LoginActivity.this, R.string.no_data, Toast.LENGTH_LONG).show();
        } else {
            fAuth.signInWithEmailAndPassword(email, contra)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fUser = fAuth.getCurrentUser();
                                i = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                i.putExtra(COD_EMAIL, fUser.getEmail());
                                buscarUsuario(email,contra);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        getString(R.string.msj_no_accede),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void buscarUsuario(String correo,String contra){
        Query q = dbRef.orderByChild("correo").equalTo(correo);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnap : snapshot.getChildren()){
                    ((miApplication)getApplicationContext()).setNombre((datasnap.getValue(User.class)).getNom());
                    dbRef.child(datasnap.getValue(User.class).getNom()+"/contra").setValue(contra);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,"ERROR");
            }
        });
    }



}