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

public class MisAnimalesAdapter extends RecyclerView.Adapter<MisAnimalesAdapter.MisAnimalesAdapterVH>
            implements View.OnClickListener {

    private final ArrayList<Animal> datos;
    private View.OnClickListener listener;


    public MisAnimalesAdapter(ArrayList<Animal> datos) {
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
    public MisAnimalesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mascotas_recicler_view, parent, false);
        v.setOnClickListener(this);
        MisAnimalesAdapter.MisAnimalesAdapterVH vh = new MisAnimalesAdapter.MisAnimalesAdapterVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MisAnimalesAdapterVH holder, int position) {
        holder.bindMenu(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public static class MisAnimalesAdapterVH extends RecyclerView.ViewHolder {

        private final ShapeableImageView imagenAnimal;


        public MisAnimalesAdapterVH(@NonNull View itemView) {
            super(itemView);
            imagenAnimal = itemView.findViewById(R.id.siAnimal);

        }

        public void bindMenu (Animal animal) {
            imagenAnimal.setImageBitmap(BitmapFactory.decodeFile(animal.getRuta()));
        }
    }
}
