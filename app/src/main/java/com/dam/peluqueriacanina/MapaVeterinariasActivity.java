package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.dam.peluqueriacanina.dao.CoordenadasDao;
import com.dam.peluqueriacanina.db.CoordenadasDB;
import com.dam.peluqueriacanina.entity.Coordenadas;
import com.dam.peluqueriacanina.model.NombreDirVet;


import com.dam.peluqueriacanina.utils.UtilsJSon;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapaVeterinariasActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ArrayList<NombreDirVet> listaVet;
    LatLng adress;
    ArrayList<LatLng> listaCoor;
    ClusterManager<MyItem> clusterManager;
    ArrayList<Marker> listaMarcadores;
    ArrayList<LatLng> listaMarcadoresAux;
    ArrayList<Coordenadas> listaCoorD;
    CoordenadasDao dao;
    CoordenadasDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_veterinarias);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapVet);
        mapFragment.getMapAsync(this);

        db = CoordenadasDB.getDatabase(this);
        dao = db.coordenadasDao();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        listaVet = new ArrayList<>();
        listaCoor = new ArrayList<>();
        listaMarcadores = new ArrayList<>();
        listaMarcadoresAux = new ArrayList<>();
        listaCoorD = new ArrayList<>();



    }

    private void aniadirMarcadores2(ArrayList<Coordenadas> listaCoorD) {
        for (int i = 0; i < listaCoorD.size(); i++) {

            clusterManager.addItem(new MyItem(listaCoorD.get(i).getLat(),listaCoorD.get(i).getLon(),listaCoorD.get(i).getNombre()));

            /*mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker())
                    .position(new LatLng(listaCoorD.get(i).getLat(),listaCoorD.get(i).getLon())));*/
        }

        clusterManager.cluster();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4165,-3.70256), 15));
        clusterManager = new ClusterManager<>(this, mMap);

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        if (dao.sacarTodo().isEmpty()) {
            try {
                String jsonFileContent = UtilsJSon.readFile(getApplicationContext(),"veterinarias.json");
                JSONArray jsonArray = new JSONArray(jsonFileContent);
                for (int i = 0; i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listaVet.add(new NombreDirVet(jsonObject.getString("clínica"),
                            jsonObject.getString("dirección"),
                            jsonObject.getString("municipio"),
                            jsonObject.getString("cp")));
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            aniadirMarcadores(listaVet);
        } else {
            listaCoorD = (ArrayList<Coordenadas>) dao.sacarTodo();
            aniadirMarcadores2(listaCoorD);
        }

    }

    private void aniadirMarcadores(ArrayList<NombreDirVet> listaVet) {
        for (int i = 0; i < listaVet.size(); i++) {
                adress = getLocationFromAddress(listaVet.get(i));
                if (adress!=null) {
                    listaCoorD.add(new Coordenadas(listaVet.get(i).getClínica(),adress.latitude,adress.longitude));
                }

        }

        dao.insert(listaCoorD);

    }

    public LatLng getLocationFromAddress(NombreDirVet strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;
        double lat;
        double lon;

        try {
            address = coder.getFromLocationName(strAddress.getDirección(), 5);
            if (address == null) {
                return null;
            } else {
                lat = address.get(0).getLatitude();
                lon = address.get(0).getLongitude();

                p1 = new LatLng(lat, lon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;

    }

    public class MyItem implements ClusterItem {
        private final LatLng position;
        private final String title;

        public MyItem(double lat, double lng, String title) {
            position = new LatLng(lat, lng);
            this.title = title;
        }

        @Override
        public LatLng getPosition() {
            return position;
        }

    }

}