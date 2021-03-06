package com.dam.peluqueriacanina.mainActivity.peluqueria.citas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
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
        dbr = fdb.getReference("coche/coor");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fcvUbicacionConductorDetalles);

        hora = getIntent().getStringExtra("hora");

        horaA = hora.split(":");

        tvMostrarTiempo = findViewById(R.id.tvTiempo);
        bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.furgo_canina);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, anchura, altura, false);

        locOrigen = new Location("ubicacionOrigen");
        locDestino = new Location("ubicacionDestino");
        adress = getLocationFromAddress(VerDatosTusCitasActivity.this, ((MiApplication) getApplicationContext()).getDireccion());
        tvMostrarTiempo.setVisibility(View.VISIBLE);
        tvMostrarTiempo.setText(getBaseContext().getString(R.string.tv_mensaje_no_llega_aun_tiempo));

        mapFragment.getMapAsync(this);

    }

    private void coorMapa() {
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Mapa mp = snapshot.getValue(Mapa.class);
                if (mp.getLatitud() != 0.0 && mp.getLongitud() != 0.0) {
                    latitud = mp.getLatitud();
                    longitud = mp.getLongitud();

                    if (markerMap != null) {
                        markerMap.remove();
                    }

                    locOrigen.setLatitude(latitud);
                    locOrigen.setLongitude(longitud);

                    locDestino.setLatitude(adress.latitude);
                    locDestino.setLongitude(adress.longitude);

                    distancia = locOrigen.distanceTo(locDestino);

                    tiempoD = ((distancia / 1000) / 23) * 60;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        now = LocalDateTime.now();
                        horaR = Integer.parseInt(horaA[0]);
                        horaB = horaR * 60;
                    }

                    if (tiempoD < 30) {
                        horaB -= tiempoD;
                        tvMostrarTiempo.setVisibility(View.VISIBLE);
                        tvMostrarTiempo.setText(getBaseContext().getString(R.string.tv_mostrar_info, formatearMinutosAHoraMinuto(horaB)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 10));
                        markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                                .position(new LatLng(latitud, longitud)));

                    }
                }
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