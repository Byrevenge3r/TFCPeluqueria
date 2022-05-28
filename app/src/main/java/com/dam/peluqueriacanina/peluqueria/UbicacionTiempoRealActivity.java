package com.dam.peluqueriacanina.peluqueria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.comunicacion.Comunicacion;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.dam.peluqueriacanina.fragmentosPel.CitasPel;
import com.dam.peluqueriacanina.model.Mapa;
import com.dam.peluqueriacanina.peluqueria.PeluqueriaActivity;
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

public class UbicacionTiempoRealActivity
        extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        Comunicacion {

    GoogleMap mMap;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    Marker markerMap;
    Double latitud;
    Double longitud;
    int altura = 130;
    int anchura = 130;
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;
    CitasPel citasPel;
    TusCitas tusCitas;
    Intent i;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference("coche");

        setContentView(R.layout.activity_ubicacion_tiempo_real);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bitmapdraw =(BitmapDrawable)getResources().getDrawable(R.drawable.furgo_canina);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, anchura, altura, false);

        citasPel = new CitasPel();

        tusCitas = new TusCitas();

        bundle = new Bundle();
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
                    markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                        .position(new LatLng(latitud, longitud)));
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4165,-3.70256), 11));
        markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                .position(new LatLng(40.4165, -3.70256)));
        mMap.setOnMarkerClickListener(this);
        coorMapa();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if (marker.equals(markerMap)) {
            bundle.putString("keyB",((MiApplication) getApplicationContext()).getKey());
            getSupportFragmentManager().setFragmentResult("key", bundle);
            citasPel.show(getSupportFragmentManager(),"Citas");
        }
        return false;
    }

    @Override
    public void info(TusCitas cita) {
        tusCitas = cita;
        i = new Intent(this, PeluqueriaActivity.class);
        i.putExtra("cita", tusCitas);
        startActivity(i);
    }
}