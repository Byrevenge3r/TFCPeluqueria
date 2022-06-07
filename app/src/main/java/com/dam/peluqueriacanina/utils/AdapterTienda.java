package com.dam.peluqueriacanina.utils;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.DatosTienda;

import java.util.ArrayList;

public class AdapterTienda extends RecyclerView.Adapter<AdapterTienda.AdapterTiendaVH>
        implements View.OnClickListener {

    private ArrayList<DatosTienda> datos;
    private View.OnClickListener listener;

    public AdapterTienda(ArrayList<DatosTienda> datos) {
        this.datos = datos;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setDatos(ArrayList<DatosTienda> datos) {
        this.datos = datos;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public AdapterTiendaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_mostrar_productos, parent, false);
        v.setOnClickListener(this);
        return new AdapterTiendaVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTiendaVH holder, int position) {
        holder.bindTienda(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class AdapterTiendaVH extends RecyclerView.ViewHolder {
        private final TextView textoProductoTienda;
        private final TextView textoPrecioTienda;
        private final TextView textoTipoTienda;
        private final ImageView fotoProductoTienda;


        public AdapterTiendaVH(@NonNull View itemView) {
            super(itemView);
            textoProductoTienda = itemView.findViewById(R.id.tv_pienso_producto_tienda);
            textoTipoTienda = itemView.findViewById(R.id.tv_nombre_producto_tienda);
            fotoProductoTienda = itemView.findViewById(R.id.image_objeto_tienda);
            textoPrecioTienda = itemView.findViewById(R.id.tv_precio_final_producto_tienda);
        }

        public void bindTienda(DatosTienda tienda) {
            textoProductoTienda.setText(tienda.getNombre());
            textoTipoTienda.setText(tienda.getTipo());
            textoPrecioTienda.setText(itemView.getContext().getString(R.string.precio, tienda.getPrecio()));
            fotoProductoTienda.setImageDrawable(itemView.getContext().getDrawable(tienda.getFoto()));
        }
    }
}
