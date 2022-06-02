package com.dam.peluqueriacanina.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dam.peluqueriacanina.mainActivity.tienda.TiendaActivity;
import com.dam.peluqueriacanina.perfil.AjustesActivity;
import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.mainActivity.noticias.NoticiasActivity;
import com.dam.peluqueriacanina.mainActivity.peluqueria.PeluqueriaActivity;
import com.dam.peluqueriacanina.registro.LoginActivity;
import com.dam.peluqueriacanina.mainActivity.veterinaria.VeterinariaActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    DateTimeFormatter dtf;
    LocalDateTime now;
    TusCitasDao daoCitas;
    TusCitasDB dbCitas;
    Date date1;
    Date date2;
    Date dateModificar;
    SimpleDateFormat formatter;
    String mes = "";
    ArrayList<TusCitas> listaCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference("dia");

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();


        dbCitas = TusCitasDB.getDatabase(this);
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





        //Borrar fechas anteriores al dia actual
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


                            for (int i = 1;i < now.getDayOfMonth();i++) {
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
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser mFirebaseUser=fAuth.getCurrentUser();
        if(mFirebaseUser!=null){
            // there is some user logged in

        }else{
            // no one logged in
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

public void logout(){
        fAuth.signOut();
}
    private void borrarFechasDao() throws ParseException {
        listaCitas = (ArrayList<TusCitas>) daoCitas.sacarTodo();
        for (int i = 0; i < listaCitas.size(); i++) {
            dateModificar = formatter.parse(listaCitas.get(i).getCitaFecha());
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
            i = new Intent(this, TiendaActivity.class);
            startActivity(i);
        } else if (v.equals(cvNoticias)) {
            i = new Intent(this, NoticiasActivity.class);
            startActivity(i);
        } else if (v.equals(cvOpciones)) {
            i = new Intent(this, AjustesActivity.class);
            startActivity(i);
        }else if (v.equals(cvCerrarSesion)) {
          logout();
            i = new Intent(this, LoginActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT).show();
        }


    }
}


