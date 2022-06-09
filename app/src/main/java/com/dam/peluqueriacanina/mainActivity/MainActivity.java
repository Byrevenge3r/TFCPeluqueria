package com.dam.peluqueriacanina.mainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.mainActivity.noticias.NoticiasActivity;
import com.dam.peluqueriacanina.mainActivity.peluqueria.PeluqueriaActivity;
import com.dam.peluqueriacanina.mainActivity.tienda.TiendaActivity;
import com.dam.peluqueriacanina.mainActivity.veterinaria.VeterinariaActivity;
import com.dam.peluqueriacanina.mainActivity.perfil.AjustesActivity;
import com.dam.peluqueriacanina.registro.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cvPeluqueria, cvVeterinaria, cvTienda, cvNoticias, cvOpciones ;
    Intent i;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    TusCitasDao daoCitas;
    TusCitasDB dbCitas;
    ArrayList<TusCitas> listaCitas;
    CestaDao dao;
    CestaDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        dao.delete(dao.sacarTodo());

        dbCitas = TusCitasDB.getDatabase(this);
        daoCitas = dbCitas.citaDao();

        listaCitas = new ArrayList<>();

        cvPeluqueria = findViewById(R.id.cvPeluqueria);
        cvVeterinaria = findViewById(R.id.cvVeterinaria);
        cvTienda = findViewById(R.id.cvTienda);
        cvNoticias = findViewById(R.id.cvNoticias);
        cvOpciones = findViewById(R.id.cvOpciones);


        cvPeluqueria.setOnClickListener(this);
        cvVeterinaria.setOnClickListener(this);
        cvTienda.setOnClickListener(this);
        cvNoticias.setOnClickListener(this);
        cvOpciones.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mFirebaseUser = fAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            // there is some user logged in

        } else {
            // no one logged in
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void logout() {
        fAuth.signOut();
    }


    @Override
    public void onClick(View v) {
        //TODO transladar a los activities seleccionados
        if (v.equals(cvPeluqueria)) {
            i = new Intent(this, PeluqueriaActivity.class);
            startActivity(i);
        } else if (v.equals(cvVeterinaria)) {
            i = new Intent(this, VeterinariaActivity.class);
            startActivity(i);
        } else if (v.equals(cvTienda)) {
            i = new Intent(this, TiendaActivity.class);
            startActivity(i);
        } else if (v.equals(cvNoticias)) {
            i = new Intent(this, NoticiasActivity.class);
            startActivity(i);
        } else if (v.equals(cvOpciones)) {
            i = new Intent(this, AjustesActivity.class);
            startActivity(i);
        }


    }
}


