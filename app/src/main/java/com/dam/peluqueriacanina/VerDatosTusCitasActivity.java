package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dam.peluqueriacanina.model.Mapa;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerDatosTusCitasActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Marker markerMap;
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;
    int altura = 130;
    int anchura = 130;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    Double latitud;
    Double longitud;
    float distancia;
    Location locOrigen;
    Location locDestino;
    TextView tvMostrarTiempo;
    float tiempoD;
    LatLng adress;
    String hora;
    String[] horaA;
    LocalDateTime now;
    int horaR;
    int horaB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_tus_citas);
        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference("coche");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fcvUbicacionConductorDetalles);

        hora = getIntent().getStringExtra("hora");

        horaA = hora.split(":");

        tvMostrarTiempo = findViewById(R.id.tvTiempo);
        tvMostrarTiempo.setVisibility(View.INVISIBLE);
        bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.furgo_canina);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, anchura, altura, false);

        locOrigen = new Location("ubicacionOrigen");
        locDestino = new Location("ubicacionDestino");

        mapFragment.getMapAsync(this);

    }

    private void coorMapa() {
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mapa mp = snapshot.getValue(Mapa.class);
                if (mp.getLatitud() != 0.0 && mp.getLongitud() != 0.0) {
                    latitud = mp.getLatitud();
                    longitud = mp.getLongitud();

                    if (markerMap != null) {
                        markerMap.remove();
                    }

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 10));
                    markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                            .position(new LatLng(latitud, longitud)));

                    locOrigen.setLatitude(latitud);
                    locOrigen.setLongitude(longitud);

                    adress = getLocationFromAddress(VerDatosTusCitasActivity.this, ((MiApplication) getApplicationContext()).getDireccion());
                    locDestino.setLatitude(adress.latitude);
                    locDestino.setLongitude(adress.longitude);

                    distancia = locOrigen.distanceTo(locDestino);

                    tiempoD = ((distancia / 1000) / 23) * 60;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        now = LocalDateTime.now();
                        //Esta raro esto mirar
                        horaR = Integer.parseInt("18");
                        horaB = horaR * 60;

                        tiempoD += horaB;

                    }
                    tvMostrarTiempo.setVisibility(View.VISIBLE);
                    tvMostrarTiempo.setText(getBaseContext().getString(R.string.tv_tiempo_estimado_horas, formatearMinutosAHoraMinuto((int) tiempoD)));

                }

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
    }

    public String formatearMinutosAHoraMinuto(int minutos) {
        String formato = "%02d:%02d";
        long horasReales = TimeUnit.MINUTES.toHours(minutos);
        long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
        return String.format(formato, horasReales, minutosReales);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        coorMapa();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;

    }
}