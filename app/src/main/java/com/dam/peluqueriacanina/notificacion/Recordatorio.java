package com.dam.peluqueriacanina.notificacion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.VeterinariaActivity;
import com.dam.peluqueriacanina.fragmentosVet.CitasAnimalFragmentVet;


public class Recordatorio extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, VeterinariaActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int valorEntero = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context,valorEntero,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"hola")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hey estas ahi?, tienes una cita")
                .setContentText("Recuerda que tienes una cita en tu centro veterinario")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

    }
}
