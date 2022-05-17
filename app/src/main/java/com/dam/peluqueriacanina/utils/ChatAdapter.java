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

    private ArrayList<Chat> datos;

    public ChatAdapter (ArrayList<Chat> datos){
        this.datos = datos;
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
        private TextView tvUsuario;
        private LinearLayout ll;

        public ChatAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvMensajeUsuario);
            ll = itemView.findViewById(R.id.llChat);
        }

        public void bindMensaje (Chat mensaje) {
            if (mensaje.getCodigoPer().equals("U")) {
                ll.setGravity(Gravity.END);
            } else {
                ll.setGravity(Gravity.START);
            }
            tvUsuario.setText(mensaje.getMensaje());


        }
    }
}
