package com.dam.peluqueriacanina.mainActivity.peluqueria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.peluqueriacanina.R;
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
    String numeroTelConduc;
//
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


       rv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
           @Override
           public void onLayoutChange(View view, int i, int i1, int i2, int bottom, int i4, int i5, int i6, int oldBottom) {
               if ( bottom < oldBottom) {
                   rv.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                       }
                   }, 0);
               }

           }
       });

       /*dbr.child("usuarios/"+((MiApplication)getApplicationContext()).getKey()+"/chat").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DataSnapshot> task) {
               if (recoger) {

                   for (DataSnapshot sp:task.getResult().getChildren()) {
                       mensaje = sp.getValue(Chat.class);
                       mensajes.add(mensaje);
                   }

                   rv.setAdapter(adapter);
                   rv.scrollToPosition(adapter.getItemCount()-1);
               }
           }
       });*/

       dbr.child("usuarios/"+ ((MiApplication)getApplicationContext()).getKey()+"/chat").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               mensajes.add(snapshot.getValue(Chat.class));
               rv.setAdapter(adapter);
               rv.scrollToPosition(adapter.getItemCount()-1);
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


       dbr.child("coche/tel").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()) {
                   numeroTelConduc = String.valueOf(snapshot.getValue());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       /*if (mensajes.isEmpty()) {
           SmsManager sms = SmsManager.getDefault();
           sms.sendTextMessage("+34" + numeroTelConduc, null,  ((MiApplication) getApplicationContext()).getKey(), null, null);
       }*/

       rv.setAdapter(adapter);
       if (!mensajes.isEmpty()) {
           rv.scrollToPosition(adapter.getItemCount()-1);
       }

    }


    @Override
    public void onClick(View view) {
       if (view.equals(ibEnviar)) {
           String mensaje = etMensajeIntroducido.getText().toString().trim();
           if (!etMensajeIntroducido.getText().toString().isEmpty()) {
               //   adapter.aniadirMensaje(new Chat(mensaje,"U"));

               String key = dbr.push().getKey();

               chat.put("mensaje",mensaje);
               chat.put("codigoPer","U");
               //Cambiar todo por la ruta correcta
               dbr.child("usuarios").child( ((MiApplication)getApplicationContext()).getKey()).child("chat").child(key).setValue(chat);

               recoger = false;
               rv.setAdapter(adapter);
               rv.scrollToPosition(adapter.getItemCount()-1);
               etMensajeIntroducido.setText("");
           }
       }
    }
}