package com.dam.peluqueriacanina.mainActivity.tienda;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.DatosTienda;
import com.dam.peluqueriacanina.utils.AdapterTienda;

import java.util.ArrayList;
import java.util.Locale;

public class TiendaActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearTienda, linearProducto;
    ImageView imageProductoTienda;
    TextView  tvObjetoNombreTienda;
    EditText etBuscarPorNombre;

    LinearLayout llmAlimentacion, llmAccesorios,llmJuguetes;

    ArrayList<DatosTienda> listaTienda = new ArrayList<>();
    ArrayList<DatosTienda> listaTiendaFiltrada = new ArrayList<>();

    RecyclerView rv;
    AdapterTienda adapter;
    CardView cvAlimentacion, cvAccesorios, cvJuguetes;

    //Tienda
    int[] imageTienda = {};
    String[] nombreObjetoTienda = {};

    //Producto
    int[] imagenProducto = {};
    String[] nombreObjetoProducto = {};
    String[] pesoPiensos = {};
    String[] precioFinalProducto = {};
    String[] precioProducto = {};

    boolean colorAlimentacion, colorAccesorios, colorJuguetes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        linearTienda = findViewById(R.id.llTienda);
        linearProducto = findViewById(R.id.llProducto);
        rv = findViewById(R.id.rvMostrarProductos);

        llmAlimentacion = findViewById(R.id.llAlimentacion);
        llmJuguetes = findViewById(R.id.llJuguetes);
        llmAccesorios = findViewById(R.id.llAccesorios);

        cvAccesorios = findViewById(R.id.cvAccesorios);
        cvJuguetes = findViewById(R.id.cvJuguetes);
        cvAlimentacion = findViewById(R.id.cvAlimentacion);

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        listaTienda.add(new DatosTienda("Pienso perros","adasd","alimentacion"));
        listaTienda.add(new DatosTienda("Pienso gato","adasd","alimentacion"));
        listaTienda.add(new DatosTienda("juguete gato","adasd","accesorio"));
        listaTienda.add(new DatosTienda("juguete perro","adasd","accesorio"));
        listaTienda.add(new DatosTienda("yo que se","adasd","alimentacion"));
        listaTienda.add(new DatosTienda("yo que se","adasd","accesorio"));

        etBuscarPorNombre = findViewById(R.id.edit_text_buscar);
        adapter = new AdapterTienda(listaTienda);
        rv.setAdapter(adapter);
        cargarTienda();
        cargarProducto();

        cvAlimentacion.setOnClickListener(this);
        cvJuguetes.setOnClickListener(this);
        cvAccesorios.setOnClickListener(this);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        etBuscarPorNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listaTiendaFiltrada.clear();
                if (colorAlimentacion) {
                    cargarDatosConFiltro("alimentacion");
                } else if (colorAccesorios) {
                    cargarDatosConFiltro("accesorio");
                } else if (colorJuguetes) {
                    cargarDatosConFiltro("juguete");
                } else {
                    for (int i = 0; i < listaTienda.size();i++) {
                        if (listaTienda.get(i).getNombre().toLowerCase(Locale.ROOT).contains(etBuscarPorNombre.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                            listaTiendaFiltrada.add(listaTienda.get(i));
                        }
                    }

                    adapter.setDatos(listaTiendaFiltrada);
                    rv.setAdapter(adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    //Esto no tengo npi de idea de lo que hace pero lo voy a quitar por que no sirve
    public void cargarProducto(){
        LayoutInflater inflater = LayoutInflater.from(this);
        int i;
        for(i=0;i<imagenProducto.length;i++){
            View view = inflater.inflate(R.layout.objeto_producto ,linearProducto, false);
            ImageView imageProduct = view.findViewById(R.id.image_producto_tienda);
            TextView tvNombre = view.findViewById(R.id.tv_nombre_producto);
            TextView tvpienso = view.findViewById(R.id.tv_pienso_producto);
            TextView tvPrecioFinal = view.findViewById(R.id.tv_precion_final_producto);
            TextView tvPrecio = view.findViewById(R.id.tv_precio_producto);
            imageProduct.setImageResource(imagenProducto[i]);
            tvNombre.setText(nombreObjetoProducto[i]);
            tvpienso.setText(pesoPiensos[i]);
            tvPrecioFinal.setText(precioFinalProducto[i]);
            tvPrecio.setText(precioProducto[i]);

            linearProducto.addView(view);
        }
    }

    public void cargarTienda(){
        LayoutInflater inflater = LayoutInflater.from(this);
        int i;
        for(i=0;i<imageTienda.length;i++){
            View view = inflater.inflate(R.layout.objetos_tienda,linearTienda, false);
            imageProductoTienda = view.findViewById(R.id.image_producto_tienda);
            tvObjetoNombreTienda = view.findViewById(R.id.tv_objeto_nombre_tienda);
            imageProductoTienda.setImageResource(imageTienda[i]);
            tvObjetoNombreTienda.setText(nombreObjetoTienda[i]);
            final int aux = 1;
            imageProductoTienda.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String objetoSeleccionado = nombreObjetoTienda[aux];
                    Toast.makeText(getApplicationContext(), "Objeto "+objetoSeleccionado,Toast.LENGTH_LONG).show();
                    Intent tienda = new Intent(getApplicationContext(), Tienda2Activity.class);
                    startActivity(tienda);
                }
            });

            linearTienda.addView(view);
        }
    }

    //REFORMAT MAÑANA
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (v.equals(cvAccesorios)) {
            if (!colorAccesorios) {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.color_gris));
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.white));
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.white));
                colorAccesorios = true;
                colorAlimentacion = false;
                colorJuguetes = false;
                cargarDatosConFiltro("accesorio");
            } else {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.white));
                colorAccesorios = false;
                adapter.setDatos(listaTienda);
                rv.setAdapter(adapter);
            }
        } else if (v.equals(cvAlimentacion)) {
            if (!colorAlimentacion) {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.white));
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.white));
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.color_gris));
                colorAlimentacion = true;
                colorAccesorios = false;
                colorJuguetes = false;
                cargarDatosConFiltro("alimentacion");
            } else {
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.white));
                colorAlimentacion = false;
                adapter.setDatos(listaTienda);
                rv.setAdapter(adapter);
            }
        } else if (v.equals(cvJuguetes)) {
            if (!colorJuguetes) {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.white));
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.color_gris));
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.white));
                colorJuguetes = true;
                colorAccesorios = false;
                colorAlimentacion = false;
                cargarDatosConFiltro("juguetes");
            } else {
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.white));
                colorJuguetes = false;
                adapter.setDatos(listaTienda);
                rv.setAdapter(adapter);
            }
        }
    }
    private void cargarDatosConFiltro(String tipo) {
        listaTiendaFiltrada.clear();
        if (!tipo.isEmpty() && !etBuscarPorNombre.getText().toString().isEmpty()) {
            for (int i = 0; i < listaTienda.size(); i++) {
                if (listaTienda.get(i).getNombre().toLowerCase(Locale.ROOT).contains(etBuscarPorNombre.getText().toString().trim().toLowerCase(Locale.ROOT))
                        && listaTienda.get(i).getTipo().toLowerCase(Locale.ROOT).equals(tipo.toLowerCase(Locale.ROOT))) {

                    listaTiendaFiltrada.add(listaTienda.get(i));
                }
            }

            adapter.setDatos(listaTiendaFiltrada);
            rv.setAdapter(adapter);
        }

        if (etBuscarPorNombre.getText().toString().isEmpty()) {
            for (int i = 0; i < listaTienda.size(); i++) {
                if (listaTienda.get(i).getTipo().equals(tipo)) {
                    listaTiendaFiltrada.add(listaTienda.get(i));
                }
            }
            adapter.setDatos(listaTiendaFiltrada);
            rv.setAdapter(adapter);
        }
    }
}