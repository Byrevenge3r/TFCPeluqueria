package com.dam.peluqueriacanina.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.VetCitas;

import java.util.ArrayList;

public class VetCitasAdapter extends RecyclerView.Adapter<VetCitasAdapter.VetCitasAdapterVH>
        implements View.OnClickListener {

    private ArrayList<VetCitas> datos;
    private View.OnClickListener listener;

    public void setDatos(ArrayList<VetCitas> datos) {
        this.datos = datos;
    }

    public VetCitasAdapter(ArrayList<VetCitas> datos) {
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
    public VetCitasAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citas_recicler_view_vet, parent, false);
        v.setOnClickListener(this);
        return new VetCitasAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VetCitasAdapterVH holder, int position) {
        holder.bindCita(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class VetCitasAdapterVH extends RecyclerView.ViewHolder {
        private final TextView tvNom;
        private final TextView tvFecha;
        private final TextView tvHora;

        public VetCitasAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNomVet);
            tvFecha = itemView.findViewById(R.id.tvDiaVet);
            tvHora = itemView.findViewById(R.id.tvHoraVet);
        }

        public void bindCita(VetCitas cita) {
            tvNom.setText(itemView.getContext().getString(R.string.nom_vet, cita.getNom()));
            tvFecha.setText(itemView.getContext().getString(R.string.dia_vet, cita.getCitaFecha()));
            tvHora.setText(itemView.getContext().getString(R.string.hora_vet, cita.getCitaHora()));
        }
    }
}
