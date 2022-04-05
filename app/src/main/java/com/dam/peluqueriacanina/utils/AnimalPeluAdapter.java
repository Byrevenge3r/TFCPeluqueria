package com.dam.peluqueriacanina.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.AnimalReservaPelu;

import java.util.ArrayList;

public class AnimalPeluAdapter extends RecyclerView.Adapter<AnimalPeluAdapter.AnimalPeluAdapterVH>
        implements View.OnClickListener{

    private ArrayList<AnimalReservaPelu> datos;
    private View.OnClickListener listener;


    public AnimalPeluAdapter(ArrayList<AnimalReservaPelu> datos) {
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
        private TextView tvNom;
        private TextView tvFecha;
        private TextView tvHora;

        public AnimalPeluAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNomAnimal);
            tvFecha = itemView.findViewById(R.id.tvDia);
            tvHora = itemView.findViewById(R.id.tvHora);
        }

        public void bindAnimal(AnimalReservaPelu animal) {
            tvNom.setText(animal.getNombreA());
            tvFecha.setText(animal.getFecha());
            tvHora.setText(animal.getHora());
        }
    }
}
