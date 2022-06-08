package com.dam.peluqueriacanina.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.peluqueriacanina.dao.CestaDao;
import com.dam.peluqueriacanina.entity.Cesta;

@Database(entities = {Cesta.class}, version = 6, exportSchema = false)
public abstract class CestaDB extends RoomDatabase {
    public abstract CestaDao cestaDao();

    private static CestaDB CESTA_DB = null;

    public static CestaDB getDatabase(Context context) {
        if (CESTA_DB == null) {
            CESTA_DB = Room.databaseBuilder(
                            context.getApplicationContext(), CestaDB.class, "cesta-db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return CESTA_DB;
    }
}
