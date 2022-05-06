package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cvPeluqueria, cvVeterinaria, cvTienda, cvNoticias, cvOpciones, cvCerrarSesion;
    Intent i;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    DateTimeFormatter dtf;
    LocalDateTime now;
    Date date1;
    Date date2;
    SimpleDateFormat formatter;
    String mes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference("dia");

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


     /*   dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        try {
                            dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            now = LocalDateTime.now();
                            date1 = formatter.parse(now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear());
                            date2 = formatter.parse(String.valueOf(snapshot.child("dia").getValue()));
                            System.out.println(date2);
                            System.out.println(date1);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (date1.after(date2)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            dbr.setValue(now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear());

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

                            Query applesQuery = dbr.child("coche/reservas/" + mes).orderByChild("fecha").equalTo("Apple");

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


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