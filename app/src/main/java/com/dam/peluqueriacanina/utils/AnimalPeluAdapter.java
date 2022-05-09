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
import com.dam.peluqueriacanina.model.AnimalReservaPelu;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AnimalPeluAdapter extends RecyclerView.Adapter<AnimalPeluAdapter.AnimalPeluAdapterVH>
        implements View.OnClickListener{

    private final ArrayList<TusCitas> datos;
    private View.OnClickListener listener;


    public AnimalPeluAdapter(ArrayList<TusCitas> datos) {
        this.datos = datos;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public AnimalPeluAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citas_recicler_view_vet, parent, false);
        v.setOnClickListener(this);
        AnimalPeluAdapterVH vh = new AnimalPeluAdapterVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalPeluAdapterVH holder, int position) {
        holder.bindAnimal(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public static class AnimalPeluAdapterVH extends RecyclerView.ViewHolder {
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
            shAnimal.setImageBitmap(BitmapFactory.decodeFile(animal.getRuta()));
            tvNom.setText(animal.getNombre());
            tvFecha.setText(animal.getFecha());
            tvHora.setText(animal.getHora());
        }
    }
}
