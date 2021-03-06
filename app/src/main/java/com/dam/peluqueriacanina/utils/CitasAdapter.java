package com.dam.peluqueriacanina.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.CitasReserva;

import java.util.ArrayList;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitasAdapterVH>
        implements View.OnClickListener {

    private ArrayList<CitasReserva> datos;
    private View.OnClickListener listener;


    public void setDatos(ArrayList<CitasReserva> datos) {
        this.datos = datos;
    }

    public CitasAdapter(ArrayList<CitasReserva> datos) {
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
    public CitasAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citas_recicler_view_pelu_crear_cita, parent, false);
        v.setOnClickListener(this);
        return new CitasAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasAdapterVH holder, int position) {
        holder.bindCita(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class CitasAdapterVH extends RecyclerView.ViewHolder {
        private final TextView tvHoraCita;

        public CitasAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvHoraCita = itemView.findViewById(R.id.tvHoraCita);
        }

        public void bindCita(CitasReserva cita) {
            tvHoraCita.setText(cita.getHora());
        }
    }
}
