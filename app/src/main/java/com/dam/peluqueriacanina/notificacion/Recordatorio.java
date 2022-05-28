package com.dam.peluqueriacanina.notificacion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dam.peluqueriacanina.R;


public class Recordatorio extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

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
