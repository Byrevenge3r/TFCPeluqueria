package com.dam.peluqueriacanina.mainActivity.tienda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.utils.CarritoAdapter;

import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

public class MostrarCompraActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    RecyclerView rv;
    LinearLayoutManager llm;
    CarritoAdapter adapter;
    ArrayList<Cesta> listaCompra;
    CestaDB db;
    CestaDao dao;
    int precioTotal;
    TextView tvPrecioTotal;
    Button btnPagar;
    Uri uri;
    String name = "Fernando pasquin";
    String upiId = "ferpascan@gmail.com";
    String transactionNote = "pay test";
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dam.peluqueriacanina.R.layout.activity_mostrar_compra);

        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        tvPrecioTotal = findViewById(R.id.precioTotal);
        btnPagar = findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(this);
        listaCompra = new ArrayList<>();
        rv = findViewById(R.id.rvTusCompras);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);


        listaCompra = (ArrayList<Cesta>) dao.sacarTodo();

        adapter = new CarritoAdapter(listaCompra);
        rv.setAdapter(adapter);

        if (!listaCompra.isEmpty()) {
            for (int i = 0; i < listaCompra.size();i++) {
                precioTotal += listaCompra.get(i).getPrecio() * listaCompra.get(i).getCantidad();
            }
            tvPrecioTotal.setText(getString(R.string.precio_total,String.valueOf(precioTotal)));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnPagar)) {

        }
    }
}