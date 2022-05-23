package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dam.peluqueriacanina.model.NombreDirVet;
import com.dam.peluqueriacanina.retrofit.ApiRestService;
import com.dam.peluqueriacanina.retrofit.RetrofitClient;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapaVeterinariasActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Retrofit r;
    ApiRestService ars;
    ArrayList<NombreDirVet> listaVet;
    Marker markerMap;
    LatLng adress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_veterinarias);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapVet);
        mapFragment.getMapAsync(this);

        listaVet = new ArrayList<>();

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        r = RetrofitClient.getClient(ApiRestService.BASE_URL);
        ars = r.create(ApiRestService.class);
        Call<ArrayList<NombreDirVet>> call = ars.obtenerVeterinarias();
        call.enqueue(new Callback<ArrayList<NombreDirVet>>() {
            @Override
            public void onResponse(Call<ArrayList<NombreDirVet>> call, Response<ArrayList<NombreDirVet>> response) {
                if (!response.isSuccessful()) {
                    Log.i("errorRetrofit","error" + response.code());
                } else {
                    listaVet = response.body();
                    Log.i("tamLista","tam lista "+ listaVet.size());

                }

            }
            @Override
            public void onFailure(Call<ArrayList<NombreDirVet>> call, Throwable t) {
                Log.e("errorRetrofit" ,"Failure" + t.toString());
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4165,-3.70256), 11));
        for (int i = 0;i < listaVet.size();i++) {
            adress = getLocationFromAddress(this,listaVet.get(i).getDirección());
            markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker())
                            .position(adress));
        }
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