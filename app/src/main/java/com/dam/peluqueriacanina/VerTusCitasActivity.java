package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dam.peluqueriacanina.dao.CitasDao;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.CitasDB;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.Cita;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.fragmentos.Citas;
import com.dam.peluqueriacanina.utils.AnimalPeluAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VerTusCitasActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    LinearLayoutManager llm;
    TusCitas tusCitas;
    Cita citas;
    int mes = 0;
    String mesN = "";
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    CitasDao citasDao;
    CitasDB citasDB;
    TusCitasDao daoTusCitas;
    TusCitasDB dbTusCitas;
    TextView tvNoHayCitasVTC;
    Button btnCancelarCita;
    AnimalPeluAdapter adapter;
    SimpleDateFormat formatter;
    Date fecha;
    View vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tus_citas);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference("coche/reservas");

        dbTusCitas = TusCitasDB.getDatabase(this);
        daoTusCitas = dbTusCitas.citaDao();

        citasDB = CitasDB.getDatabase(this);
        citasDao = citasDB.citaDao();

        rv = findViewById(R.id.rvVerTusCitas);
        llm = new LinearLayoutManager(this);
        tvNoHayCitasVTC = findViewById(R.id.tvNoHayCitasVTC);
        btnCancelarCita = findViewById(R.id.btnCancelarCita);

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        fecha = new Date();



        btnCancelarCita.setOnClickListener(this);
        vista = new View(this);

        adapter = new AnimalPeluAdapter((ArrayList<TusCitas>) daoTusCitas.sacarTodo());
        if (daoTusCitas.sacarTodo().isEmpty()) {
            tvNoHayCitasVTC.setVisibility(View.VISIBLE);
        }
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelarCita.setVisibility(View.VISIBLE);
                vista = v;
            }
        });

        rv.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        tusCitas = daoTusCitas.sacarTodo().get(rv.getChildAdapterPosition(vista));
        try {
            fecha = formatter.parse(tusCitas.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int mesActual = cal.get(Calendar.MONTH)+1;

        switch (mesActual) {
            case 1:
                mesN = "enero";
                break;
            case 2:
                mesN = "febrero";
                break;
            case 3:
                mesN = "marzo";
                break;
            case 4:
                mesN = "abril";
                break;
            case 5:
                mesN = "mayo";
                break;
            case 6:
                mesN = "junio";
                break;
            case 7:
                mesN = "julio";
                break;
            case 8:
                mesN = "agosto";
                break;
            case 9:
                mesN = "septiembre";
                break;
            case 10:
                mesN = "octubre";
                break;
            case 11:
                mesN = "noviembre";
                break;
            case 12:
                mesN = "diciembre";
                break;
        }

        Query q = dbr.child(mesN).orderByChild("fecha").equalTo(tusCitas.getFecha());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("erroraa", "onCancelled", databaseError.toException());
            }
        });
    }
}