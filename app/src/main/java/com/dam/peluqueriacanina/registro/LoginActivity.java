package com.dam.peluqueriacanina.registro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.mainActivity.MainActivity;
import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ERROR";
    public static String COD_EMAIL = "EMAIL";
    public static String COD_CONTRA = "CONTRA";

    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase fdb;
    DatabaseReference dbRef;
    EditText etCorreo, etContrasenia;
    Button btnInicSes;
    Button btnReg;
    CheckBox cbRecuerdame;
    Intent i;
    AnimalesDao dao;
    AnimalesDB db;
    ArrayList<Animal> listaAnimales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        listaAnimales = new ArrayList<>();

        fdb = FirebaseDatabase.getInstance();
        dbRef = fdb.getReference("usuarios");

        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        cbRecuerdame = findViewById(R.id.chbRecuerdame);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnReg = findViewById(R.id.btnRegistro);
        btnInicSes = findViewById(R.id.btnIniciar);

        SharedPreferences spf = getSharedPreferences("chekbox", MODE_PRIVATE);
        String checkbox = spf.getString("remember", "");

        if (fAuth.getCurrentUser() != null) {
            String correo = fAuth.getCurrentUser().getEmail();
            Query q = dbRef.orderByChild("correo").equalTo(correo);
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot datasnap : snapshot.getChildren()) {
                        ((MiApplication) getApplicationContext()).setKey(String.valueOf(datasnap.getKey()));
                        ((MiApplication) getApplicationContext()).setNombre(String.valueOf((datasnap.getValue(User.class)).getNombre()));
                        ((MiApplication) getApplicationContext()).setApellidos((datasnap.getValue(User.class)).getApellidos());
                        ((MiApplication) getApplicationContext()).setUsuario((datasnap.getValue(User.class)).getUsuario());
                        ((MiApplication) getApplicationContext()).setCorreo((datasnap.getValue(User.class)).getCorreo());
                        ((MiApplication) getApplicationContext()).setTelefono((datasnap.getValue(User.class)).getTelefono());
                        ((MiApplication) getApplicationContext()).setDireccion((datasnap.getValue(User.class)).getDireccion());
                        ((MiApplication) getApplicationContext()).setUrlPerfil((datasnap.getValue(User.class)).getUrlPerfil());
                        ((MiApplication) getApplicationContext()).setRecuerdame((datasnap.getValue(User.class)).getRecuerdame());
                    }

                    if (((MiApplication) getApplicationContext()).isRecuerdame()) {
                        i = new Intent(LoginActivity.this,
                                MainActivity.class);
                        i.putExtra(COD_EMAIL, fUser.getEmail());
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


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
        startActivity(i);
        overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
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

                                buscarUsuario(email);

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


    private void buscarUsuario(String correo) {
        HashMap<String, Object> hmRecuerdame = new HashMap<>();
        Query q = dbRef.orderByChild("correo").equalTo(correo);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnap : snapshot.getChildren()) {
                    ((MiApplication) getApplicationContext()).setKey(String.valueOf(datasnap.getKey()));
                    ((MiApplication) getApplicationContext()).setNombre(String.valueOf((datasnap.getValue(User.class)).getNombre()));
                    ((MiApplication) getApplicationContext()).setApellidos((datasnap.getValue(User.class)).getApellidos());
                    ((MiApplication) getApplicationContext()).setUsuario((datasnap.getValue(User.class)).getUsuario());
                    ((MiApplication) getApplicationContext()).setCorreo((datasnap.getValue(User.class)).getCorreo());
                    ((MiApplication) getApplicationContext()).setTelefono((datasnap.getValue(User.class)).getTelefono());
                    ((MiApplication) getApplicationContext()).setDireccion((datasnap.getValue(User.class)).getDireccion());
                    ((MiApplication) getApplicationContext()).setUrlPerfil((datasnap.getValue(User.class)).getUrlPerfil());
                    ((MiApplication) getApplicationContext()).setRecuerdame((datasnap.getValue(User.class)).getRecuerdame());

                    if (cbRecuerdame.isChecked()) {
                        hmRecuerdame.put("recuerdame", true);
                        dbRef.child(((MiApplication) getApplicationContext()).getKey()).updateChildren(hmRecuerdame);
                        hmRecuerdame.clear();
                    } else {
                        hmRecuerdame.put("recuerdame", false);
                        dbRef.child(((MiApplication) getApplicationContext()).getKey()).updateChildren(hmRecuerdame);
                        hmRecuerdame.clear();
                    }
                }
                if (dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey()).isEmpty()) {
                    dbRef = fdb.getReference("usuarios/" + ((MiApplication) getApplicationContext()).getKey() + "/animales");
                    dbRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            for (DataSnapshot sp : task.getResult().getChildren()) {
                                dao.insert(new Animal(sp.getValue(Animal.class).getKey(),
                                        sp.getValue(Animal.class).getKeyU(),
                                        sp.getValue(Animal.class).getUrlI(),
                                        sp.getValue(Animal.class).getNombre(),
                                        sp.getValue(Animal.class).getRaza()));
                            }

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "ERROR");
            }
        });
    }


}