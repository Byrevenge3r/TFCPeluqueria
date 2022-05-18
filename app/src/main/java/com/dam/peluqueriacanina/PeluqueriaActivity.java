package com.dam.peluqueriacanina;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.db.AnimalesDB;
import com.dam.peluqueriacanina.db.TusCitasDB;
import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.utils.MisAnimalesAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class PeluqueriaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CLAVE_ANIMAL = "ANIMAL_PEL";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    RecyclerView rv;
    LinearLayoutManager llm;
    MisAnimalesAdapter adapter;
    AnimalesDao dao;
    AnimalesDB db;
    TusCitasDao daoTusCitas;
    TusCitasDB dbTusCitas;
    Animal animalPel;
    ShapeableImageView imagenAnimal;
    Button btnAniadirMascotaPel;
    CardView cvUbicacionTiempoReal, cvTusCitas;
    Intent i;
    ArrayList<Animal> listaAnimalesPel;
    TusCitas tusCitas;
    ArrayList<TusCitas> tusCitasLista;

    SmsListener smsListener = new SmsListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if (msg.contains("confirmado") && tusCitas.getKey() != null) {
                daoTusCitas.insert(tusCitas);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(smsListener, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsListener);
    }


    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarTodo());
                        rv.setAdapter(adapter);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peluqueria);

        tusCitasLista = new ArrayList<>();
        tusCitas = new TusCitas();
        if ((getIntent().getParcelableExtra("cita")) != null) {
            tusCitas = getIntent().getParcelableExtra("cita");
        }
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) +
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS))
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, 1001);
        }
        db = AnimalesDB.getDatabase(this);
        dao = db.animalDao();

        dbTusCitas = TusCitasDB.getDatabase(this);
        daoTusCitas = dbTusCitas.citaDao();

        imagenAnimal = findViewById(R.id.siAnimal);
        btnAniadirMascotaPel = findViewById(R.id.btnAniadirMascotaPel);
        rv = findViewById(R.id.rvReservarPel);
        cvUbicacionTiempoReal = findViewById(R.id.cvUbicacionTiempoReal);
        cvTusCitas = findViewById(R.id.cvVerTusCitas);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);



        if (!dao.sacarTodo().isEmpty()) {
            adapter = new MisAnimalesAdapter((ArrayList<Animal>) dao.sacarTodo());
            if ((dao.sacarTodo()).isEmpty()) {
                cvUbicacionTiempoReal.setEnabled(false);
            }
            adapter.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaAnimalesPel = (ArrayList<Animal>) dao.sacarTodo();
                    i = new Intent(PeluqueriaActivity.this, DatosAnimalActivity.class);
                    animalPel = listaAnimalesPel.get(rv.getChildAdapterPosition(v));
                    i.putExtra(CLAVE_ANIMAL, animalPel.getRuta());
                    arl.launch(i);
                }
            });

        }

        rv.setAdapter(adapter);

        btnAniadirMascotaPel.setOnClickListener(this);
        cvUbicacionTiempoReal.setOnClickListener(this);
        cvTusCitas.setOnClickListener(this);
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
        }
    }


}