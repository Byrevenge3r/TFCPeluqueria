package com.dam.peluqueriacanina.mainActivity.veterinaria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.mainActivity.veterinaria.fragmentosVet.CitasVet;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapaVeterinariasActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ArrayList<NombreDirVet> listaVet;
    ClusterManager<MyItem> clusterManager;
    int altura = 120;
    int anchura = 170;
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;
    Bundle bundle;
    CitasVet citasVet;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_veterinarias);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapVet);
        mapFragment.getMapAsync(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        listaVet = new ArrayList<>();
        bundle = new Bundle();
        citasVet = new CitasVet();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ubicacionUser = getLocationFromAddress(this,((MiApplication)getApplicationContext()).getDireccion());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionUser,15));
        clusterManager = new ClusterManager<>(this, mMap);
        CustomClusterRenderers renderers = new CustomClusterRenderers(this, mMap, clusterManager);
        clusterManager.setRenderer(renderers);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        mMap.setOnInfoWindowClickListener(clusterManager.getMarkerManager());
        mMap.setOnCameraIdleListener(clusterManager);

        clusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<MyItem>() {
            @Override
            public void onClusterItemInfoWindowClick(MyItem myItem) {
                bundle.putString("keyB", ((MiApplication) getApplicationContext()).getKey());
                bundle.putString("nom", myItem.getTitle());
                getSupportFragmentManager().setFragmentResult("key", bundle);
                citasVet.show(getSupportFragmentManager(), "Citas");
            }
        });

        CargarMarcJson();
    }

    private void CargarMarcJson() {
        try {
            String jsonFileContent = UtilsJSon.readFile(getApplicationContext(), "veterinarias.json");
            JSONArray jsonArray = new JSONArray(jsonFileContent);
            for (int i = 0; i < jsonArray.length(); i++) {
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
            clusterManager.addItem(new MyItem(Double.parseDouble(listaVet.get(i).getLat()), Double.parseDouble(listaVet.get(i).getLon()), listaVet.get(i).getClinica()));
        }
        clusterManager.cluster();
    }


    public class CustomClusterRenderers extends DefaultClusterRenderer<MyItem> {
        private Context context;
        private GoogleMap map;
        private ClusterManager<MyItem> clusterManager;
        private IconGenerator mClusterIconGenerator;

        public CustomClusterRenderers(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
            this.context = context;
            this.map = map;
            this.clusterManager = clusterManager;

            mClusterIconGenerator = new IconGenerator(context.getApplicationContext());
        }


        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.veterinaria_icono_mapa);
            b = bitmapdraw.getBitmap();
            smallMarker = Bitmap.createScaledBitmap(b, anchura, altura, false);

            BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromBitmap(smallMarker);
            markerOptions.icon(markerDescriptor).snippet(item.getTitle()).title(item.getTitle());
        }
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

        public String getTitle() {
            return title;
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