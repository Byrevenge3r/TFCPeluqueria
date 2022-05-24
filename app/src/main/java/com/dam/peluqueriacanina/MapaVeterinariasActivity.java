package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dam.peluqueriacanina.model.NombreDirVet;


import com.dam.peluqueriacanina.utils.MiApplication;
import com.dam.peluqueriacanina.utils.UtilsJSon;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapaVeterinariasActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ArrayList<NombreDirVet> listaVet;
    Marker markerMap;
    LatLng adress;
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;
    int altura = 130;
    int anchura = 130;
    ArrayList<LatLng> listaCoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_veterinarias);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapVet);
        mapFragment.getMapAsync(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        listaVet = new ArrayList<>();
        listaCoor = new ArrayList<>();

        try {
            String jsonFileContent = UtilsJSon.readFile(getApplicationContext(),"veterinarias.json");
            JSONArray jsonArray = new JSONArray(jsonFileContent);
            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listaVet.add(new NombreDirVet(jsonObject.getString("clínica"),jsonObject.getString("dirección"),
                        jsonObject.getString("municipio"),jsonObject.getString("cp")));

            }
            aniadirMarcadores(listaVet);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        adress = getLocationFromAddress(this, ((MiApplication) getApplicationContext()).getDireccion());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(adress, 10));
        //mClusterManager = new ClusterManager<>(this,mMap);
    }

    private void aniadirMarcadores(List<NombreDirVet> listaVet) {
        for (int i = 0; i < listaVet.size(); i++) {
            adress = getLocationFromAddress(this, this.listaVet.get(i).getDirección());
            if (adress!=null) {
               // listaCoor.add(adress);
                markerMap = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker())
                        .position(adress));
            }
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
            } else {
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;

    }
    }