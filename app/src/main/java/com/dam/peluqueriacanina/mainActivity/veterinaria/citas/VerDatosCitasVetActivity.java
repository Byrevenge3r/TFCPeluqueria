package com.dam.peluqueriacanina.mainActivity.veterinaria.citas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.NombreDirVet;
import com.dam.peluqueriacanina.utils.UtilsJSon;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VerDatosCitasVetActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNombreVet,tvHoraVet,tvFechaVet;
    Button btnGoogleMaps;
    String nom;
    String hora;
    String fecha;
    Location locDestino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_ver_datos_citas_vet);

        tvNombreVet = findViewById(R.id.tvNombreVet);
        tvHoraVet = findViewById(R.id.tvHoraVet);
        tvFechaVet = findViewById(R.id.tvFechaVet);


        btnGoogleMaps = findViewById(R.id.btnGoogleMaps);

        fecha = getIntent().getStringExtra("fecha");
        nom = getIntent().getStringExtra("nom");
        hora = getIntent().getStringExtra("hora");

        btnGoogleMaps.setOnClickListener(this);

        tvNombreVet.setText(getBaseContext().getString(R.string.tv_nombre_vet, nom));
        tvFechaVet.setText(getBaseContext().getString(R.string.tv_fecha_vet, fecha));
        tvHoraVet.setText(getBaseContext().getString(R.string.tv_hora_vet, hora));

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:" + locDestino.getLatitude() + "," + locDestino.getLongitude() + "?z=16&q=" + locDestino.getLatitude() + "," + locDestino.getLongitude()
                + "(veterinaria)"));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }



}