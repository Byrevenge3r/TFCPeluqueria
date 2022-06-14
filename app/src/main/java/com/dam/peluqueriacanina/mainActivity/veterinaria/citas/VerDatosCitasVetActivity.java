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

    TextView tvNombreVet,tvHoraVet,tvFechaVet,tvDistanciaTiempoVet;
    Button btnGoogleMaps;
    NombreDirVet clinica;
    String nom;
    String hora;
    String fecha;
    LocationCallback locationCallback;
    Location location;
    Location locDestino;
    float distancia;
    float tiempoD;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_ver_datos_citas_vet);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 3);

        }

        tvNombreVet = findViewById(R.id.tvNombreVet);
        tvHoraVet = findViewById(R.id.tvHoraVet);
        tvFechaVet = findViewById(R.id.tvFechaVet);
        tvDistanciaTiempoVet = findViewById(R.id.tvDistanciaTiempoVet);

        btnGoogleMaps = findViewById(R.id.btnGoogleMaps);

        fecha = getIntent().getStringExtra("fecha");
        nom = getIntent().getStringExtra("nom");
        hora = getIntent().getStringExtra("hora");

        location = new Location("ubicacionOrigen");
        locDestino = new Location("ubicacionDestino");
        btnGoogleMaps.setOnClickListener(this);

        cargarMarcJson();
        initFused();

        tvNombreVet.setText(getBaseContext().getString(R.string.tv_nombre_vet, nom));
        tvFechaVet.setText(getBaseContext().getString(R.string.tv_fecha_vet, fecha));
        tvHoraVet.setText(getBaseContext().getString(R.string.tv_hora_vet, hora));


    }

    @SuppressLint("MissingPermission")
    private void initFused() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        buildLocationRequest();
        buildLocationCallBack();

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }
    @SuppressLint("MissingPermission")
    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                location = locationResult.getLocations().get(locationResult.getLocations().size() - 1);
                distancia = location.distanceTo(locDestino);
                tiempoD = ((distancia / 1000) / 23) * 60;
                tvDistanciaTiempoVet.setText(getBaseContext().getString(R.string.tv_tiempo_estimado_horas,  tiempoD));
            }
        };
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:" + locDestino.getLatitude() + "," + locDestino.getLongitude() + "?z=16&q=" + locDestino.getLatitude() + "," + locDestino.getLongitude()
                + "(veterinaria)"));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    private void cargarMarcJson() {
        try {
            String jsonFileContent = UtilsJSon.readFile(getApplicationContext(), "veterinarias.json");
            JSONArray jsonArray = new JSONArray(jsonFileContent);
            for (int i = 0; i < jsonArray.length(); i++) {
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


    private void buildLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(100);
    }

}