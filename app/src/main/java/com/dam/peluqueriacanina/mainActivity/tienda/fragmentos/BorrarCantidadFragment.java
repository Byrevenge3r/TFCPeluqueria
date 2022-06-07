package com.dam.peluqueriacanina.mainActivity.tienda.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.comunicacion.ComunicacionProductos;
import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.db.CestaDB;
import com.dam.peluqueriacanina.entity.Cesta;

public class BorrarCantidadFragment extends DialogFragment implements View.OnClickListener {

    Cesta cesta;
    TextView tvCantidad;
    ImageButton imgMas,imgMenos;
    Button btnAceptarCambios;
    int contador;
    CestaDao dao;
    CestaDB db;
    ComunicacionProductos listener;
    public BorrarCantidadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_borrar_cantidad, null);
        builder.setView(v);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                cesta = bundle.getParcelable("producto");
                contador = cesta.getCantidad();
                db = CestaDB.getDatabase(v.getContext());
                dao = db.cestaDao();

                tvCantidad = v.findViewById(R.id.tv_cantidad_eliminar_producto);

                tvCantidad.setText(String.valueOf(cesta.getCantidad()));

                imgMas = v.findViewById(R.id.imagen_add_tienda_eliminar);
                imgMenos = v.findViewById(R.id.imagen_remove_tienda_eliminar);
                btnAceptarCambios = v.findViewById(R.id.btnAceptarCambios);

                imgMas.setOnClickListener(BorrarCantidadFragment.this);
                imgMenos.setOnClickListener(BorrarCantidadFragment.this);
                btnAceptarCambios.setOnClickListener(BorrarCantidadFragment.this);
            }
        });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_borrar_cantidad, container, false);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(imgMas) && cesta.getCantidad() + 1 <= 99) {
            tvCantidad.setText(String.valueOf(cesta.getCantidad() + 1));
            cesta.setCantidad(cesta.getCantidad()+1);
            dao.update(cesta);
        } else if (v.equals(imgMenos) && cesta.getCantidad() - 1 >= 0) {
            tvCantidad.setText(String.valueOf(cesta.getCantidad() - 1));
            cesta.setCantidad(cesta.getCantidad()-1);
            dao.update(cesta);
        } else if (v.equals(btnAceptarCambios)) {
            if (cesta.getCantidad() == 0) {
                dao.delete(cesta);
                listener.comunicacion(null);
            } else {
                listener.comunicacion(cesta);
            }
            dismiss();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ComunicacionProductos) {
            listener = (ComunicacionProductos) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnComunicationFragmentListener");
        }
    }
}