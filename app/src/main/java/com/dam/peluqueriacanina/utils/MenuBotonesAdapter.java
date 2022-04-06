package com.dam.peluqueriacanina.utils;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.MenuBotones;

import java.util.ArrayList;

public class MenuBotonesAdapter extends RecyclerView.Adapter<MenuBotonesAdapter.MenuBotonesAdapterVH>
    implements View.OnClickListener {

    private ArrayList<MenuBotones> datos;
    private View.OnClickListener listener;

    public MenuBotonesAdapter (ArrayList<MenuBotones> datos) {
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
    public MenuBotonesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.boton_menu_rv, parent, false);
        v.setOnClickListener(this);
        MenuBotonesAdapter.MenuBotonesAdapterVH vh = new MenuBotonesAdapter.MenuBotonesAdapterVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuBotonesAdapterVH holder, int position) {
        holder.bindMenu(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class MenuBotonesAdapterVH extends RecyclerView.ViewHolder {

        private Button btnMenu;
        private ImageView imgMenu;

        public MenuBotonesAdapterVH(@NonNull View itemView) {
            super(itemView);
            btnMenu = itemView.findViewById(R.id.btnMenu);
            imgMenu = itemView.findViewById(R.id.imgMenu);
        }

        public void bindMenu (MenuBotones menu) {
            btnMenu.setText(menu.getNombreDepartamento());
            btnMenu.setBackground(itemView.getResources().getDrawable(menu.getColorFondo()));
            imgMenu.setImageResource(menu.getImagen());
        }


    }
}
