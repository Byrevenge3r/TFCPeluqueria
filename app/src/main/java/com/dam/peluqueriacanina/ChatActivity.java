package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    LinearLayoutManager llm;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    ArrayList<Chat> mensajes;
    ChatAdapter adapter;
    EditText etMensajeIntroducido;
    ImageButton ibEnviar;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
       fdb = FirebaseDatabase.getInstance();
       dbr = fdb.getReference();

       rv = findViewById(R.id.rvChatUsuario);
       llm = new LinearLayoutManager(this);
       etMensajeIntroducido = findViewById(R.id.etChat);
       ibEnviar = findViewById(R.id.btnEnviar);

       ibEnviar.setOnClickListener(this);

       llm.setOrientation(LinearLayoutManager.VERTICAL);
       rv.setLayoutManager(llm);

       mensajes = new ArrayList<>();

      /* dbr.child("usuarios/"+((MiApplication) getApplicationContext()).getKey()+"/chat").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()) {
                   for (DataSnapshot ds: snapshot.getChildren()) {
                       mensajes.add(ds.getValue(Chat.class));
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       if (!mensajes.isEmpty()) {
           //TODO: webada esta
           adapter = new ChatAdapter(mensajes);
           rv.setAdapter(adapter);
       }*/

    }

    @Override
    public void onClick(View view) {
       if (view.equals(ibEnviar)) {
           if (!etMensajeIntroducido.getText().toString().isEmpty()) {
               mensajes.add(new Chat(etMensajeIntroducido.getText().toString().trim(),"U"));
               mensajes.add(new Chat(etMensajeIntroducido.getText().toString().trim(),"C"));
               adapter = new ChatAdapter(mensajes);

               rv.smoothScrollToPosition(mensajes.size());

               rv.setAdapter(adapter);
           }
       }
    }
}