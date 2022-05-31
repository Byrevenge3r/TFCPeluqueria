package com.dam.peluqueriacanina.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.model.DatosTienda;

import java.util.ArrayList;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoAdapterVH>
            implements View.OnClickListener {

    private ArrayList<Cesta> datos;
    private View.OnClickListener listener;

    public CarritoAdapter(ArrayList<Cesta> datos) {
        this.datos = datos;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarritoAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mostrar_compra_recycler, parent, false);
        v.setOnClickListener(this);
        return new CarritoAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapterVH holder, int position) {
        holder.bindCarrito(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public static class CarritoAdapterVH extends RecyclerView.ViewHolder {
        private final TextView tvNomProducto, tvCantidadProducto, tvPrecioProducto;

        public CarritoAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvNomProducto = itemView.findViewById(R.id.tvNomCompra);
            tvCantidadProducto = itemView.findViewById(R.id.tvCantidadCompra);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioCompra);
        }

        public void bindCarrito (Cesta cesta) {
            tvNomProducto.setText(itemView.getContext().getString(R.string.nom_compra_carrito,cesta.getNombre()));
            tvCantidadProducto.setText(itemView.getContext().getString(R.string.cantidad_compra_carrito,String.valueOf(cesta.getCantidad())));
            tvPrecioProducto.setText(itemView.getContext().getString(R.string.precio_compra_carrito,String.valueOf(cesta.getPrecio())));
        }
    }
}
