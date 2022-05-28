package com.dam.peluqueriacanina.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.dao.TusCitasVetDao;
import com.dam.peluqueriacanina.entity.TusCitas;

@Database(entities = {TusCitas.class},version = 3)
public abstract class TusCitasVetDB extends RoomDatabase {
    public abstract TusCitasVetDao citaDao();

    private static TusCitasVetDB CITAS_VET_DB = null;

    public static TusCitasVetDB getDatabase(Context context) {
        if (CITAS_VET_DB == null) {
            CITAS_VET_DB = Room.databaseBuilder(
                            context.getApplicationContext(), TusCitasVetDB.class, "tusCitasVet-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return CITAS_VET_DB;
    }
}
