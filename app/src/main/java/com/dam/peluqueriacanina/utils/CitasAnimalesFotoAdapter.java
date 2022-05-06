package com.dam.peluqueriacanina.utils;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.entity.Animal;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class CitasAnimalesFotoAdapter extends RecyclerView.Adapter<CitasAnimalesFotoAdapter.CitasAnimalesFotoAdapterVH>
            implements View.OnClickListener {

    private final ArrayList<Animal> datos;
    private View.OnClickListener listener;


    public CitasAnimalesFotoAdapter(ArrayList<Animal> datos) {
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
    public CitasAnimalesFotoAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citas_recicler_view_citas_adapter, parent, false);
        v.setOnClickListener(this);
        return new CitasAnimalesFotoAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasAnimalesFotoAdapterVH holder, int position) {
        holder.bindMenu(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public static class CitasAnimalesFotoAdapterVH extends RecyclerView.ViewHolder {

        private final ShapeableImageView imagenAnimal;


        public CitasAnimalesFotoAdapterVH(@NonNull View itemView) {
            super(itemView);
            imagenAnimal = itemView.findViewById(R.id.shImagenAnimalReserva);

        }

        public void bindMenu (Animal animal) {
            imagenAnimal.setImageBitmap(BitmapFactory.decodeFile(animal.getRuta()));
        }
    }
}
