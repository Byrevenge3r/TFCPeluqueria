package com.dam.peluqueriacanina.mainActivity.tienda;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.DatosTienda;
import com.dam.peluqueriacanina.utils.AdapterTienda;

import java.util.ArrayList;
import java.util.Locale;

public class TiendaActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearTienda, linearProducto;
    ImageView imageProductoTienda;
    TextView  tvObjetoNombreTienda;
    EditText etBuscarPorNombre;

    ImageView ivCarrito;

    LinearLayout llmAlimentacion, llmAccesorios,llmJuguetes;

    ArrayList<DatosTienda> listaTienda = new ArrayList<>();
    ArrayList<DatosTienda> listaTiendaFiltrada = new ArrayList<>();

    RecyclerView rv;
    AdapterTienda adapter;
    CardView cvAlimentacion, cvAccesorios, cvJuguetes;

    DatosTienda tienda;
    //Tienda
    int[] imageTienda = {};
    String[] nombreObjetoTienda = {};

    //Producto
    int[] imagenProducto = {};
    String[] nombreObjetoProducto = {};
    String[] pesoPiensos = {};
    String[] precioFinalProducto = {};
    String[] precioProducto = {};

    boolean colorAlimentacion, colorAccesorios, colorJuguetes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        linearTienda = findViewById(R.id.llTienda);
        linearProducto = findViewById(R.id.llProducto);
        rv = findViewById(R.id.rvMostrarProductos);

        ivCarrito = findViewById(R.id.imagen_compra_tienda_detalles);

        llmAlimentacion = findViewById(R.id.llAlimentacion);
        llmJuguetes = findViewById(R.id.llJuguetes);
        llmAccesorios = findViewById(R.id.llAccesorios);

        cvAccesorios = findViewById(R.id.cvAccesorios);
        cvJuguetes = findViewById(R.id.cvJuguetes);
        cvAlimentacion = findViewById(R.id.cvAlimentacion);

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        /*listaTienda.add(new DatosTienda("Pienso perros","adasd","alimentacion","pienso de perros puta madre"));
        listaTienda.add(new DatosTienda("Pienso gato","adasd","alimentacion","pienso de perros puta madre"));
        listaTienda.add(new DatosTienda("juguete gato","adasd","accesorio","accesorio de gato puta madre"));
        listaTienda.add(new DatosTienda("juguete perro","adasd","accesorio","accesorio de perros puta madre"));
        listaTienda.add(new DatosTienda("yo que se","adasd","alimentacion","pienso de perros puta madre"));
        listaTienda.add(new DatosTienda("yo que se","adasd","accesorio","pienso de perros puta madre"));
*/
        listaTienda.add(new DatosTienda("pienso perro razas pequeño", "1kg", "drawable/pienso_perro_pequeno.png", "pienso natural especialmente recomendado para perros de razas pequeñas (1-10 kg peso adulto), fabricado en España con ingredientes naturales. ","alimentacion" , "20"));
        listaTienda.add(new DatosTienda("transportin perro y gato", "1", "drawable/transportin.png", "El bolso de transporte para perros y gatos Kibo Slide naranja tiene un diseño moderno y deportivo, en colores naranja, negro y gris, para que puedas viajar o pasear con tu mascota","accesorios", "35"));
        listaTienda.add(new DatosTienda("Bozal perro ajuste perfecto", "1", "drawable/bozal.png", "Bozal ajustable tiene la ventaja de adaptarse perfectamente a todos los hocicos largos o medianos.","accesorios", "6"));
        listaTienda.add(new DatosTienda("Correa para perros", "1", "drawable/correa", "Correa muy comoda de usar para pasear a tu perro","accesorios", "9"));
        listaTienda.add(new DatosTienda("Pelota de tenis para perros ", "1", "drawable/pelota_perro.png", "Clasica pelota de tenis canina, forrada por un material resistente y una tela menos abrasiva de lo habitual para que se conserven intactos s","juguetes", "8"));
        listaTienda.add(new DatosTienda("Pienso perro razas medianas", "1kg", "drawable/pienso_perro_mediano.png", "pienso natural especialmente recomendado para perros de razas medianas (10-25 kg peso adulto), fabricado en España con ingredientes naturales. ","alimentacion", "20"));
        listaTienda.add(new DatosTienda("Pienso perro razas grandes", "1kg", "drawable/pienso_perro_grandes.png", "pienso natural especialmente recomendado para perros de razas grandes (>25 kg peso adulto), fabricado en España con ingredientes naturales. ","alimentacion", "20"));
        listaTienda.add(new DatosTienda("Pienso para gatos esterilizados", "1kg", "drawable/pienso_gato_esteril.png", "Pienso para gatos esterilizados ","alimentacion", "15"));
        listaTienda.add(new DatosTienda("Pienso para gatos adultos", "1kg", "drawable/pienso_gato_adulto.png", "Delicioso y completo pienso para gatos adultos con sabor a pollo","alimentacion", "15"));
        listaTienda.add(new DatosTienda("Rascador para gatos", "1", "drawable/rascador.png", " ofrece a tu mascota largas horas de diversión  a la vez que te ayuda a proteger los muebles y tapicerías de la casa.","accesorios", "10"));
        listaTienda.add(new DatosTienda("Raton con muelle para Gatos", "1", "drawable/juego_gato.png", "no es sólo un entretenimiento sino que el juego les hace dar salida a su instinto y les hace estar más felices y menos estresados.","juguetes","7"));
        listaTienda.add(new DatosTienda("Cama para perros y gatos ", "1", "drawable/cama.png", "tipo colchoneta desenfundable roja está pensada y diseñada para asegurar un descanso de calidad a tu mascota","accesorios", "14"));
        listaTienda.add(new DatosTienda("Arenero de plastico para gatos", "1", "drawable/arenero.png", "Este arenero es genial para que tus mascotas disfruten de intimidad cuando tienen que hacer sus intimidades en casa.","accesorios", "50"));
        listaTienda.add(new DatosTienda("Arena para gatos olor Lavanda", "3kg", "drawable/arena.png", "El momento de limpiar la bandeja de tu gato puede llegar a ser muy desagradable, gracias a esta arena de lavanda, el proceso será mucho más simple","accesorios", "7"));
        listaTienda.add(new DatosTienda("Tratamiento Antipulgas para perros y gatos", "1", "drawable/antipulgas.png", "Antiparasitario en comprimidos que actúa en casos de infestación por pulgas ","accesorios","11"));
        listaTienda.add(new DatosTienda("Alimento para canarios y exoticos", "1kg", "drawable/pienso_pajaros.png", "granulados con una composición científicamente probada a base de cereales seleccionados, fruta fresca y un 50 % de alpiste.","alimentacion", "15"));
        listaTienda.add(new DatosTienda("Jaula para pajaros redonda pequeña", "1", "drawable/jaula_pajaro.png", "Bonita jaula especialmente diseñada para el alojamiento de todo tipo de pajaros como exoticos, tropicales, canarios, periquitos, agapornis, ninfas, etc.","accesorios", "20"));
        listaTienda.add(new DatosTienda("Alimento para tortugas", "300mg", "drawable/pienso_tortuga.png", "Promueve un crecimiento sano, mejora el metabolismo energético y refuerza el sistema inmunitario.","alimentacion", "5"));
        listaTienda.add(new DatosTienda("Tortuguera con palmera", "1", "drawable/tortuguera.png", "Tu tortuga encontrará un lugar seco donde descansar y alimentarse. Posee un compartimento donde la comida (seca o húmeda) se conserva y no ensucia el agua.","accesorios", "6"));
        listaTienda.add(new DatosTienda("Pienso para roedores", "1kg", "drawable/pienso_roedor.png", "Es un alimento complementario indicado para roedores que previene el aburrimiento y enriquece su dieta diaria. ","alimentacion", "5"));
        listaTienda.add(new DatosTienda("Jaula para roedores", "1", "drawable/jaula_roedor.png", "Cuenta con un diseño especial de dos plantas que permite separar el serrin, de esta forma los roedores podran disfrutar de dos zonas diferentes segun lo que les apetezca en cada momento.","accesorios", "50"));
        listaTienda.add(new DatosTienda("Alimento para peces", "4kg", "drawable/pienso_peces.png", "Alimento completo para peces de fondo con una formula unica a base de insectos, enriquecida con multiples proteinas y carbohidratos de primera calidad","alimentacion", "10"));
        listaTienda.add(new DatosTienda("Acuario de cristal", "1", "drawable/acuario.png", "Elaborado con materiales de calidad, que lo convierten en un artículo de alta gama para este tipo de mascotas.","accesorios", "110"));
        listaTienda.add(new DatosTienda("Hormiguero", "1", "drawable/hormiguero", "Hormiguero de acrilico pequeño con zona de forrajeo de gran altura con tapa. Perfecto para las colonias de especies de gran tamaño.","accesorios", "40"));
        listaTienda.add(new DatosTienda("Suplemento alimenticio para caballos", "10kg", "drawable/pienso_caballo.png", "suplemento nutricional desarrollado para aumentar la masa muscular en los caballos de competición o de trabajo forzado, también para los preparativos para apareamiento. ","alimentacion", "60"));


        etBuscarPorNombre = findViewById(R.id.edit_text_buscar);
        adapter = new AdapterTienda(listaTienda);
        rv.setAdapter(adapter);
        cargarTienda();
        cargarProducto();

        cvAlimentacion.setOnClickListener(this);
        cvJuguetes.setOnClickListener(this);
        cvAccesorios.setOnClickListener(this);
        ivCarrito.setOnClickListener(this);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listaTiendaFiltrada.isEmpty()) {
                    tienda = listaTiendaFiltrada.get(rv.getChildAdapterPosition(v));
                } else {
                    tienda = listaTienda.get(rv.getChildAdapterPosition(v));
                }

                Intent i = new Intent(TiendaActivity.this, TiendaDetallesActivity.class);
                i.putExtra("tienda", tienda);
                startActivity(i);
            }
        });

        etBuscarPorNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listaTiendaFiltrada.clear();
                if (colorAlimentacion) {
                    cargarDatosConFiltro("alimentacion");
                } else if (colorAccesorios) {
                    cargarDatosConFiltro("accesorios");
                } else if (colorJuguetes) {
                    cargarDatosConFiltro("juguetes");
                } else {
                    for (int i = 0; i < listaTienda.size();i++) {
                        if (listaTienda.get(i).getNombre().toLowerCase(Locale.ROOT).contains(etBuscarPorNombre.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                            listaTiendaFiltrada.add(listaTienda.get(i));
                        }
                    }

                    adapter.setDatos(listaTiendaFiltrada);
                    rv.setAdapter(adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    //Esto no tengo npi de idea de lo que hace pero lo voy a quitar por que no sirve
    public void cargarProducto(){
        LayoutInflater inflater = LayoutInflater.from(this);
        int i;
        for(i=0;i<imagenProducto.length;i++){
            View view = inflater.inflate(R.layout.objeto_producto ,linearProducto, false);
            ImageView imageProduct = view.findViewById(R.id.image_producto_tienda);
            TextView tvNombre = view.findViewById(R.id.tv_nombre_producto);
            TextView tvpienso = view.findViewById(R.id.tv_pienso_producto);
            TextView tvPrecioFinal = view.findViewById(R.id.tv_precion_final_producto);
            TextView tvPrecio = view.findViewById(R.id.tv_precio_producto);
            imageProduct.setImageResource(imagenProducto[i]);
            tvNombre.setText(nombreObjetoProducto[i]);
            tvpienso.setText(pesoPiensos[i]);
            tvPrecioFinal.setText(precioFinalProducto[i]);
            tvPrecio.setText(precioProducto[i]);

            linearProducto.addView(view);
        }
    }

    public void cargarTienda(){
        LayoutInflater inflater = LayoutInflater.from(this);
        int i;
        for(i=0;i<imageTienda.length;i++){
            View view = inflater.inflate(R.layout.objetos_tienda,linearTienda, false);
            imageProductoTienda = view.findViewById(R.id.image_producto_tienda);
            tvObjetoNombreTienda = view.findViewById(R.id.tv_objeto_nombre_tienda);
            imageProductoTienda.setImageResource(imageTienda[i]);
            tvObjetoNombreTienda.setText(nombreObjetoTienda[i]);
            final int aux = 1;
            imageProductoTienda.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String objetoSeleccionado = nombreObjetoTienda[aux];
                    Toast.makeText(getApplicationContext(), "Objeto "+objetoSeleccionado,Toast.LENGTH_LONG).show();
                    Intent tienda = new Intent(getApplicationContext(), Tienda2Activity.class);
                    startActivity(tienda);
                }
            });

            linearTienda.addView(view);
        }
    }

    //REFORMAT MAÑANA
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (v.equals(cvAccesorios)) {
            if (!colorAccesorios) {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.color_gris));
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.white));
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.white));
                colorAccesorios = true;
                colorAlimentacion = false;
                colorJuguetes = false;
                cargarDatosConFiltro("accesorios");
            } else {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.white));
                colorAccesorios = false;
                listaTiendaFiltrada.clear();
                adapter.setDatos(listaTienda);
                rv.setAdapter(adapter);
            }
        } else if (v.equals(cvAlimentacion)) {
            if (!colorAlimentacion) {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.white));
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.white));
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.color_gris));
                colorAlimentacion = true;
                colorAccesorios = false;
                colorJuguetes = false;
                cargarDatosConFiltro("alimentacion");
            } else {
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.white));
                colorAlimentacion = false;
                listaTiendaFiltrada.clear();
                adapter.setDatos(listaTienda);
                rv.setAdapter(adapter);
            }
        } else if (v.equals(cvJuguetes)) {
            if (!colorJuguetes) {
                llmAccesorios.setBackgroundColor(getResources().getColor(R.color.white));
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.color_gris));
                llmAlimentacion.setBackgroundColor(getResources().getColor(R.color.white));
                colorJuguetes = true;
                colorAccesorios = false;
                colorAlimentacion = false;
                cargarDatosConFiltro("juguetes");
            } else {
                llmJuguetes.setBackgroundColor(getResources().getColor(R.color.white));
                colorJuguetes = false;
                listaTiendaFiltrada.clear();
                adapter.setDatos(listaTienda);
                rv.setAdapter(adapter);
            }
        } else if (v.equals(ivCarrito)) {
            Intent i = new Intent(this,MostrarCompraActivity.class);
            startActivity(i);
        }
    }
    private void cargarDatosConFiltro(String tipo) {
        listaTiendaFiltrada.clear();
        if (!tipo.isEmpty() && !etBuscarPorNombre.getText().toString().isEmpty()) {
            for (int i = 0; i < listaTienda.size(); i++) {
                if (listaTienda.get(i).getNombre().toLowerCase(Locale.ROOT).contains(etBuscarPorNombre.getText().toString().trim().toLowerCase(Locale.ROOT))
                        && listaTienda.get(i).getTipo().toLowerCase(Locale.ROOT).equals(tipo.toLowerCase(Locale.ROOT))) {

                    listaTiendaFiltrada.add(listaTienda.get(i));
                }
            }

            adapter.setDatos(listaTiendaFiltrada);
            rv.setAdapter(adapter);
        }

        if (etBuscarPorNombre.getText().toString().isEmpty()) {
            for (int i = 0; i < listaTienda.size(); i++) {
                if (listaTienda.get(i).getTipo().equals(tipo)) {
                    listaTiendaFiltrada.add(listaTienda.get(i));
                }
            }
            adapter.setDatos(listaTiendaFiltrada);
            rv.setAdapter(adapter);
        }
    }
}