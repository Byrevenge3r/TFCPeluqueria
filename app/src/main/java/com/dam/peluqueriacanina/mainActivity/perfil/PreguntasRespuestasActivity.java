package com.dam.peluqueriacanina.mainActivity.perfil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.dam.peluqueriacanina.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PreguntasRespuestasActivity extends AppCompatActivity {

    ExpandableListView elvPreguntasRespuestas;
    ArrayList<String> listGroup = new ArrayList<>();
    HashMap<String,ArrayList<String>> listChild = new HashMap<>();
    PreguntasRespuestasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_respuestas);

        elvPreguntasRespuestas = findViewById(R.id.elvPreguntasRespuestas);


        for (int i = 0; i <= 10;i++) {
            listGroup.add("group"+i);

            ArrayList<String> arrayList = new ArrayList<>();

            for (int x = 0;x < 5; x++) {
                arrayList.add("item"+x);
            }
            listChild.put(listGroup.get(i),arrayList);
        }

        adapter = new PreguntasRespuestasAdapter(listGroup,listChild);
        elvPreguntasRespuestas.setAdapter(adapter);
    }
}