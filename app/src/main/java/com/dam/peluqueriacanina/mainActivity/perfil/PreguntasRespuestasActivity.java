package com.dam.peluqueriacanina.mainActivity.perfil;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.PreguntaRespuesta;
import com.dam.peluqueriacanina.utils.PreguntasRespuestasAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PreguntasRespuestasActivity extends AppCompatActivity {

    ExpandableListView elvPreguntasRespuestas;
    ArrayList<String> listGroup = new ArrayList<>();
    HashMap<String, ArrayList<String>> listChild = new HashMap<>();
    ArrayList<PreguntaRespuesta> listaPreguntasRespuestas = new ArrayList<>();
    PreguntasRespuestasAdapter adapter;
    FirebaseDatabase fdb;
    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_respuestas);

        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();
        elvPreguntasRespuestas = findViewById(R.id.elvPreguntasRespuestas);

        dbr.child("preguntasRespuestas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()) {
                    for (DataSnapshot sp : task.getResult().getChildren()) {
                        listaPreguntasRespuestas.add(sp.getValue(PreguntaRespuesta.class));
                    }

                    for (int i = 0; i < listaPreguntasRespuestas.size(); i++) {
                        listGroup.add(listaPreguntasRespuestas.get(i).getTitulo());

                        ArrayList<String> arrayList = new ArrayList<>();

                        arrayList.add(listaPreguntasRespuestas.get(i).getRespuesta());

                        listChild.put(listGroup.get(i), arrayList);
                    }

                    adapter = new PreguntasRespuestasAdapter(listGroup, listChild);
                    elvPreguntasRespuestas.setAdapter(adapter);

                }
            }
        });
    }
}