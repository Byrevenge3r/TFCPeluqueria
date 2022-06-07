package com.dam.peluqueriacanina.mainActivity.tienda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.comunicacion.ComunicacionProductos;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.mainActivity.tienda.fragmentos.BorrarCantidadFragment;
import com.dam.peluqueriacanina.model.BotonBorrarProducto;
import com.dam.peluqueriacanina.utils.CarritoAdapter;
import com.dam.peluqueriacanina.utils.MostrarDatosTusCitasAdapter;

import java.util.ArrayList;

public class MostrarCompraActivity extends AppCompatActivity implements View.OnClickListener, ComunicacionProductos {

    RecyclerView rv,rvBorrar;
    LinearLayoutManager llm;
    CarritoAdapter adapter;
    ArrayList<Cesta> listaCompra;
    CestaDB db;
    CestaDao dao;
    TextView tvPrecioTotal;
    Button btnPagar;
    MostrarDatosTusCitasAdapter adapterDetallesCita;
    BotonBorrarProducto boton;
    Cesta cesta;
    BorrarCantidadFragment borrar;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_mostrar_compra);

        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        borrar = new BorrarCantidadFragment();

        bundle = new Bundle();

        tvPrecioTotal = findViewById(R.id.precioTotal);
        btnPagar = findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(this);
        listaCompra = new ArrayList<>();
        rv = findViewById(R.id.rvTusCompras);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);

        boton = new BotonBorrarProducto();

        listaCompra = (ArrayList<Cesta>) dao.sacarTodo();
        adapterDetallesCita = new MostrarDatosTusCitasAdapter(boton.getBoton());
        calcularPrecioTotal();
        adapter = new CarritoAdapter(listaCompra);
        rv.setAdapter(adapter);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llCompra:

                        rvBorrar = v.findViewById(R.id.rlContainerCompra).findViewById(R.id.rvBorrar);
                        rvBorrar.setLayoutManager(new LinearLayoutManager(v.findViewById(R.id.llCompra).getContext()));
                        rvBorrar.setAdapter(adapterDetallesCita);

                        adapterDetallesCita.setListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cesta = listaCompra.get(rv.getChildAdapterPosition(v));

                                bundle.putParcelable("producto",cesta);
                                getSupportFragmentManager().setFragmentResult("key", bundle);
                                borrar.show(getSupportFragmentManager(),"Borrar");

                            }
                        });
                    break;
                }
            }
        });
    }

    private void calcularPrecioTotal() {
        int precioTotal = 0;
        for (int i = 0; i < listaCompra.size(); i++) {
            precioTotal += listaCompra.get(i).getPrecio() * listaCompra.get(i).getCantidad();
        }
        tvPrecioTotal.setText(getString(R.string.precio_total, String.valueOf(precioTotal)));
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnPagar)) {

        }
    }

    @Override
    public void comunicacion(Cesta cesta) {
        listaCompra = (ArrayList<Cesta>) dao.sacarTodo();
        adapter.setDatos(listaCompra);
        rv.setAdapter(adapter);
        calcularPrecioTotal();

    }
}