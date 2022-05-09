package com.dam.peluqueriacanina.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.peluqueriacanina.dao.TusCitasDao;
import com.dam.peluqueriacanina.entity.TusCitas;

@Database(entities = {TusCitas.class},version = 1)
public abstract class TusCitasDB extends RoomDatabase {
    public abstract TusCitasDao citaDao();

    private static TusCitasDB CITAS_DB = null;

    public static TusCitasDB getDatabase(Context context) {
        if (CITAS_DB == null) {
            CITAS_DB = Room.databaseBuilder(
                            context.getApplicationContext(), TusCitasDB.class, "tusCitas-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return CITAS_DB;
    }
}
