package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.dam.peluqueriacanina.model.Mapa;
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

public class VerDatosTusCitasActivity extends AppCompatActivity  implements OnMapReadyCallback{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_tus_citas);
        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference("coche");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fcvUbicacionConductorDetalles);

        bitmapdraw =(BitmapDrawable)getResources().getDrawable(R.drawable.furgo_canina);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, anchura, altura, false);


        mapFragment.getMapAsync( this);

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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud,longitud), 10));
                    mMap.animateCamera();
                    markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                            .position(new LatLng(latitud, longitud)));

                   /* markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                            .position(new LatLng(latitud, longitud)));*/


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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        coorMapa();
    }
}