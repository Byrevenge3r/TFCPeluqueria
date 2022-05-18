package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dam.peluqueriacanina.model.Chat;
import com.dam.peluqueriacanina.utils.ChatAdapter;
import com.dam.peluqueriacanina.utils.MiApplication;
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
    ImageButton ibEnviar;
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
       ibEnviar = findViewById(R.id.btnEnviar);

       ibEnviar.setOnClickListener(this);

       llm.setOrientation(LinearLayoutManager.VERTICAL);
       rv.setLayoutManager(llm);

       mensajes = new ArrayList<>();


       //Peta aqui mirar mañana un momento hay que cambiar las claves y que recoja los objetos directamente

       dbr.child("usuarios/"+"-N1optheLLOVSoaaBkiS"+"/chat").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               if (recoger) {

                   mensaje = snapshot.getValue(Chat.class);
                   mensajes.add(mensaje);


               }
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot snapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });



       adapter = new ChatAdapter(mensajes);
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
           }
       }
    }
}