package com.dam.peluqueriacanina.mainActivity.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dam.peluqueriacanina.R;

public class TiendaDetallesActivity extends AppCompatActivity {

    ImageButton iBMas, iBMenos;
    Button btnCesta;
    TextView tvNombreProd, tvPesoPienso, tvDetalle, tvInfo, tvPrecioFin, tvPrecio;
    RatingBar rbEstrellas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_detalles);

        iBMas = findViewById(R.id.imagen_remove_tienda_detalles);
        iBMenos = findViewById(R.id.imagen_add_tienda_detalles);
        btnCesta = findViewById(R.id.btn_add_cesta);
        rbEstrellas = findViewById(R.id.rat_tienda_detalles);
        tvNombreProd = findViewById(R.id.tv_nombre_producto_tienda_detalle);
        tvPesoPienso = findViewById(R.id.tv_pienso_producto_tienda_detalles);
        tvDetalle = findViewById(R.id.tv_producto_detalle);
        tvInfo = findViewById(R.id.tv_info_tienda_detalle);
        tvPrecioFin = findViewById(R.id.tv_precio_final_producto_tienda_detalle);
        tvPrecio = findViewById(R.id.tv_precio_producto_tienda_detalle);
    }
}