package com.dam.peluqueriacanina.notificacion;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class Recordatorio extends Worker {

    public Recordatorio(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void guardarNoti(Long duracion, Data data,String tag) {
        OneTimeWorkRequest noti = new OneTimeWorkRequest.Builder(Recordatorio.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();

        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(noti);
    }

    @NonNull
    @Override
    public Result doWork() {

        String titulo = getInputData().getString("titulo");
        String detalle = getInputData().getString("detalle");
        int id = (int) getInputData().getLong("idNoti",0);


        return Result.success();
    }
}
