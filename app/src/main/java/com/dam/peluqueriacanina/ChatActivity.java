package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dam.peluqueriacanina.model.Chat;
import com.dam.peluqueriacanina.utils.ChatAdapter;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    LinearLayoutManager llm;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    ArrayList<Chat> mensajes;
    ChatAdapter adapter;
    EditText etMensajeIntroducido;
    Button ibEnviar;
    HashMap<String,Object> chat;
    Chat mensaje;
    boolean recoger = true;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
       fdb = FirebaseDatabase.getInstance();
       dbr = fdb.getReference();

       chat = new HashMap<>();

       rv = findViewById(R.id.rvChatUsuario);
       llm = new LinearLayoutManager(this);
       etMensajeIntroducido = findViewById(R.id.etChat);
       ibEnviar = findViewById(R.id.btnEnviarMensaje);

       ibEnviar.setOnClickListener(this);

       llm.setOrientation(LinearLayoutManager.VERTICAL);
       rv.setLayoutManager(llm);

       mensajes = new ArrayList<>();
       adapter = new ChatAdapter(mensajes);

       //Funciona pero no recoge cuando se inicia la app

       dbr.child("usuarios/"+"-N1optheLLOVSoaaBkiS"+"/chat").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DataSnapshot> task) {
               if (recoger) {

                   for (DataSnapshot sp:task.getResult().getChildren()) {
                       mensaje = sp.getValue(Chat.class);
                       mensajes.add(mensaje);
                   }

                   rv.setAdapter(adapter);

               }
           }
       });


       rv.setAdapter(adapter);


    }



    @Override
    public void onClick(View view) {
       if (view.equals(ibEnviar)) {
           String mensaje = etMensajeIntroducido.getText().toString().trim();
           if (!etMensajeIntroducido.getText().toString().isEmpty()) {
              adapter.aniadirMensaje(new Chat(mensaje,"U"));

               String key = dbr.push().getKey();

               chat.put("mensaje",mensaje);
               chat.put("codigoPer","U");
               //Cambiar todo por la ruta correcta
               dbr.child("usuarios").child("-N1optheLLOVSoaaBkiS").child("chat").child(key).setValue(chat);

               recoger = false;
               rv.setAdapter(adapter);
               rv.scrollToPosition(adapter.getItemCount()-1);
               etMensajeIntroducido.setText("");
           }
       }
    }
}