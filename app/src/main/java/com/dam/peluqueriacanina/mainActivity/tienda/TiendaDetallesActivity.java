package com.dam.peluqueriacanina.mainActivity.tienda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.model.DatosTienda;
import com.dam.peluqueriacanina.model.Rating;
import com.dam.peluqueriacanina.model.RatingUser;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TiendaDetallesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton iBMas, iBMenos, ivCarrito;
    Button btnCesta;
    TextView tvNombreProd, tvPesoPienso, tvDetalle, tvInfo, tvPrecioFin, tvCantidad;
    RatingBar rbEstrellas;
    DatosTienda tienda;
    int contador = 0;
    CestaDao dao;
    CestaDB db;
    ImageView fotoProducto;
    FirebaseDatabase fb;
    DatabaseReference dbRef;
    String nombreObj = "";
    Cesta cesta;
    ArrayList<Cesta> listaCompra;
    boolean existe = false;
    ArrayList<Rating> listaRating;
    float rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_detalles);

        fb = FirebaseDatabase.getInstance();
        dbRef = fb.getReference();

        db = CestaDB.getDatabase(this);
        dao = db.cestaDao();

        listaCompra = new ArrayList<>();
        tienda = getIntent().getParcelableExtra("tienda");
        nombreObj = getIntent().getStringExtra("nombreObj");

        dbRef.child("rating/"+tienda.getNombre()+"/usuarios").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()) {
                    for (DataSnapshot sp: task.getResult().getChildren()) {
                        listaRating.add(sp.getValue(Rating.class));
                    }
                    if (!listaRating.isEmpty()) {
                        for (int i = 0; i < listaRating.size(); i++) {
                            rating += listaRating.get(i).getRating();
                        }
                        rbEstrellas.setRating(rating/listaRating.size());
                    }

                }
            }
        });

        listaRating = new ArrayList<>();
        fotoProducto = findViewById(R.id.producto_detalle_tienda);
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

        fotoProducto.setImageDrawable(getBaseContext().getDrawable(tienda.getFoto()));
        tvPesoPienso.setText(tienda.getCantidad());
        tvPrecioFin.setText(getString(R.string.precio, tienda.getPrecio()));
        tvNombreProd.setText(tienda.getNombre());
        tvPesoPienso.setText(tienda.getCantidad());
        tvInfo.setText(tienda.getDetalle());


        rbEstrellas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                HashMap<String,Object> ratingObj = new HashMap<>();
                if (fromUser) {
                    ratingObj.put("rating",(ratingBar.getRating()));
                    dbRef.child("rating/"+tienda.getNombre()+"/usuarios/"+((MiApplication) getApplicationContext()).getKey()).updateChildren(ratingObj);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        contador = Integer.parseInt(tvCantidad.getText().toString());
        if (v.equals(iBMas)&& contador + 1 <= 99) {
            tvCantidad.setText(String.valueOf(contador + 1));
        } else if (v.equals(iBMenos) && contador - 1 >= 1) {
            tvCantidad.setText(String.valueOf(contador - 1));
        } else if (v.equals(btnCesta)) {
            String[] precio;
            precio = tvPrecioFin.getText().toString().split("€");
            cesta = new Cesta(tienda.getNombre(), Integer.parseInt(tvCantidad.getText().toString()), Integer.parseInt(precio[0]));
            listaCompra = (ArrayList<Cesta>) dao.sacarTodo();

            for (int i = 0;i < listaCompra.size();i++) {
                if (listaCompra.get(i).getNombre().equals(cesta.getNombre())) {
                    listaCompra.get(i).setCantidad(listaCompra.get(i).getCantidad()+cesta.getCantidad());
                    existe = true;
                    dao.delete(listaCompra);
                    dao.insert(listaCompra);
                }
            }
            if (!existe) {
                dao.insert(cesta);
            }
            existe = false;
            tvCantidad.setText("1");
        } else if (v.equals(ivCarrito)) {
            if (dao.sacarTodo().isEmpty()) {
                Toast.makeText(this, R.string.carrito_vacio, Toast.LENGTH_SHORT).show();
            } else {
                tvCantidad.setText("1");
                Intent i = new Intent(this, MostrarCompraActivity.class);
                startActivity(i);
            }

        }
    }

}