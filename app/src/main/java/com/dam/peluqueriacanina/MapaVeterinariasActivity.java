package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;

import com.dam.peluqueriacanina.model.NombreDirVet;


import com.dam.peluqueriacanina.utils.UtilsJSon;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MapaVeterinariasActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ArrayList<NombreDirVet> listaVet;
    ClusterManager<MyItem> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_veterinarias);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapVet);
        mapFragment.getMapAsync(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        listaVet = new ArrayList<>();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4165,-3.70256), 15));
        clusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        CargarMarcJson();

    }

    private void CargarMarcJson() {
            try {
                String jsonFileContent = UtilsJSon.readFile(getApplicationContext(),"veterinarias.json");
                JSONArray jsonArray = new JSONArray(jsonFileContent);
                for (int i = 0; i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listaVet.add(new NombreDirVet(jsonObject.getString("clinica"),
                            jsonObject.getString("lat"),
                            jsonObject.getString("lon")));
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        aniadirMarcadores(listaVet);

    }

    private void aniadirMarcadores(ArrayList<NombreDirVet> listaVet) {
        for (int i = 0; i < listaVet.size(); i++) {
            clusterManager.addItem(new MyItem(Double.parseDouble(listaVet.get(i).getLat()),Double.parseDouble(listaVet.get(i).getLon()),listaVet.get(i).getClinica()));
        }
        clusterManager.cluster();
    }


   /* public class  CustomClusterRenderers extends DefaultClusterRenderer<MyItem> {
       private Context context;
    }*/

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