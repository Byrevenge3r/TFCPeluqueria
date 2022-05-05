package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.dam.peluqueriacanina.model.Mapa;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.dam.peluqueriacanina.databinding.ActivityUbicacionTiempoRealBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UbicacionTiempoRealActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    Marker marker;
    Double latitud;
    Double longitud;
    int height = 130;
    int width = 130;
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fdb = FirebaseDatabase.getInstance();
        dbr = fdb.getReference();
        setContentView(R.layout.activity_ubicacion_tiempo_real);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        bitmapdraw =(BitmapDrawable)getResources().getDrawable(R.drawable.furgo_canina);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);


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

                if (marker != null) {
                    marker.remove();
                }

                marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                        .position(new LatLng(latitud, longitud)));
                    countDownTimer();
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

    private void countDownTimer() {
        new CountDownTimer(  2000, 1000) {
            public void onTick (long millisuntilFinished) {
                onMapReady(mMap);
            }

            public void onFinish () {

            }

        }.start ();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4165,-3.70256), 11));
        marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap((smallMarker)))
                .position(new LatLng(40.4165, -3.70256)));
        coorMapa();
    }
}