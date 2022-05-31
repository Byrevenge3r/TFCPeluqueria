package com.dam.peluqueriacanina.utils;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.entity.TusCitas;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimalPeluAdapter extends RecyclerView.Adapter<AnimalPeluAdapter.AnimalPeluAdapterVH>
        implements View.OnClickListener{

    private ArrayList<TusCitas> datos;
    private View.OnClickListener listener;


    public AnimalPeluAdapter(ArrayList<TusCitas> datos) {
        this.datos = datos;
    }

    public void setDatos(ArrayList<TusCitas> datos) {
        this.datos = datos;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AnimalPeluAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citas_recicler_view_pel, parent, false);
        v.setOnClickListener(this);
        return new AnimalPeluAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalPeluAdapterVH holder, int position) {
        holder.bindAnimal(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public static class AnimalPeluAdapterVH extends RecyclerView.ViewHolder  {
        private final TextView tvNom;
        private final TextView tvFecha;
        private final TextView tvHora;
        private final ShapeableImageView shAnimal;

        public AnimalPeluAdapterVH(@NonNull View itemView) {
            super(itemView);
            shAnimal = itemView.findViewById(R.id.shAnimal);
            tvNom = itemView.findViewById(R.id.tvNomAnimal);
            tvFecha = itemView.findViewById(R.id.tvDia);
            tvHora = itemView.findViewById(R.id.tvHora);
        }

        public void bindAnimal(TusCitas animal) {
            Picasso.get().load(animal.getUrlI()).resize(50,50).centerCrop().into(shAnimal);
            tvNom.setText(itemView.getContext().getString(R.string.nom_animal_pel,animal.getNomAnimal()));
            tvFecha.setText(itemView.getContext().getString(R.string.dia_anima_pel,animal.getCitaFecha()));
            tvHora.setText(itemView.getContext().getString(R.string.hora_animal_vet,animal.getCitaHora()));
        }
    }
}
