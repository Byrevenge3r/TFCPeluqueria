package com.dam.peluqueriacanina.mainActivity.veterinaria.citas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.NombreDirVet;
import com.dam.peluqueriacanina.utils.UtilsJSon;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VerDatosCitasVetActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTiempo;
    Button btnGoogleMaps;
    NombreDirVet clinica;
    String nom;
    String hora;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    Location location;
    Location locDestino;
    float distancia;
    float tiempoD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_ver_datos_citas_vet);

        tvTiempo = findViewById(R.id.tvMostrarInfo);
        btnGoogleMaps = findViewById(R.id.btnGoogleMaps);

        nom = getIntent().getStringExtra("nombre");
        hora = getIntent().getStringExtra("hora");
        location = new Location("ubicacionOrigen");
        locDestino = new Location("ubicacionDestino");
        btnGoogleMaps.setOnClickListener(this);

        cargarMarcJson();
        buildLocationCallBack();

        distancia = location.distanceTo(locDestino);

        tiempoD = ((distancia / 1000) / 23) * 60;

        tvTiempo.setText(getBaseContext().getString(R.string.tv_tiempo_estimado_horas,String.valueOf(tiempoD)));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:"+ locDestino.getLatitude()+","+locDestino.getLongitude()+"?z=16&q="+ locDestino.getLatitude()+","+locDestino.getLongitude()
                +"(veterinaria)"));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    private void cargarMarcJson() {
        try {
            String jsonFileContent = UtilsJSon.readFile(getApplicationContext(),"veterinarias.json");
            JSONArray jsonArray = new JSONArray(jsonFileContent);
            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                clinica = new NombreDirVet(jsonObject.getString("clinica"),
                        jsonObject.getString("lat"),
                        jsonObject.getString("lon"));

                if (clinica.getClinica().trim().equals(nom)) {
                    i = jsonArray.length();
                    locDestino.setLatitude(Double.parseDouble(clinica.getLat()));
                    locDestino.setLongitude(Double.parseDouble(clinica.getLon()));
                }
            }



        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                location = locationResult.getLocations().get(locationResult.getLocations().size() - 1);
                            }
        };
    }

}