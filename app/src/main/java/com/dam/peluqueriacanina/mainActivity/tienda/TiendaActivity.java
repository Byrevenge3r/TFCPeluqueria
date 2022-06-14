package com.dam.peluqueriacanina.mainActivity.tienda;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.model.DatosTienda;
import com.dam.peluqueriacanina.model.datos.ProductosTienda;
import com.dam.peluqueriacanina.utils.AdapterTienda;

import java.util.ArrayList;
import java.util.Locale;

public class TiendaActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearTienda, linearProducto;
    ImageView imageProductoTienda;
    TextView tvObjetoNombreTienda;
    EditText etBuscarPorNombre;

    LinearLayoutManager llm;

    ImageView ivCarrito;

    LinearLayout llmAlimentacion, llmAccesorios, llmJuguetes;

    ArrayList<DatosTienda> listaTienda;
    ArrayList<DatosTienda> listaTiendaFiltrada = new ArrayList<>();

    RecyclerView rv;
    AdapterTienda adapter;
    CardView cvAlimentacion, cvAccesorios, cvJuguetes;

    DatosTienda tienda;

    CestaDao dao;
    CestaDB db;

    ProductosTienda productos;

    boolean colorAlimentacion, colorAccesorios, colorJuguetes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        listaTienda = new ArrayList<>();
        linearTienda = findViewById(R.id.llTienda);
        linearProducto = findViewById(R.id.llProducto);
        rv = findViewById(R.id.rvMostrarProductos);

        productos = new ProductosTienda();
        listaTienda = productos.getListaTienda();

        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        ivCarrito = findViewById(R.id.imagen_compra_tienda);

        llmAlimentacion = findViewById(R.id.llAlimentacion);
        llmJuguetes = findViewById(R.id.llJuguetes);
        llmAccesorios = findViewById(R.id.llAccesorios);

        cvAccesorios = findViewById(R.id.cvAccesorios);
        cvJuguetes = findViewById(R.id.cvJuguetes);
        cvAlimentacion = findViewById(R.id.cvAlimentacion);

        llm = new LinearLayoutManager(this);

        rv.setLayoutManager(llm);

        etBuscarPorNombre = findViewById(R.id.edit_text_buscar);
        adapter = new AdapterTienda(listaTienda);
        rv.setAdapter(adapter);


        cvAlimentacion.setOnClickListener(this);
        cvJuguetes.setOnClickListener(this);
        cvAccesorios.setOnClickListener(this);
        ivCarrito.setOnClickListener(this);
        imageProductoTienda = findViewById(R.id.image_producto_tienda);
        tvObjetoNombreTienda = findViewById(R.id.tv_objeto_nombre_tienda);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listaTiendaFiltrada.isEmpty()) {
                    tienda = listaTiendaFiltrada.get(rv.getChildAdapterPosition(v));
                } else {
                    tienda = listaTienda.get(rv.getChildAdapterPosition(v));
                }

                Intent i = new Intent(TiendaActivity.this, TiendaDetallesActivity.class);
                i.putExtra("tienda", tienda);
                i.putExtra("nombreObj", tienda.getNombre());
                startActivity(i);
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
                    cargarDatosConFiltro("accesorios");
                } else if (colorJuguetes) {
                    cargarDatosConFiltro("juguetes");
                } else {
                    for (int i = 0; i < listaTienda.size(); i++) {
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
                cargarDatosConFiltro("accesorios");
            } else {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.white));
                colorAccesorios = false;
                listaTiendaFiltrada.clear();
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
                listaTiendaFiltrada.clear();
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
                listaTiendaFiltrada.clear();
                adapter.setDatos(listaTienda);
                rv.setAdapter(adapter);
            }
        } else if (v.equals(ivCarrito)) {
            if (dao.sacarTodo().isEmpty()) {
                Toast.makeText(this, R.string.carrito_vacio, Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(this, MostrarCompraActivity.class);
                startActivity(i);
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