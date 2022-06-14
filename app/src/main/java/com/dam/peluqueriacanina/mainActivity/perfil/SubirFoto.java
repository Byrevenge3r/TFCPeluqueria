package com.dam.peluqueriacanina.mainActivity.perfil;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.dam.peluqueriacanina.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class SubirFoto extends Thread {

    private final Uri uri;
    private static final Semaphore semaforo = new Semaphore(1);
    private StorageReference mStorage;
    private FirebaseDatabase fb;
    private DatabaseReference dbRef;
    User user;
    HashMap<String, Object> fotoPerfil = new HashMap<>();

    public SubirFoto(Uri uri, User user) {
        this.uri = uri;
        this.user = user;
    }

    @Override
    public void run() {
        super.run();
        try {
            semaforo.acquire();

            mStorage = FirebaseStorage.getInstance().getReference();
            fb = FirebaseDatabase.getInstance();
            dbRef = fb.getReference();

            StorageReference filePath = mStorage.child("fotosPerfil/" + user.getKey() + "/fotoPerfil.jpg");
            filePath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mStorage.child("fotosPerfil/" + user.getKey() + "/fotoPerfil.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setUrlPerfil(uri.toString());
                            fotoPerfil.clear();
                            fotoPerfil.put("nombre", user.getNombre());
                            fotoPerfil.put("apellidos", user.getApellidos());
                            fotoPerfil.put("usuario", user.getUsuario());
                            fotoPerfil.put("correo", user.getCorreo());
                            fotoPerfil.put("telefono", user.getTelefono());
                            fotoPerfil.put("direccion", user.getDireccion());
                            fotoPerfil.put("urlPerfil", user.getUrlPerfil());
                            fotoPerfil.put("key", user.getKey());
                            fotoPerfil.put("recuerdame", user.getRecuerdame());

                            dbRef.child("usuarios/" + user.getKey()).updateChildren(fotoPerfil);
                        }
                    });
                }
            });
            semaforo.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
