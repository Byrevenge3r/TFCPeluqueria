package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dam.peluqueriacanina.dao.CitasDao;
import com.dam.peluqueriacanina.db.CitasDB;
import com.dam.peluqueriacanina.entity.Cita;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cvPeluqueria, cvVeterinaria, cvTienda, cvNoticias, cvOpciones, cvCerrarSesion;
    Intent i;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    DateTimeFormatter dtf;
    LocalDateTime now;
    CitasDao daoCitas;
    CitasDB dbCitas;
    Date date1;
    Date date2;
    Date dateModificar;
    SimpleDateFormat formatter;
    String mes = "";
    ArrayList<Cita> listaCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference("dia");

        dbCitas = CitasDB.getDatabase(this);
        daoCitas = dbCitas.citaDao();

        listaCitas = new ArrayList<>();

        cvPeluqueria = findViewById(R.id.cvPeluqueria);
        cvVeterinaria = findViewById(R.id.cvVeterinaria);
        cvTienda = findViewById(R.id.cvTienda);
        cvNoticias = findViewById(R.id.cvNoticias);
        cvOpciones = findViewById(R.id.cvOpciones);
        cvCerrarSesion = findViewById(R.id.cvCerrarSesion);

        cvPeluqueria.setOnClickListener(this);
        cvVeterinaria.setOnClickListener(this);
        cvTienda.setOnClickListener(this);
        cvNoticias.setOnClickListener(this);
        cvOpciones.setOnClickListener(this);
        cvCerrarSesion.setOnClickListener(this);

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        date1 = new Date();
        date2 = new Date();
        dateModificar = new Date();

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        try {
                            dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            now = LocalDateTime.now();
                            date1 = formatter.parse(now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear());
                            date2 = formatter.parse(String.valueOf(snapshot.getValue()));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (date1.after(date2)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            dbr.setValue(now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear());
                            dbr = fdb.getReference();
                            switch (now.getMonthValue()) {
                                case 1:
                                    mes = "enero";
                                    break;
                                case 2:
                                    mes = "febrero";
                                    break;
                                case 3:
                                    mes = "marzo";
                                    break;
                                case 4:
                                    mes = "abril";
                                    break;
                                case 5:
                                    mes = "mayo";
                                    break;
                                case 6:
                                    mes = "junio";
                                    break;
                                case 7:
                                    mes = "julio";
                                    break;
                                case 8:
                                    mes = "agosto";
                                    break;
                                case 9:
                                    mes = "septiembre";
                                    break;
                                case 10:
                                    mes = "octubre";
                                    break;
                                case 11:
                                    mes = "noviembre";
                                    break;
                                case 12:
                                    mes = "diciembre";
                                    break;
                            }
                            for (int i = 0;i < now.getDayOfMonth();i++) {
                                Query q = dbr.child("coche/reservas/" + mes).orderByChild("fecha").equalTo((now.getDayOfMonth()-i) + "/" + now.getMonthValue() + "/" + now.getYear());
                                q.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                            try {
                                                borrarFechasDao();
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("erroraa", "onCancelled", databaseError.toException());
                                    }
                                });
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    //Puede fallar no lo he probado aun pero vamos estoy al 90% seguro de que no
    private void borrarFechasDao() throws ParseException {
        listaCitas = (ArrayList<Cita>) daoCitas.sacarTodo();
        for (int i = 0; i < listaCitas.size(); i++) {
            dateModificar = formatter.parse(listaCitas.get(i).getFecha());
            if (date1.after(dateModificar)) {
                daoCitas.delete(listaCitas.get(i));
            }
        }
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
            Toast.makeText(this, "Tienda", Toast.LENGTH_SHORT).show();
        } else if (v.equals(cvNoticias)) {
            Toast.makeText(this, "Noticias", Toast.LENGTH_SHORT).show();
        } else if (v.equals(cvOpciones)) {
            Toast.makeText(this, "Opciones", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT).show();
        }


    }
}