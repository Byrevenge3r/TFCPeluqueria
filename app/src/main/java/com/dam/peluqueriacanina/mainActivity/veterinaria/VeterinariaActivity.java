package com.dam.peluqueriacanina.mainActivity.veterinaria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.UriDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.UriDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.mainActivity.peluqueria.DatosAnimalActivity;
import com.dam.peluqueriacanina.mainActivity.peluqueria.RegistrarAnimal;
import com.dam.peluqueriacanina.mainActivity.veterinaria.citas.VerCitasVetActivity;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VeterinariaActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String CLAVE_ANIMAL = "ANIMAL_VET";

    RecyclerView rv;
    LinearLayoutManager llm;
    MisAnimalesAdapter adapter;
    AnimalesDao dao;
    AnimalesDB db;
    Animal animalVet;
    ShapeableImageView imagenAnimal, ivPerfilVet;
    Button btnAniadirMascotaVet;
    ArrayList<Animal> listaAnimalesVet;
    Intent i;
    CardView cvVeterinaria, cvTusCitasVet;
    TextView tvNombreVet;
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
                        listenerRv();
                        rv.setAdapter(adapter);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinaria);
        mStorageP = FirebaseStorage.getInstance().getReference();
        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        dbU = UriDB.getDatabase(this);
        daoU = dbU.uriDao();

        ivPerfilVet = findViewById(R.id.ivPerfilVet);
        tvNombreVet = findViewById(R.id.tvNombreVet);
        tvNombreVet.setText(((MiApplication) getApplicationContext()).getNombre() + " " + ((MiApplication) getApplicationContext()).getApellidos());

        if (daoU.sacarUri(((MiApplication) getApplicationContext()).getKey()) != null) {
            com.dam.peluqueriacanina.entity.Uri uri = daoU.sacarUri(((MiApplication) getApplicationContext()).getKey());
            mStorageP.child("fotosPerfil/" + ((MiApplication) getApplicationContext()).getKey() + "/fotoPerfil.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).resize(150, 150).centerCrop().into(ivPerfilVet);
                }
            });
        }
        imagenAnimal = findViewById(R.id.siAnimal);
        btnAniadirMascotaVet = findViewById(R.id.btnAniadirMascotaVet);
        rv = findViewById(R.id.rvReservarVet);
        cvVeterinaria = findViewById(R.id.cvVeterinaria);
        cvTusCitasVet = findViewById(R.id.cvTusCitasVet);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);

        if (!dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey()).isEmpty()) {
            adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey()));
            listenerRv();
        }

        rv.setAdapter(adapter);

        btnAniadirMascotaVet.setOnClickListener(this);
        cvVeterinaria.setOnClickListener(this);
        cvTusCitasVet.setOnClickListener(this);
    }

    private void listenerRv() {
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaAnimalesVet = (ArrayList<Animal>) dao.sacarAnimalKey(((MiApplication) getApplicationContext()).getKey());
                i = new Intent(VeterinariaActivity.this, DatosAnimalActivity.class);
                animalVet = listaAnimalesVet.get(rv.getChildAdapterPosition(v));
                i.putExtra(CLAVE_ANIMAL, animalVet.getKey());
                arl.launch(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAniadirMascotaVet)) {
            i = new Intent(this, RegistrarAnimal.class);
            arl.launch(i);
        } else if (v.equals(cvVeterinaria)) {
            i = new Intent(this, MapaVeterinariasActivity.class);
            startActivity(i);
        } else if (v.equals(cvTusCitasVet)) {
            i = new Intent(this, VerCitasVetActivity.class);
            startActivity(i);
        }
    }
}