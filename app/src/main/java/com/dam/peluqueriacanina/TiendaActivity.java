package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TiendaActivity extends AppCompatActivity {

    LinearLayout linearTienda, linearProducto;
    ImageView imageProductoTienda;
    TextView  tvObjetoNombreTienda;

    //Tienda
    int[] imageTienda = {};
    String[] nombreObjetoTienda = {};

    //Producto
    int[] imagenProducto = {};
    String[] nombreObjetoProducto = {};
    String[] pesoPiensos = {};
    String[] precioFinalProducto = {};
    String[] precioProducto = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        linearTienda = findViewById(R.id.llTienda);
        linearProducto = findViewById(R.id.llProducto);

        cargarTienda();
        cargarProducto();
    }

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
                    Intent tienda = new Intent(getApplicationContext(),Tienda2Activity.class);
                    startActivity(tienda);
                }
            });

            linearTienda.addView(view);
        }
    }
}