package com.dam.peluqueriacanina;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.peluqueriacanina.model.DatosTienda;
import com.dam.peluqueriacanina.model.PreguntaRespuesta;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PushearProductos2 extends AppCompatActivity {

    String key;
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = fdb.getReference();
    ArrayList<DatosTienda> listaTienda = new ArrayList<>();
    ArrayList<PreguntaRespuesta> listaa = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushear_productos2);

        // subirProductos(); a

        aaaaaa();


    }

    //a
    private void aaaaaa() {
        listaa.add(new PreguntaRespuesta("¿La furgoneta de lavado recoge y entrega la mascota en casa?", "Así es, elige dia y hora y nosotros te la recogemos y entregamos totalmente limpia."));
        listaa.add(new PreguntaRespuesta("¿Puedo ver en cada momento donde se encuentra mi mascota??", "Sí, la aplicación permite ver en tiempo real donde está tu mascota."));
        listaa.add(new PreguntaRespuesta("¿Que centros veterinarios puedo elegir con la aplicación?", "De momento, todos los de la Comunidad de Madrid, actualizados al día."));
        listaa.add(new PreguntaRespuesta("¿Puedo registrar más de una mascota?", "En efecto, tantas como tengas."));
        listaa.add(new PreguntaRespuesta("¿Cuesta dinero usar la aplicación?", "No, la aplicación es totalmente gratuita."));
        listaa.add(new PreguntaRespuesta("¿Que puedo comprar en la tienda?", "Desde alimentos, juguetes hasta accesorios para cualquier tipo de mascota."));
        listaa.add(new PreguntaRespuesta("¿El chat es en tiempo real?", "Así es, es en tiempo real y directo con la veterinaria o el centro de lavado."));
        listaa.add(new PreguntaRespuesta("¿Puedo registrar más de una cuenta?", "Sólo una por email y teléfono movil."));
        listaa.add(new PreguntaRespuesta("¿Puedo cancelar una cita una vez ya está hecha?", "Sí, puedes cancelar citas en cualquier momento."));
        listaa.add(new PreguntaRespuesta("¿Cómo ver las citas activas?", "En el apartado citas, dentro de lavado o veterinario según sea el caso, puedes ver el centro veterinario dia y hora de la cita elegida."));


        HashMap<String, Object> usuario = new HashMap<>();
        for (int i = 0; i < listaa.size(); i++) {
            key = dbRef.push().getKey();
            usuario.put("titulo", listaa.get(i).getTitulo());
            usuario.put("respuesta", listaa.get(i).getRespuesta());
            dbRef.child("preguntasRespuestas").child(key).setValue(usuario);
        }
    }

    private void subirProductos() {
        /*listaTienda.add(new DatosTienda("pienso perro razas pequeño", "1kg", getBaseContext().getDrawable(R.drawable.pienso_perro_pequeno), "pienso natural especialmente recomendado para perros de razas pequeñas (1-10 kg peso adulto), fabricado en España con ingredientes naturales. ", "alimentacion", "20"));
        listaTienda.add(new DatosTienda("transportin perro y gato", "1", getBaseContext().getDrawable(R.drawable.transportin), "El bolso de transporte para perros y gatos Kibo Slide naranja tiene un diseño moderno y deportivo, en colores naranja, negro y gris, para que puedas viajar o pasear con tu mascota", "accesorios", "35"));
        listaTienda.add(new DatosTienda("Bozal perro ajuste perfecto", "1", getBaseContext().getDrawable(R.drawable.bozal), "Bozal ajustable tiene la ventaja de adaptarse perfectamente a todos los hocicos largos o medianos.", "accesorios", "6"));
        listaTienda.add(new DatosTienda("Correa para perros", "1", getBaseContext().getDrawable(R.drawable.correa), "Correa muy comoda de usar para pasear a tu perro", "accesorios", "9"));
        listaTienda.add(new DatosTienda("Pelota de tenis para perros ", "1", getBaseContext().getDrawable(R.drawable.pelota_perro), "Clasica pelota de tenis canina, forrada por un material resistente y una tela menos abrasiva de lo habitual para que se conserven intactos s", "juguetes", "8"));
        listaTienda.add(new DatosTienda("Pienso perro razas medianas", "1kg", getBaseContext().getDrawable(R.drawable.pienso_perro_mediano), "pienso natural especialmente recomendado para perros de razas medianas (10-25 kg peso adulto), fabricado en España con ingredientes naturales. ", "alimentacion", "20"));
        listaTienda.add(new DatosTienda("Pienso perro razas grandes", "1kg", getBaseContext().getDrawable(R.drawable.pienso_perro_grandes), "pienso natural especialmente recomendado para perros de razas grandes (>25 kg peso adulto), fabricado en España con ingredientes naturales. ", "alimentacion", "20"));
        listaTienda.add(new DatosTienda("Pienso para gatos esterilizados", "1kg", getBaseContext().getDrawable(R.drawable.pienso_gato_esteril), "Pienso para gatos esterilizados ", "alimentacion", "15"));
        listaTienda.add(new DatosTienda("Pienso para gatos adultos", "1kg", getBaseContext().getDrawable(R.drawable.pienso_gato_adulto), "Delicioso y completo pienso para gatos adultos con sabor a pollo", "alimentacion", "15"));
        listaTienda.add(new DatosTienda("Rascador para gatos", "1", getBaseContext().getDrawable(R.drawable.rascador), " ofrece a tu mascota largas horas de diversión  a la vez que te ayuda a proteger los muebles y tapicerías de la casa.", "accesorios", "10"));
        listaTienda.add(new DatosTienda("Raton con muelle para Gatos", "1", getBaseContext().getDrawable(R.drawable.juego_gato), "no es sólo un entretenimiento sino que el juego les hace dar salida a su instinto y les hace estar más felices y menos estresados.", "juguetes", "7"));
        listaTienda.add(new DatosTienda("Cama para perros y gatos ", "1", getBaseContext().getDrawable(R.drawable.cama), "tipo colchoneta desenfundable roja está pensada y diseñada para asegurar un descanso de calidad a tu mascota", "accesorios", "14"));
        listaTienda.add(new DatosTienda("Arenero de plastico para gatos", "1", getBaseContext().getDrawable(R.drawable.arenero), "Este arenero es genial para que tus mascotas disfruten de intimidad cuando tienen que hacer sus intimidades en casa.", "accesorios", "50"));
        listaTienda.add(new DatosTienda("Arena para gatos olor Lavanda", "3kg", getBaseContext().getDrawable(R.drawable.arena), "El momento de limpiar la bandeja de tu gato puede llegar a ser muy desagradable, gracias a esta arena de lavanda, el proceso será mucho más simple", "accesorios", "7"));
        listaTienda.add(new DatosTienda("Tratamiento Antipulgas para perros y gatos", "1", getBaseContext().getDrawable(R.drawable.antipulgas), "Antiparasitario en comprimidos que actúa en casos de infestación por pulgas ", "accesorios", "11"));
        listaTienda.add(new DatosTienda("Alimento para canarios y exoticos", "1kg", getBaseContext().getDrawable(R.drawable.pienso_pajaros), "granulados con una composición científicamente probada a base de cereales seleccionados, fruta fresca y un 50 % de alpiste.", "alimentacion", "15"));
        listaTienda.add(new DatosTienda("Jaula para pajaros redonda pequeña", "1", getBaseContext().getDrawable(R.drawable.jaula_pajaro), "Bonita jaula especialmente diseñada para el alojamiento de todo tipo de pajaros como exoticos, tropicales, canarios, periquitos, agapornis, ninfas, etc.", "accesorios", "20"));
        listaTienda.add(new DatosTienda("Alimento para tortugas", "300mg", getBaseContext().getDrawable(R.drawable.pienso_tortuga), "Promueve un crecimiento sano, mejora el metabolismo energético y refuerza el sistema inmunitario.", "alimentacion", "5"));
        listaTienda.add(new DatosTienda("Tortuguera con palmera", "1", getBaseContext().getDrawable(R.drawable.tortuguera), "Tu tortuga encontrará un lugar seco donde descansar y alimentarse. Posee un compartimento donde la comida (seca o húmeda) se conserva y no ensucia el agua.", "accesorios", "6"));
        listaTienda.add(new DatosTienda("Pienso para roedores", "1kg", getBaseContext().getDrawable(R.drawable.pienso_roedor), "Es un alimento complementario indicado para roedores que previene el aburrimiento y enriquece su dieta diaria. ", "alimentacion", "5"));
        listaTienda.add(new DatosTienda("Jaula para roedores", "1", getBaseContext().getDrawable(R.drawable.jaula_roedor), "Cuenta con un diseño especial de dos plantas que permite separar el serrin, de esta forma los roedores podran disfrutar de dos zonas diferentes segun lo que les apetezca en cada momento.", "accesorios", "50"));
        listaTienda.add(new DatosTienda("Alimento para peces", "4kg", getBaseContext().getDrawable(R.drawable.pienso_peces), "Alimento completo para peces de fondo con una formula unica a base de insectos, enriquecida con multiples proteinas y carbohidratos de primera calidad", "alimentacion", "10"));
        listaTienda.add(new DatosTienda("Acuario de cristal", "1", getBaseContext().getDrawable(R.drawable.acuario), "Elaborado con materiales de calidad, que lo convierten en un artículo de alta gama para este tipo de mascotas.", "accesorios", "110"));
        listaTienda.add(new DatosTienda("Hormiguero", "1", getBaseContext().getDrawable(R.drawable.hormiguero), "Hormiguero de acrilico pequeño con zona de forrajeo de gran altura con tapa. Perfecto para las colonias de especies de gran tamaño.", "accesorios", "40"));
        listaTienda.add(new DatosTienda("Suplemento alimenticio para caballos", "10kg", getBaseContext().getDrawable(R.drawable.pienso_caballo), "suplemento nutricional desarrollado para aumentar la masa muscular en los caballos de competición o de trabajo forzado, también para los preparativos para apareamiento. ", "alimentacion", "60"));
*/

        HashMap<String, Object> usuario = new HashMap<>();

        for (int i = 0; i < listaTienda.size(); i++) {
            key = dbRef.push().getKey();
            usuario.put("nombre", listaTienda.get(i).getNombre());
            usuario.put("cantidad", listaTienda.get(i).getCantidad());
            usuario.put("foto", listaTienda.get(i).getFoto());
            usuario.put("detalle", listaTienda.get(i).getDetalle());
            usuario.put("tipo", listaTienda.get(i).getTipo());
            dbRef.child("productos tienda").child(key).setValue(usuario);
        }
    }

}