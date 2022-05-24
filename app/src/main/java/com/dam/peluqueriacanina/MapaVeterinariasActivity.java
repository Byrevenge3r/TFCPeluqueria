package com.dam.peluqueriacanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.dam.peluqueriacanina.model.NombreDirVet;


import com.dam.peluqueriacanina.utils.UtilsJSon;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapaVeterinariasActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ArrayList<String> listaVet;
    LatLng adress;
    Marker marker;
    ArrayList<LatLng> listaCoor;
    ClusterManager<MyItem> clusterManager;
    ArrayList<Marker> listaMarcadores;
    ArrayList<LatLng> listaMarcadoresAux;
    LatLngBounds latLngBounds;
    boolean primeraPasada = true;
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
        listaMarcadores = new ArrayList<>();
        listaMarcadoresAux = new ArrayList<>();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4165,-3.70256), 20));
        clusterManager = new ClusterManager<>(this, mMap);
        ZoomBasedRenderer renderer = new ZoomBasedRenderer(this, mMap, clusterManager);
        clusterManager.setRenderer(renderer);

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                aniadirMarcadores(listaVet);
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
            }
        });


        try {
            String jsonFileContent = UtilsJSon.readFile(getApplicationContext(),"veterinarias.json");
            JSONArray jsonArray = new JSONArray(jsonFileContent);
            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listaVet.add(jsonObject.getString("dirección"));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private class ZoomBasedRenderer extends DefaultClusterRenderer<MyItem> implements GoogleMap.OnCameraIdleListener {
        private Float zoom = 15f;
        private Float oldZoom;
        private static final float ZOOM_THRESHOLD = 12f;

        public ZoomBasedRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        public void onCameraIdle() {
            // Remember the previous zoom level, capture the new zoom level.
            oldZoom = zoom;
            zoom = mMap.getCameraPosition().zoom;
        }


        @Override
        protected boolean shouldRenderAsCluster(@NonNull Cluster<MyItem> cluster) {
            // Show as cluster when zoom is less than the threshold, otherwise show as marker
            boolean isInBounds = latLngBounds.contains(cluster.getPosition());
            if (isInBounds) {
                return cluster.getSize() > 4 && mMap.getCameraPosition().zoom < 17;
            } else {
                return true;

            }
        }


        protected boolean shouldRender(@NonNull Set<? extends Cluster<MyItem>> oldClusters, @NonNull Set<? extends Cluster<MyItem>> newClusters) {
            if (crossedZoomThreshold(oldZoom, zoom)) {
                // Render when the zoom level crosses the threshold, even if the clusters don't change
                return true;
            } else {
                // If clusters didn't change, skip render for optimization using default super implementation
                return shouldRender(oldClusters, newClusters);
            }
        }

        private boolean crossedZoomThreshold(Float oldZoom, Float newZoom) {
            if (oldZoom == null || newZoom == null) {
                return true;
            }
            return (oldZoom < ZOOM_THRESHOLD && newZoom > ZOOM_THRESHOLD) ||
                    (oldZoom > ZOOM_THRESHOLD && newZoom < ZOOM_THRESHOLD);
        }
    }


    private void aniadirMarcadores(List<String> listaVet) {
        for (int i = 0; i < listaVet.size(); i++) {
            /*if (!primeraPasada) {
                boolean isInBounds = latLngBounds.contains(listaCoor.get(i));
                if(isInBounds){
                    clusterManager.addItem(new MyItem(adress.latitude, adress.longitude,listaVet.get(i).getDirección()));
                } else {
                    clusterManager.removeItem(new MyItem(adress.latitude, adress.longitude,listaVet.get(i).getDirección()));
                }
                clusterManager.cluster();
            } else {*/
                adress = getLocationFromAddress(this.listaVet.get(i));
                if (adress!=null) {
                    listaCoor.add(adress);
                }
                primeraPasada = false;
               /*mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker())
                        .position(adress));*/
            //}
        }

    }

    private Boolean isInBounds(LatLng position, LatLngBounds latLngBounds) {
        return (latLngBounds == null ? mMap.getProjection().getVisibleRegion().latLngBounds : latLngBounds).contains(position);
    }


    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;
        double lat;
        double lon;

        try {
            address = coder.getFromLocationName(strAddress, 5);
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