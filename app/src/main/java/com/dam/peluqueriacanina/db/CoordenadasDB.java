package com.dam.peluqueriacanina.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.peluqueriacanina.dao.CoordenadasDao;
import com.dam.peluqueriacanina.entity.Coordenadas;

@Database(entities = {Coordenadas.class},version = 3)
public abstract class CoordenadasDB extends RoomDatabase {
    public abstract CoordenadasDao coordenadasDao();

    private static CoordenadasDB COORDENADAS_DB = null;

    public static CoordenadasDB getDatabase(Context context) {
        if (COORDENADAS_DB == null) {
            COORDENADAS_DB = Room.databaseBuilder(
                            context.getApplicationContext(), CoordenadasDB.class, "coordenadas-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return COORDENADAS_DB;
    }
}
