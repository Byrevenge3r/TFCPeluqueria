package com.dam.peluqueriacanina.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.Chat;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatAdapterVH> {

    private final ArrayList<Chat> datos;

    public ChatAdapter(ArrayList<Chat> datos) {
        this.datos = datos;
    }

    public void aniadirMensaje(Chat mensaje) {
        datos.add(mensaje);
    }

    @NonNull
    @Override
    public ChatAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_layout_usuario, parent, false);
        return new ChatAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapterVH holder, int position) {
        holder.bindMensaje(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ChatAdapterVH extends RecyclerView.ViewHolder {
        private final TextView tvUsuario;
        private final LinearLayout ll;

        public ChatAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvMensajeUsuario);
            ll = itemView.findViewById(R.id.llChat);
        }

        public void bindMensaje(Chat mensaje) {
            if (!mensaje.getCodigoPer().equals("U")) {
                ll.setGravity(Gravity.START);
            } else {
                ll.setGravity(Gravity.END);
            }
            tvUsuario.setText(mensaje.getMensaje());


        }
    }
}
