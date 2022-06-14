package com.dam.peluqueriacanina.model.datos;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.DatosTienda;

import java.util.ArrayList;

public class ProductosTienda {

    private final ArrayList<DatosTienda> listaTienda;


    public ProductosTienda() {
        listaTienda = new ArrayList<>();

        listaTienda.add(new DatosTienda("pienso perro razas pequeño", "1kg", R.drawable.pienso_perro_pequeno, "pienso natural especialmente recomendado para perros de razas pequeñas (1-10 kg peso adulto), fabricado en España con ingredientes naturales. ", "alimentacion", "20"));
        listaTienda.add(new DatosTienda("transportin perro y gato", "1", R.drawable.transportin, "El bolso de transporte para perros y gatos Kibo Slide naranja tiene un diseño moderno y deportivo, en colores naranja, negro y gris, para que puedas viajar o pasear con tu mascota", "accesorios", "35"));
        listaTienda.add(new DatosTienda("Bozal perro ajuste perfecto", "1", R.drawable.bozal, "Bozal ajustable tiene la ventaja de adaptarse perfectamente a todos los hocicos largos o medianos.", "accesorios", "6"));
        listaTienda.add(new DatosTienda("Correa para perros", "1", R.drawable.correa, "Correa muy comoda de usar para pasear a tu perro", "accesorios", "9"));
        listaTienda.add(new DatosTienda("Pelota de tenis para perros ", "1", R.drawable.pelota_perro, "Clasica pelota de tenis canina, forrada por un material resistente y una tela menos abrasiva de lo habitual para que se conserven intactos s", "juguetes", "8"));
        listaTienda.add(new DatosTienda("Pienso perro razas medianas", "1kg", R.drawable.pienso_perro_mediano, "pienso natural especialmente recomendado para perros de razas medianas (10-25 kg peso adulto), fabricado en España con ingredientes naturales. ", "alimentacion", "20"));
        listaTienda.add(new DatosTienda("Pienso perro razas grandes", "1kg", R.drawable.pienso_perro_grandes, "pienso natural especialmente recomendado para perros de razas grandes (>25 kg peso adulto), fabricado en España con ingredientes naturales. ", "alimentacion", "20"));
        listaTienda.add(new DatosTienda("Pienso para gatos esterilizados", "1kg", R.drawable.pienso_gato_esteril, "Pienso para gatos esterilizados ", "alimentacion", "15"));
        listaTienda.add(new DatosTienda("Pienso para gatos adultos", "1kg", R.drawable.pienso_gato_adulto, "Delicioso y completo pienso para gatos adultos con sabor a pollo", "alimentacion", "15"));
        listaTienda.add(new DatosTienda("Rascador para gatos", "1", R.drawable.rascador, " ofrece a tu mascota largas horas de diversión  a la vez que te ayuda a proteger los muebles y tapicerías de la casa.", "accesorios", "10"));
        listaTienda.add(new DatosTienda("Raton con muelle para Gatos", "1", R.drawable.juego_gato, "no es sólo un entretenimiento sino que el juego les hace dar salida a su instinto y les hace estar más felices y menos estresados.", "juguetes", "7"));
        listaTienda.add(new DatosTienda("Cama para perros y gatos ", "1", R.drawable.cama, "tipo colchoneta desenfundable roja está pensada y diseñada para asegurar un descanso de calidad a tu mascota", "accesorios", "14"));
        listaTienda.add(new DatosTienda("Arenero de plastico para gatos", "1", R.drawable.arenero, "Este arenero es genial para que tus mascotas disfruten de intimidad cuando tienen que hacer sus intimidades en casa.", "accesorios", "50"));
        listaTienda.add(new DatosTienda("Arena para gatos olor Lavanda", "3kg", R.drawable.arena, "El momento de limpiar la bandeja de tu gato puede llegar a ser muy desagradable, gracias a esta arena de lavanda, el proceso será mucho más simple", "accesorios", "7"));
        listaTienda.add(new DatosTienda("Tratamiento Antipulgas para perros y gatos", "1", R.drawable.antipulgas, "Antiparasitario en comprimidos que actúa en casos de infestación por pulgas ", "accesorios", "11"));
        listaTienda.add(new DatosTienda("Alimento para canarios y exoticos", "1kg", R.drawable.pienso_pajaros, "granulados con una composición científicamente probada a base de cereales seleccionados, fruta fresca y un 50 % de alpiste.", "alimentacion", "15"));
        listaTienda.add(new DatosTienda("Jaula para pajaros redonda pequeña", "1", R.drawable.jaula_pajaro, "Bonita jaula especialmente diseñada para el alojamiento de todo tipo de pajaros como exoticos, tropicales, canarios, periquitos, agapornis, ninfas, etc.", "accesorios", "20"));
        listaTienda.add(new DatosTienda("Alimento para tortugas", "300mg", R.drawable.pienso_tortuga, "Promueve un crecimiento sano, mejora el metabolismo energético y refuerza el sistema inmunitario.", "alimentacion", "5"));
        listaTienda.add(new DatosTienda("Tortuguera con palmera", "1", R.drawable.tortuguera, "Tu tortuga encontrará un lugar seco donde descansar y alimentarse. Posee un compartimento donde la comida (seca o húmeda) se conserva y no ensucia el agua.", "accesorios", "6"));
        listaTienda.add(new DatosTienda("Pienso para roedores", "1kg", R.drawable.pienso_roedor, "Es un alimento complementario indicado para roedores que previene el aburrimiento y enriquece su dieta diaria. ", "alimentacion", "5"));
        listaTienda.add(new DatosTienda("Jaula para roedores", "1", R.drawable.jaula_roedor, "Cuenta con un diseño especial de dos plantas que permite separar el serrin, de esta forma los roedores podran disfrutar de dos zonas diferentes segun lo que les apetezca en cada momento.", "accesorios", "50"));
        listaTienda.add(new DatosTienda("Alimento para peces", "4kg", R.drawable.pienso_peces, "Alimento completo para peces de fondo con una formula unica a base de insectos, enriquecida con multiples proteinas y carbohidratos de primera calidad", "alimentacion", "10"));
        listaTienda.add(new DatosTienda("Acuario de cristal", "1", R.drawable.acuario, "Elaborado con materiales de calidad, que lo convierten en un artículo de alta gama para este tipo de mascotas.", "accesorios", "110"));
        listaTienda.add(new DatosTienda("Hormiguero", "1", R.drawable.hormiguero, "Hormiguero de acrilico pequeño con zona de forrajeo de gran altura con tapa. Perfecto para las colonias de especies de gran tamaño.", "accesorios", "40"));
        listaTienda.add(new DatosTienda("Suplemento alimenticio para caballos", "10kg", R.drawable.pienso_caballo, "suplemento nutricional desarrollado para aumentar la masa muscular en los caballos de competición o de trabajo forzado, también para los preparativos para apareamiento. ", "alimentacion", "60"));

    }

    public ArrayList<DatosTienda> getListaTienda() {
        return listaTienda;
    }
}
