package com.dam.peluqueriacanina.mainActivity.peluqueria;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.UriDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.UriDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.mainActivity.peluqueria.citas.VerTusCitasActivity;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PeluqueriaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CLAVE_ANIMAL = "ANIMAL_PEL";
    RecyclerView rv;
    LinearLayoutManager llm;
    MisAnimalesAdapter adapter;
    AnimalesDao dao;
    AnimalesDB db;
    Animal animalPel;
    ShapeableImageView imagenAnimal, ivPerfilPel;
    Button btnAniadirMascotaPel;
    CardView cvUbicacionTiempoReal, cvTusCitas, cvChat;
    TextView tvNombrePel;
    Intent i;
    ArrayList<Animal> listaAnimalesPel;
    StorageReference mStorage;
    FirebaseDatabase fbr;
    DatabaseReference dbr;
    ArrayList<TusCitas> listaCitas;
    StorageReference mStorageP;
    UriDao daoU;
    UriDB dbU;

    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey()));
                        rv.setAdapter(adapter);
                        adapter.setListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listaAnimalesPel = (ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey());
                                i = new Intent(PeluqueriaActivity.this, DatosAnimalActivity.class);
                                animalPel = listaAnimalesPel.get(rv.getChildAdapterPosition(v));
                                i.putExtra(CLAVE_ANIMAL, animalPel.getKey());
                                arl.launch(i);
                            }
                        });
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peluqueria);
        mStorage = FirebaseStorage.getInstance().getReference("fotos/" + ((MiApplication) getApplicationContext()).getKey() + "/");
        listaCitas = new ArrayList<>();
        mStorageP = FirebaseStorage.getInstance().getReference();
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) +
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS))
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, 1001);
        }

        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        dbU = UriDB.getDatabase(this);
        daoU = dbU.uriDao();

        fbr = FirebaseDatabase.getInstance();
        dbr = fbr.getReference();

        /*dbr.child("usuarios/" + ((MiApplication) getApplicationContext()).getKey() + "/reservasCoche").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot sp : task.getResult().getChildren()) {
                    listaCitas.add(new TusCitas(sp.getValue(TusCitas.class).getUrlI(),
                            sp.getValue(TusCitas.class).getKey(),
                            sp.getValue(TusCitas.class).getKeyE(),
                            sp.getValue(TusCitas.class).getKeyEC(),
                            sp.getValue(TusCitas.class).getNomAnimal(),
                            sp.getValue(TusCitas.class).getCitaFecha(),
                            sp.getValue(TusCitas.class).getCitaHora()));
                }
            }
        });*/

        ivPerfilPel = findViewById(R.id.ivPerfilPel);



        if (daoU.sacarUri(((MiApplication) getApplicationContext()).getKey()) != null) {
            com.dam.peluqueriacanina.entity.Uri uri = daoU.sacarUri(((MiApplication) getApplicationContext()).getKey());
            StorageReference filePath = mStorage.child("fotosPerfil/" + ((MiApplication) getApplicationContext()).getKey() + "/fotoPerfil.jpg");
            mStorageP.child("fotosPerfil/" + ((MiApplication) getApplicationContext()).getKey() + "/fotoPerfil.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).resize(150, 150).centerCrop().into(ivPerfilPel);
                }
            });
        }


        imagenAnimal = findViewById(R.id.siAnimal);
        btnAniadirMascotaPel = findViewById(R.id.btnAniadirMascotaPel);
        rv = findViewById(R.id.rvReservarPel);
        cvUbicacionTiempoReal = findViewById(R.id.cvUbicacionTiempoReal);
        cvTusCitas = findViewById(R.id.cvVerTusCitas);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);
        cvChat = findViewById(R.id.cvChat);
        tvNombrePel = findViewById(R.id.tvNombrePel);

        tvNombrePel.setText(((MiApplication) getApplicationContext()).getNombre() + " " + ((MiApplication) getApplicationContext()).getApellidos());

        if (!dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey()).isEmpty()) {
            adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey()));
            cvUbicacionTiempoReal.setEnabled(true);
            adapter.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaAnimalesPel = (ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey());
                    i = new Intent(PeluqueriaActivity.this, DatosAnimalActivity.class);
                    animalPel = listaAnimalesPel.get(rv.getChildAdapterPosition(v));
                    i.putExtra(CLAVE_ANIMAL, animalPel.getKey());
                    arl.launch(i);
                }
            });

        } else {
            cvUbicacionTiempoReal.setEnabled(false);
        }

        rv.setAdapter(adapter);

        btnAniadirMascotaPel.setOnClickListener(this);
        cvUbicacionTiempoReal.setOnClickListener(this);
        cvTusCitas.setOnClickListener(this);
        cvChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAniadirMascotaPel)) {
            i = new Intent(this, RegistrarAnimal.class);
            arl.launch(i);
        } else if (v.equals(cvUbicacionTiempoReal)) {
            // if (((dao.sacarTodo()).isEmpty())) {
            // Toast.makeText(this,R.string.error_no_hay_animales,Toast.LENGTH_SHORT).show();
            //  } else {
            i = new Intent(this, UbicacionTiempoRealActivity.class);
            startActivity(i);
            //}

        } else if (v.equals(cvTusCitas)) {
            i = new Intent(this, VerTusCitasActivity.class);
            startActivity(i);
        } else if (v.equals(cvChat)) {
            if (!listaCitas.isEmpty()) {
                i = new Intent(this, ChatActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, R.string.error_no_hay_citas, Toast.LENGTH_SHORT).show();
            }

        }
    }


}