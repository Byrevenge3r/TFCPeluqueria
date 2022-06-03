package com.dam.peluqueriacanina.mainActivity.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.model.DatosTienda;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TiendaDetallesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton iBMas, iBMenos, ivCarrito;
    Button btnCesta;
    TextView tvNombreProd, tvPesoPienso, tvDetalle, tvInfo, tvPrecioFin, tvPrecio, tvCantidad;
    RatingBar rbEstrellas;
    DatosTienda tienda;
    int contador = 0;
    CestaDao dao;
    CestaDB db;
    FirebaseDatabase fb;
    DatabaseReference dbRef;
    String nombreObj = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_detalles);

        fb = FirebaseDatabase.getInstance();
        dbRef = fb.getReference();

        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        tienda = getIntent().getParcelableExtra("tienda");
        nombreObj = getIntent().getStringExtra("nombreObj");

        iBMas = findViewById(R.id.imagen_add_tienda_detalles);
        iBMenos = findViewById(R.id.imagen_remove_tienda_detalles);
        ivCarrito = findViewById(R.id.imagen_compra_tienda_detalles);
        btnCesta = findViewById(R.id.btn_add_cesta);
        rbEstrellas = findViewById(R.id.rat_tienda_detalles);
        tvNombreProd = findViewById(R.id.tv_nombre_producto_tienda_detalle);
        tvPesoPienso = findViewById(R.id.tv_cantidad_producto_tienda_detalles);
        tvDetalle = findViewById(R.id.tv_producto_detalle);
        tvInfo = findViewById(R.id.tv_info_tienda_detalle);
        tvPrecioFin = findViewById(R.id.tv_precio_final_producto_tienda_detalle);
        tvCantidad = findViewById(R.id.tv_comprar_detalle_producto);

        iBMas.setOnClickListener(this);
        iBMenos.setOnClickListener(this);
        btnCesta.setOnClickListener(this);
        ivCarrito.setOnClickListener(this);

        tvPesoPienso.setText(tienda.getCantidad());
        tvPrecioFin.setText(tienda.getPrecio());
        tvNombreProd.setText(tienda.getNombre());
        tvPesoPienso.setText(tienda.getCantidad());
        tvInfo.setText(tienda.getDetalle());

        //Hacer el rating y terminar con la interfaz de la tienda (SAMU TE FOLLO)
    }

    @Override
    public void onClick(View v) {
        contador = Integer.parseInt(tvCantidad.getText().toString());
        if (v.equals(iBMas)){
            tvCantidad.setText(String.valueOf(contador+1));
        } else if (v.equals(iBMenos) && contador-1 >= 1) {
            tvCantidad.setText(String.valueOf(contador-1));
        } else if (v.equals(btnCesta)) {
            String[] precio;
            precio = tvPrecioFin.getText().toString().split("€");
            dao.insert(new Cesta(tienda.getNombre(),Integer.parseInt(tvCantidad.getText().toString()),Integer.parseInt(precio[0])));
        } else if (v.equals(ivCarrito)) {
            Intent i = new Intent(this,MostrarCompraActivity.class);
            startActivity(i);
        }
    }
}