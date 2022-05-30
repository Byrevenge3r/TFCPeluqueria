package com.dam.peluqueriacanina.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.entity.TusCitas;
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
        private final ImageView fotoProductoTienda;


        public AdapterTiendaVH(@NonNull View itemView) {
            super(itemView);

            textoProductoTienda = itemView.findViewById(R.id.textoProductoTienda);
            fotoProductoTienda = itemView.findViewById(R.id.fotoProductoTienda);
        }

        public void bindTienda (DatosTienda tienda) {
            textoProductoTienda.setText(tienda.getNombre());
        }
    }
}