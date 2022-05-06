package com.dam.peluqueriacanina.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.peluqueriacanina.dao.CitasDao;
import com.dam.peluqueriacanina.entity.Cita;

@Database(entities = {Cita.class},version = 1)
public abstract class CitasDB extends RoomDatabase {
    public abstract CitasDao citaDao();

    private static CitasDB CITAS_DB = null;

    public static CitasDB getDatabase(Context context) {
        if (CITAS_DB == null) {
            CITAS_DB = Room.databaseBuilder(
                    context.getApplicationContext(), CitasDB.class, "cita-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return CITAS_DB;
    }
}
