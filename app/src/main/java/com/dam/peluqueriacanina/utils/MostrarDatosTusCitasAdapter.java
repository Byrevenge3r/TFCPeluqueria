package com.dam.peluqueriacanina.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.BotonTusCitas;

import java.util.ArrayList;

public class MostrarDatosTusCitasAdapter extends RecyclerView.Adapter<MostrarDatosTusCitasAdapter.MostrarDatosTusCitasAdapterVH>
        implements View.OnClickListener {

    private final ArrayList<BotonTusCitas> datos;

    private View.OnClickListener listener;

    public MostrarDatosTusCitasAdapter(ArrayList<BotonTusCitas> datos) {
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
    public MostrarDatosTusCitasAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.btn_datos_cita, parent, false);
        v.setOnClickListener(this);
        return new MostrarDatosTusCitasAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MostrarDatosTusCitasAdapterVH holder, int position) {
        holder.bindBoton(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class MostrarDatosTusCitasAdapterVH extends RecyclerView.ViewHolder {
        private final TextView tvDatosCitas;

        public MostrarDatosTusCitasAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvDatosCitas = itemView.findViewById(R.id.tvVerDatosCita);
        }

        public void bindBoton(BotonTusCitas datos) {
            tvDatosCitas.setText(datos.getVerDatos());
        }
    }
}
